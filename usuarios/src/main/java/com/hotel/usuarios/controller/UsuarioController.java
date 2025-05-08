package com.hotel.usuarios.controller;


import com.hotel.usuarios.model.Usuario;
import com.hotel.usuarios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios") // URL base para los endpoints, La ruta raíz de la API
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    // Endpoint para registrar un nuevo usuario
    @PostMapping("/registrar")
    public String crearUsuario(@RequestBody Usuario usuario) {
        return service.crearUsuario(usuario);
    }
    // Endpoint para actualizar un usuario
    @PutMapping("/registrar")
    public String actualizarUsuario(@RequestBody Usuario usuario) {
        return service.actualizarUsuario(usuario);
    }
    // Endpoint para eliminar un usuario
    @DeleteMapping
    public String eliminarUsuario(@RequestBody Usuario usuario) {
        return service
                .eliminarUsuario(usuario.getNombre()
                        , usuario.getContrasena());
    }
    // Endpoint para validar un usuario
    @PostMapping("/validar")
    public boolean validarUsuario(@RequestBody Usuario usuario) {
        return service.validarUsuario(usuario.getNombre(), usuario.getContrasena());
    }
    // Endpoint para obtener el nombre de un usuario por su ID
    @GetMapping("/info/id/{id}")
    public String obtenerNombre(@PathVariable Integer id) {
        return service.obtenerNombrePorId(id);
    }
    // Endpoint para obtener el ID de un usuario por su nombre
    @GetMapping("/info/nombre/{nombre}")
    public String obtenerId(@PathVariable String nombre) {
        return service.obtenerIdPorNombre(nombre);
    }
    // Endpoint para verificar si un usuario existe por su ID
    @GetMapping("/checkIfExist/{id}")
    public boolean checkIfExist(@PathVariable Integer id) {
        return service.checkIfExist(id);
    }
}