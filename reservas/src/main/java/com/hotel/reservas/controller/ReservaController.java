package com.hotel.reservas.controller;

import com.hotel.reservas.dto.CambiarEstadoDTO;
import com.hotel.reservas.dto.ReservaDTO;
import com.hotel.reservas.dto.ReservaUsuarioDTO;
import com.hotel.reservas.dto.UsuarioDTO;
import com.hotel.reservas.model.Reserva;
import com.hotel.reservas.repository.ReservaRepository;
import com.hotel.reservas.service.ReservaService;
import com.hotel.reservas.service.UsuariosRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController // Anotación que indica que esta clase es un controlador REST
@RequestMapping("/reservas") // URL base para los endpoints
public class ReservaController {
    // Inyección de dependencias para el servicio de reservas
    @Autowired
    private ReservaService reservaService;
    // Inyección de dependencias para el cliente REST de usuarios
    @Autowired
    private UsuariosRestClient usuariosRestClient;
    // Inyección de dependencias para el repositorio de reservas para que comentarios pueda acceder
    @Autowired
    private ReservaRepository reservaRepository;



    // Endpoint para crear una nueva reserva usando credenciales de usuario y contraseña
    @PostMapping
    public String crearReserva(@RequestBody ReservaDTO dto) {
        if (!usuariosRestClient.validarCredenciales(dto.getUsuario().getNombre(), dto.getUsuario().getContrasena())) {
            return "Credenciales incorrectas";
        }
        return reservaService.crearReserva(dto.getReserva(), dto.getUsuario().getNombre());
    }


    // Endpoint para actualizar una reserva usando credenciales de usuario y contraseña
    @PatchMapping
    public String cambiarEstado(@RequestBody CambiarEstadoDTO dto) {
        if (!usuariosRestClient.validarCredenciales(dto.getUsuario().getNombre(), dto.getUsuario().getContrasena())) {
            return "Credenciales incorrectas";
        }
        return reservaService.cambiarEstado(dto.getReservaId(), dto.getEstado());
    }

    //Aunque el enunciado no dice que devuelva mensaje de error de credenciales incorrectas lo pongo para no recibir un objeto vacio
    // Endpoint para obtener una reserva por su ID usando credenciales de usuario y contraseña
    @GetMapping
    public List<ReservaUsuarioDTO> listarReservasUsuario(@RequestBody UsuarioDTO usuario) {
        if (!usuariosRestClient.validarCredenciales(usuario.getNombre(), usuario.getContrasena())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales incorrectas");//Si se prefiere lanzar una excepción en lugar de devolver un objeto vacío sino comentamos este o lo eliminamos
            //return List.of(); si se prefiere no mostrar el mensaje de error de credenciales incorrectas se descomenta este
        }
        return reservaService.listarReservasUsuario(usuario.getNombre());
    }




    // Endpoint para obtener reservas por su estado usando credenciales de usuario y contraseña
    @GetMapping("/{estado}")
    public List<ReservaUsuarioDTO> listarReservasSegunEstado(
            @PathVariable String estado,
            @RequestBody UsuarioDTO usuario) {

        if (!usuariosRestClient.validarCredenciales(usuario.getNombre(), usuario.getContrasena())) {
            return List.of();  // o lanzar una excepción si se prefiere
        }
        return reservaService.listarReservasSegunEstado(estado);
    }



    // Endpoint para verificar si una reserva existe por ID de usuario, ID de hotel y ID de reserva
    // Este endpoint recibe los parámetros idUsuario, idHotel e idReserva
    // y devuelve un booleano indicando si la reserva existe o no
    //Este método no recibirá la información de usuario y contraseña.
    @GetMapping("/check")
    public boolean checkReserva(@RequestParam Integer idUsuario,
                                @RequestParam Integer idHotel,
                                @RequestParam Integer idReserva) {
        return reservaService.checkReserva(idUsuario, idHotel, idReserva);
    }
    // Se usa en el microservicio de comentarios.
    // Endpoint para obtener el ID del hotel desde la reserva para el Microservicio-Comentarios-->> mostrarComentarioUsuarioReserva
    @GetMapping("/hotel/idReserva/{idReserva}")
    public Integer obtenerHotelIdDesdeReserva(@PathVariable Integer idReserva) {
        Reserva reserva = reservaRepository.findById(idReserva)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        return reserva.getHabitacion().getHotel().getId(); // o según cómo esté estructurado tu modelo
    }

}
