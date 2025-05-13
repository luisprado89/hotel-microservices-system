package com.hotel.comentarios.service;

import com.hotel.comentarios.dto.ComentarioInput;
import com.hotel.comentarios.dto.ComentarioResponse;
import com.hotel.comentarios.dto.EliminarComentarioInput;
import com.hotel.comentarios.model.Comentario;
import com.hotel.comentarios.repository.ComentarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final RestTemplate restTemplate;

    private static final String USUARIOS_URL = "http://localhost:8080/usuarios";
    private static final String RESERVAS_URL = "http://localhost:8080/reservas";

    public ComentarioResponse crearComentario(ComentarioInput input) {
        // Validar usuario
        int usuarioId = obtenerUsuarioId(input.getNombreUsuario(), input.getContrasena());

        // Obtener ID del hotel con credenciales
        int hotelId = obtenerHotelId(input.getNombreHotel(), input.getNombreUsuario(), input.getContrasena());

        // Verificar si la reserva existe
        if (!checkReserva(usuarioId, hotelId, input.getReservaId())) {
            throw new RuntimeException("Reserva no válida.");
        }

        // Verificar si ya existe un comentario para esa combinación
        if (comentarioRepository.existsByUsuarioIdAndHotelIdAndReservaId(usuarioId, hotelId, input.getReservaId())) {
            throw new RuntimeException("Ya existe un comentario para esta reserva.");
        }

        // Crear y guardar comentario
        Comentario comentario = new Comentario();
        comentario.setUsuarioId(usuarioId);
        comentario.setHotelId(hotelId);
        comentario.setReservaId(input.getReservaId());
        comentario.setPuntuacion(input.getPuntuacion());
        comentario.setComentario(input.getComentario());
        comentario.setFechaCreacion(Instant.now());

        comentarioRepository.save(comentario);

        return new ComentarioResponse(
                input.getNombreHotel(),
                input.getReservaId(),
                input.getPuntuacion(),
                input.getComentario()
        );
    }

    public String eliminarTodos() {
        try {
            comentarioRepository.deleteAll();
            return "Todos los comentarios han sido eliminados correctamente.";
        } catch (Exception e) {
            e.printStackTrace(); // imprime en consola
            return "Error al eliminar los comentarios: " + e.getMessage();
        }
    }

    //Eliminar un comentario por ID del comentario que es un string
    //eliminarComentarioDeUsuario
    public String eliminarPorId(String id) {
        if (!comentarioRepository.existsById(id)) {
            return "Error: El comentario no existe.";
        }

        comentarioRepository.deleteById(id);
        return "Comentario eliminado correctamente.";
    }
    //Eliminar un comentario por ID del comentario que es un string
    //eliminarComentarioDeUsuario en el caso de pida usuario y contraseña sino podemos eliminarlo
    public String eliminarPorIdAutenticado(EliminarComentarioInput input) {
        Integer usuarioId = obtenerUsuarioId(input.getNombreUsuario(), input.getContrasena());

        Optional<Comentario> comentarioOpt = comentarioRepository.findById(input.getId());
        if (comentarioOpt.isEmpty()) {
            return "Error: El comentario no existe.";
        }

        Comentario comentario = comentarioOpt.get();
        if (comentario.getUsuarioId() != usuarioId) {
            return "Error: No tienes permiso para eliminar este comentario.";
        }

        comentarioRepository.deleteById(input.getId());
        return "Comentario eliminado correctamente.";
    }



    public List<ComentarioResponse> obtenerComentariosPorHotel(String nombreHotel, String nombreUsuario, String contrasena) {
        Integer hotelId = obtenerHotelId(nombreHotel, nombreUsuario, contrasena);
        return comentarioRepository.findByHotelId(hotelId).stream()
                .map(c -> new ComentarioResponse(
                        nombreHotel,
                        c.getReservaId(),
                        c.getPuntuacion(),
                        c.getComentario()))
                .collect(Collectors.toList());
    }


    public List<ComentarioResponse> obtenerComentariosPorUsuario(String nombreUsuario, String contrasena) {
        Integer usuarioId = obtenerUsuarioId(nombreUsuario, contrasena);
        return comentarioRepository.findByUsuarioId(usuarioId).stream()
                .map(c -> new ComentarioResponse(
                        obtenerNombreHotel(c.getHotelId(), nombreUsuario, contrasena),
                        c.getReservaId(),
                        c.getPuntuacion(),
                        c.getComentario()))
                .collect(Collectors.toList());
    }

    public List<ComentarioResponse> obtenerComentarioDeReserva(String nombreUsuario, String contrasena, Integer reservaId) {
        Integer usuarioId = obtenerUsuarioId(nombreUsuario, contrasena);
        Integer hotelId = obtenerHotelIdDesdeReserva(reservaId);

        Optional<Comentario> comentarioOpt = comentarioRepository
                .findByUsuarioIdAndHotelIdAndReservaId(usuarioId, hotelId, reservaId);

        return comentarioOpt
                .map(c -> List.of(new ComentarioResponse(
                        obtenerNombreHotel(c.getHotelId(), nombreUsuario, contrasena),
                        c.getReservaId(),
                        c.getPuntuacion(),
                        c.getComentario())))
                .orElse(List.of());
    }

    public Double mediaHotel(String nombreHotel, String nombreUsuario, String contrasena) {
        Integer hotelId = obtenerHotelId(nombreHotel, nombreUsuario, contrasena);
        List<Comentario> comentarios = comentarioRepository.findByHotelId(hotelId);

        return comentarios.isEmpty() ? 0.0 :
                comentarios.stream()
                        .mapToDouble(Comentario::getPuntuacion)
                        .average()
                        .orElse(0.0);
    }


    public Double mediaUsuario(String nombreUsuario, String contrasena) {
        Integer usuarioId = obtenerUsuarioId(nombreUsuario, contrasena);
        List<Comentario> comentarios = comentarioRepository.findByUsuarioId(usuarioId);

        return comentarios.isEmpty() ? 0.0 :
                comentarios.stream()
                        .mapToDouble(Comentario::getPuntuacion)
                        .average()
                        .orElse(0.0);
    }

    // -----------------------------
    // Métodos auxiliares privados
    // -----------------------------

    private Integer obtenerUsuarioId(String nombre, String contrasena) {
        Boolean esValido = restTemplate.postForObject(
                USUARIOS_URL + "/validar",
                new UsuarioDTO(nombre, contrasena),
                Boolean.class
        );

        if (Boolean.FALSE.equals(esValido)) {
            throw new RuntimeException("Usuario o contraseña inválidos.");
        }

        return restTemplate.getForObject(
                USUARIOS_URL + "/info/nombre/" + nombre,
                Integer.class
        );
    }

    private Integer obtenerHotelId(String nombreHotel, String nombreUsuario, String contrasena) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HotelDTO dto = new HotelDTO(nombreHotel, new UsuarioDTO(nombreUsuario, contrasena));

        HttpEntity<HotelDTO> request = new HttpEntity<>(dto, headers);

        String resultado = restTemplate.postForObject(
                RESERVAS_URL + "/hotel/id",
                request,
                String.class
        );

        try {
            return Integer.parseInt(resultado);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Respuesta inválida al obtener hotel: " + resultado);
        }
    }

    private String obtenerNombreHotel(Integer hotelId, String nombreUsuario, String contrasena) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HotelDTO dto = new HotelDTO(null, new UsuarioDTO(nombreUsuario, contrasena));
        dto.id = hotelId;

        HttpEntity<HotelDTO> request = new HttpEntity<>(dto, headers);

        return restTemplate.postForObject(
                RESERVAS_URL + "/hotel/nombre",
                request,
                String.class
        );
    }

    private Integer obtenerHotelIdDesdeReserva(Integer reservaId) {
        return restTemplate.getForObject(
                RESERVAS_URL + "/hotel/idReserva/" + reservaId,
                Integer.class
        );
    }

    private boolean checkReserva(Integer usuarioId, Integer hotelId, Integer reservaId) {
        return Boolean.TRUE.equals(restTemplate.getForObject(
                RESERVAS_URL + "/check?idUsuario=" + usuarioId + "&idHotel=" + hotelId + "&idReserva=" + reservaId,
                Boolean.class
        ));
    }

    // DTO interno para validación de usuario
    record UsuarioDTO(String nombre, String contrasena) {}

    // DTO interno para solicitud a /hotel/id y /hotel/nombre
    static class HotelDTO {
        public Integer id;
        public String nombre;
        public UsuarioDTO usuario;

        public HotelDTO(String nombre, UsuarioDTO usuario) {
            this.nombre = nombre;
            this.usuario = usuario;
        }
    }
}
