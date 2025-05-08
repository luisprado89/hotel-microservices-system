package com.hotel.reservas.controller;

import com.hotel.reservas.dto.HabitacionDTO;
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

    @Autowired
    private UsuariosRestClient usuariosRestClient;

    // Endpoint para crear una nueva habitación usando credenciales de usuario y contraseña
    @PostMapping
    public String crearHabitacion(@RequestBody HabitacionDTO dto) {
        if (!usuariosRestClient.validarCredenciales(dto.getUsuario().getNombre(), dto.getUsuario().getContrasena())) {
            return "Credenciales incorrectas";
        }
        return habitacionService.crearHabitacion(dto);
    }

    // Endpoint para actualizar una habitación usando credenciales de usuario y contraseña
    @PatchMapping
    public String actualizarHabitacion(@RequestBody HabitacionDTO dto) {
        if (!usuariosRestClient.validarCredenciales(dto.getUsuario().getNombre(), dto.getUsuario().getContrasena())) {
            return "Credenciales incorrectas";
        }
        return habitacionService.actualizarHabitacion(dto);
    }

    // Endpoint para obtener una habitación por su ID usando credenciales de usuario y contraseña
    @DeleteMapping("/{id}")
    public String eliminarHabitacion(@PathVariable Integer id,
                                     @RequestParam String nombre,
                                     @RequestParam String contrasena) {
        if (!usuariosRestClient.validarCredenciales(nombre, contrasena)) {
            return "Credenciales incorrectas";
        }
        return habitacionService.eliminarHabitacion(id);
    }

}
