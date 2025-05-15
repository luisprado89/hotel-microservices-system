package com.hotel.reservas.controller;

import com.hotel.reservas.dto.HotelDTO;
import com.hotel.reservas.dto.UsuarioDTO;
import com.hotel.reservas.service.HotelService;
import com.hotel.reservas.service.UsuariosRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController // Anotación que indica que esta clase es un controlador REST
@RequestMapping("/reservas/hotel") // URL base para los endpoints, La ruta raíz de la API
public class HotelController {
    // Inyección de dependencias para el servicio de hoteles
    @Autowired
    private HotelService hotelService;
    // Inyección de dependencias para el cliente REST de usuarios
    @Autowired
    private UsuariosRestClient usuariosRestClient;

    // Endpoint para crear un nuevo hotel usando credenciales de usuario y contraseña
    @PostMapping
    public String crearHotel(@RequestBody HotelDTO dto) {
        // Validar que se envió el objeto usuario y sus credenciales
        if (dto.getUsuario() == null ||
                dto.getUsuario().getNombre() == null ||
                dto.getUsuario().getContrasena() == null) {
            return "Error: Credenciales de usuario (nombre y contraseña) obligatorias.";
        }

        // Validar que se envió el objeto usuario y sus credenciales
        if (!usuariosRestClient.validarCredenciales(dto.getUsuario().getNombre(), dto.getUsuario().getContrasena())) {
            return "Credenciales incorrectas";
        }
        return hotelService.crearHotel(dto);
    }
    //PATCH puede implicar actualización parcial no esta obligado a actualizar todos los campos
    // Endpoint para actualizar un hotel usando credenciales de usuario y contraseña
    @PatchMapping
    public String actualizarHotel(@RequestBody HotelDTO dto) {
        // Validar que se envió el objeto usuario y sus credenciales
        if (dto.getUsuario() == null ||
                dto.getUsuario().getNombre() == null ||
                dto.getUsuario().getContrasena() == null) {
            return "Error: Credenciales de usuario (nombre y contraseña) obligatorias.";
        }
        // Validar que credenciales de usuario sean correctas
        if (!usuariosRestClient.validarCredenciales(dto.getUsuario().getNombre(), dto.getUsuario().getContrasena())) {
            return "Credenciales incorrectas";
        }
        return hotelService.actualizarHotel(dto);
    }

    // Endpoint para eliminar un hotel por su ID usando credenciales de usuario y contraseña
    @DeleteMapping("/{id}")
    public String eliminarHotel(@PathVariable Integer id,
                                @RequestBody UsuarioDTO usuario) {
        // Validar que se enviaron las credenciales
        if (usuario == null ||
                usuario.getNombre() == null ||
                usuario.getContrasena() == null) {
            return "Error: Credenciales de usuario (nombre y contraseña) obligatorias.";
        }

        // Validar que credenciales de usuario sean correctas
        if (!usuariosRestClient.validarCredenciales(usuario.getNombre(), usuario.getContrasena())) {
            return "Credenciales incorrectas";
        }
        return hotelService.eliminarHotel(id);
    }

    // Endpoint para obtener un hotel por su ID a partir del nombre y usando credenciales de usuario y contraseña
    @PostMapping("/id/{nombreHotel}")
    public String obtenerIdApartirNombre(@PathVariable String nombreHotel, @RequestBody UsuarioDTO usuario) {
        // Validar que se enviaron las credenciales
        if (usuario == null ||
                usuario.getNombre() == null ||
                usuario.getContrasena() == null) {
            return "Error: Credenciales de usuario (nombre y contraseña) obligatorias.";
        }

        //Validar que credenciales de usuario sean correctas
        if (!usuariosRestClient.validarCredenciales(usuario.getNombre(), usuario.getContrasena())) {
            return "Credenciales incorrectas";
        }
        return hotelService.obtenerIdApartirNombre(nombreHotel);
    }


    // Endpoint para verificar si un hotel existe por su ID usando credenciales de usuario y contraseña
    @PostMapping("/nombre/{idHotel}")
    public String obtenerNombreAPartirId(@PathVariable Integer idHotel, @RequestBody UsuarioDTO usuario) {
        // Validar que se enviaron las credenciales
        if (usuario == null ||
                usuario.getNombre() == null ||
                usuario.getContrasena() == null) {
            return "Error: Credenciales de usuario (nombre y contraseña) obligatorias.";
        }

        // Validar que credenciales de usuario sean correctas
        if (!usuariosRestClient.validarCredenciales(usuario.getNombre(), usuario.getContrasena())) {
            return "Credenciales incorrectas";
        }
        return hotelService.obtenerNombreAPartirId(idHotel);
    }

}
