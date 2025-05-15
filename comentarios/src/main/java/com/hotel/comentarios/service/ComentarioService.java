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

    //Método para crear un comentario -> Usado en: ComentarioMutation.crearComentario(...)
    public ComentarioResponse crearComentario(ComentarioInput input) {
        // Validar usuario
        int usuarioId = obtenerUsuarioId(input.getNombreUsuario(), input.getContrasena());

        // Obtener ID del hotel con credenciales
        int hotelId = obtenerHotelId(input.getNombreHotel(), input.getNombreUsuario(), input.getContrasena());

        // Verificar si la reserva existe, el Metodo está más abajo
        if (!checkReserva(usuarioId, hotelId, input.getReservaId())) {
            throw new RuntimeException("Reserva no válida.");
        }

/**        Si el usuario ya hizo un comentario sobre esa combinación (idUsuario - idHotel - idReserva) no se podrá realizar el comentario.
        // Verificar si ya existe un comentario para esa combinación    */
        if (comentarioRepository.existsByUsuarioIdAndHotelIdAndReservaId(usuarioId, hotelId, input.getReservaId())) {
            System.out.println("Intento de comentar con combinación ya existente:");
            System.out.println("usuarioId: " + usuarioId);
            System.out.println("hotelId: " + hotelId);
            System.out.println("reservaId: " + input.getReservaId());
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

    //Método para eliminar todos los comentarios -> Usado en: ComentarioMutation.eliminarComentarios()
    public String eliminarTodos() {
        try {
            comentarioRepository.deleteAll();
            return "Todos los comentarios han sido eliminados correctamente.";
        } catch (Exception e) {
            e.printStackTrace(); // imprime en consola
            return "Error al eliminar los comentarios: " + e.getMessage();
        }
    }

    /**
     * //Eliminar un comentario por ID del comentario que es un string(sin autenticación). ->  Usado en: ComentarioMutation.eliminarComentarioDeUsuario(id)
     * //eliminarComentarioDeUsuario
     */
    public String eliminarPorId(String id) {
        if (!comentarioRepository.existsById(id)) {
            return "Error: El comentario no existe.";
        }

        comentarioRepository.deleteById(id);
        return "Comentario eliminado correctamente.";
    }

    /**
     * Eliminar un comentario por ID del comentario que es un string -> Usado en: ComentarioMutation.eliminarComentarioAutenticado(...)
     * eliminarComentarioDeUsuario en el caso de que pida usuario y contraseña sino podemos eliminarlo
     */
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
// -------------------- CONSULTAS --------------------

    /**
     * //Métodos para obtener comentarios de un hotel , validando credenciales del usuario. -> Usado en: ComentarioQuery.listarComentariosHotel(...)
     */
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

    /**
     * //Métodos para obtener comentarios de un usuario -> Usado en: ComentarioQuery.listarComentariosUsuario(...)
     */
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

    /**
     * // DTO interno para solicitud a /hotel/id y /hotel/nombre  para enviar información de hotel y usuario al microservicio reservas
     * //Métodos para obtener comentarios de un usuario por reserva específica. -> Usado en: ComentarioQuery.mostrarComentarioUsuarioReserva(...)
     */
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

    /**
     * //Métodos para obtener la media de puntuaciones de un hotel de todos los comentarios hechos a un hotel. -> Usado en: ComentarioQuery.puntuacionMediaHotel(...)
     */
    public Double mediaHotel(String nombreHotel, String nombreUsuario, String contrasena) {
        Integer hotelId = obtenerHotelId(nombreHotel, nombreUsuario, contrasena);
        List<Comentario> comentarios = comentarioRepository.findByHotelId(hotelId);

        return comentarios.isEmpty() ? 0.0 :
                comentarios.stream()
                        .mapToDouble(Comentario::getPuntuacion)
                        .average()
                        .orElse(0.0);
    }

    /**
     * //Métodos para obtener la media de puntuaciones de un usuario. -> Usado en: ComentarioQuery.puntuacionesMediasUsuario(...)
     */
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

    /**
     * // Métodos para obtener el ID del usuario y del hotel, validando credenciales contra los microservicios de usuarios.
     */
    private Integer obtenerUsuarioId(String nombre, String contrasena) {
        Boolean esValido = restTemplate.postForObject(
                /** Endpoint @PostMapping("/validar") -> validarUsuario
                 * -> Microservicio Usuarios*/
                USUARIOS_URL + "/validar",
                new UsuarioDTO(nombre, contrasena),
                Boolean.class
        );

        if (Boolean.FALSE.equals(esValido)) {
            throw new RuntimeException("Usuario o contraseña inválidos.");
        }

        return restTemplate.getForObject(
                /** Endpoint @GetMapping("/info/nombre/{nombre}") -> obtenerInfoUsuarioPorNombre
                 * -> Microservicio Usuarios*/
                USUARIOS_URL + "/info/nombre/" + nombre,
                Integer.class
        );
    }

    // Método para obtener el ID del hotel, validando credenciales contra el microservicio de reservas.
    private Integer obtenerHotelId(String nombreHotel, String nombreUsuario, String contrasena) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        UsuarioDTO usuarioDTO = new UsuarioDTO(nombreUsuario, contrasena);
        HttpEntity<UsuarioDTO> request = new HttpEntity<>(usuarioDTO, headers);

        // La URL debe coincidir con el endpoint del microservicio reservas  'obtenerIdApartirNombre'
        String resultado = restTemplate.postForObject(
                /** Endpoint @PostMapping("/id/{nombreHotel}") -> obtenerIdApartirNombre
                 * -> Microservicio Reservas*/
                RESERVAS_URL + "/hotel/id/" + nombreHotel,
                request,
                String.class
        );

        try {
            return Integer.parseInt(resultado);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Respuesta inválida al obtener hotel: " + resultado);
        }
    }

/**    // Método para obtener el nombre del hotel a partir de su ID, validando credenciales contra el microservicio de 'reservas'.
*/
    private String obtenerNombreHotel(Integer hotelId, String nombreUsuario, String contrasena) {
        // Crear encabezado JSON
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // Crear el cuerpo con las credenciales del usuario
        UsuarioDTO usuarioDTO = new UsuarioDTO(nombreUsuario, contrasena);
        HttpEntity<UsuarioDTO> request = new HttpEntity<>(usuarioDTO, headers);
        /**
         * La URL debe coincidir con el endpoint del microservicio reservas  'obtenerNombreAPartirId'
         * Endpoint @PostMapping("/nombre/{idHotel}") -> obtenerNombreAPartirId
         * -> Microservicio Reservas*/

        return restTemplate.postForObject(
                RESERVAS_URL + "/hotel/nombre/" + hotelId,
                request,
                String.class
        );
    }

    /**
     * // Método para obtener el ID del hotel a partir de la reserva, validando credenciales contra el microservicio de 'reservas'.
     */
    private Integer obtenerHotelIdDesdeReserva(Integer reservaId) {
        return restTemplate.getForObject(//Metodo creado en el microservicio reservas en ReservasController ya que no lo teníamos y no lo pedia en ningun enunciado.
                /** Endpoint @GetMapping("/hotel/idReserva/{idReserva}") -> obtenerHotelIdDesdeReserva
                 * -> Microservicio Reservas*/
                RESERVAS_URL + "/hotel/idReserva/" + reservaId,
                Integer.class
        );
    }

    /**
     * // Método para verificar si la reserva es válida, validando credenciales contra el microservicio de 'reservas'.
     * Enunciado:
     * Deberá comprobar frente al microservicio reservas (método checkReserva) si la combinación (idUsuario - idHotel - idReserva) existe antes de poder crear el comentario.
     */
    private boolean checkReserva(Integer usuarioId, Integer hotelId, Integer reservaId) {
        return Boolean.TRUE.equals(restTemplate.getForObject(
                /** Endpoint @GetMapping("/check") -> checkReserva
                 * -> Microservicio Reservas*/
                RESERVAS_URL + "/check?idUsuario=" + usuarioId + "&idHotel=" + hotelId + "&idReserva=" + reservaId,
                Boolean.class
        ));
    }

    // DTO interno para validación de usuario
    record UsuarioDTO(String nombre, String contrasena) {
    }

}
