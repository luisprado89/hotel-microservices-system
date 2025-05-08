package com.hotel.reservas.controller;

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
    public String crearReserva(@RequestBody Reserva reserva,
                               @RequestParam String nombre,
                               @RequestParam String contrasena) {
        if (!usuariosRestClient.validarCredenciales(nombre, contrasena)) {
            return "Credenciales incorrectas";
        }
        return reservaService.crearReserva(reserva);
    }


    // Endpoint para actualizar una reserva usando credenciales de usuario y contraseña
    @PatchMapping
    public String cambiarEstado(@RequestParam Integer reservaId,
                                @RequestParam String estado,
                                @RequestParam String nombre,
                                @RequestParam String contrasena) {
        if (!usuariosRestClient.validarCredenciales(nombre, contrasena)) {
            return "Credenciales incorrectas";
        }
        return reservaService.cambiarEstado(reservaId, estado);
    }


    // Endpoint para obtener una reserva por su ID usando credenciales de usuario y contraseña
    @GetMapping
    public List<Reserva> listarPorUsuario(@RequestParam Integer usuarioId,
                                          @RequestParam String nombre,
                                          @RequestParam String contrasena) {
        if (!usuariosRestClient.validarCredenciales(nombre, contrasena)) {
            return List.of(); // vacío si no es válido
        }
        return reservaService.listarReservasPorUsuario(usuarioId);
    }


    // Endpoint para obtener reservas por su estado usando credenciales de usuario y contraseña
    @GetMapping("/estado")
    public List<Reserva> listarPorEstado(@RequestParam String estado,
                                         @RequestParam String nombre,
                                         @RequestParam String contrasena) {
        if (!usuariosRestClient.validarCredenciales(nombre, contrasena)) {
            return List.of();
        }
        return reservaService.listarReservasPorEstado(estado);
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
