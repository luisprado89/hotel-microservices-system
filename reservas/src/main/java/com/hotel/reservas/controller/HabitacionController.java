package com.hotel.reservas.controller;

import com.hotel.reservas.dto.HabitacionDTO;
import com.hotel.reservas.dto.UsuarioDTO;
import com.hotel.reservas.service.HabitacionService;
import com.hotel.reservas.service.UsuariosRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController // Anotación que indica que esta clase es un controlador REST
@RequestMapping("/reservas/habitacion") // URL base para los endpoints, La ruta raíz de la API
public class HabitacionController {
    // Inyección de dependencias para el servicio de habitaciones
    @Autowired
    private HabitacionService habitacionService;
    // Inyección de dependencias para el cliente REST de usuarios
    @Autowired
    private UsuariosRestClient usuariosRestClient;

    // Endpoint para crear una nueva habitación usando credenciales de usuario y contraseña
    @PostMapping
    public String crearHabitacion(@RequestBody HabitacionDTO dto) {
        // Validar que se envió el objeto usuario y sus credenciales
        if (dto.getUsuario() == null ||
                dto.getUsuario().getNombre() == null ||
                dto.getUsuario().getContrasena() == null) {
            return "Error: Credenciales de usuario (nombre y contraseña) obligatorias.";
        }

        // Validación de credenciales de usuario que sean correctas
        if (!usuariosRestClient.validarCredenciales(dto.getUsuario().getNombre(), dto.getUsuario().getContrasena())) {
            return "Credenciales incorrectas";
        }
        return habitacionService.crearHabitacion(dto);
    }

    // Endpoint para actualizar una habitación usando credenciales de usuario y contraseña
    @PatchMapping
    public String actualizarHabitacion(@RequestBody HabitacionDTO dto) {
        // Validar que se envió el objeto usuario y sus credenciales
        if (dto.getUsuario() == null ||
                dto.getUsuario().getNombre() == null ||
                dto.getUsuario().getContrasena() == null) {
            return "Error: Credenciales de usuario (nombre y contraseña) obligatorias.";
        }

        // Validación de credenciales de usuario que sean correctas
        if (!usuariosRestClient.validarCredenciales(dto.getUsuario().getNombre(), dto.getUsuario().getContrasena())) {
            return "Credenciales incorrectas";
        }
        return habitacionService.actualizarHabitacion(dto);
    }

    // Endpoint para obtener una habitación por su ID usando credenciales de usuario y contraseña
    @DeleteMapping("/{id}")
    public String eliminarHabitacion(@PathVariable Integer id,
                                     @RequestBody UsuarioDTO usuario) {
        // Validar que se enviaron las credenciales
        if (usuario == null ||
                usuario.getNombre() == null ||
                usuario.getContrasena() == null) {
            return "Error: Credenciales de usuario (nombre y contraseña) obligatorias.";
        }
        // Validación de credenciales de usuario que sean correctas
        if (!usuariosRestClient.validarCredenciales(usuario.getNombre(), usuario.getContrasena())) {
            return "Credenciales incorrectas";
        }
        return habitacionService.eliminarHabitacion(id);
    }

}
