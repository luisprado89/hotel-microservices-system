package com.hotel.reservas.controller;

import com.hotel.reservas.dto.ReservaDTO;
import com.hotel.reservas.dto.ReservaUsuarioDTO;
import com.hotel.reservas.dto.UsuarioDTO;
import com.hotel.reservas.model.Reserva;
import com.hotel.reservas.service.ReservaService;
import com.hotel.reservas.service.UsuariosRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    // Endpoint para crear una nueva reserva usando credenciales de usuario y contraseña
    @PostMapping
    public String crearReserva(@RequestBody ReservaDTO dto) {
        if (!usuariosRestClient.validarCredenciales(dto.getUsuario().getNombre(), dto.getUsuario().getContrasena())) {
            return "Credenciales incorrectas";
        }
        return reservaService.crearReserva(dto.getReserva());
    }


    // Endpoint para actualizar una reserva usando credenciales de usuario y contraseña
    @PatchMapping
    public String cambiarEstado(@RequestParam Integer reservaId,
                                @RequestParam String estado,
                                @RequestBody UsuarioDTO usuario) {
        if (!usuariosRestClient.validarCredenciales(usuario.getNombre(), usuario.getContrasena())) {
            return "Credenciales incorrectas";
        }
        return reservaService.cambiarEstado(reservaId, estado);
    }


    // Endpoint para obtener una reserva por su ID usando credenciales de usuario y contraseña
    @GetMapping
    public List<ReservaUsuarioDTO> listarReservasUsuario(@RequestBody UsuarioDTO usuario) {
        if (!usuariosRestClient.validarCredenciales(usuario.getNombre(), usuario.getContrasena())) {
            return List.of();
        }
        return reservaService.listarReservasUsuario(usuario.getNombre());
    }


    // Endpoint para obtener reservas por su estado usando credenciales de usuario y contraseña
    @GetMapping("/estado")
    public List<ReservaUsuarioDTO> listarReservasSegunEstado(@RequestParam String estado,
                                         @RequestBody UsuarioDTO usuario) {
        if (!usuariosRestClient.validarCredenciales(usuario.getNombre(), usuario.getContrasena())) {
            return List.of();
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
}
