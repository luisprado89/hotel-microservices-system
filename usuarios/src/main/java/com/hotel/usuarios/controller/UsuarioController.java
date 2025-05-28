package com.hotel.usuarios.controller;


import com.hotel.usuarios.model.Usuario;
import com.hotel.usuarios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios") // URL base para los endpoints, La ruta raíz de la API
public class UsuarioController {
    // Inyección de dependencias del servicio de usuario
    @Autowired
    private UsuarioService service;

    // Endpoint para registrar un nuevo usuario
    @PostMapping("/registrar")
    public String crearUsuario(@RequestBody Usuario usuario) {//@RequestBody le dice a Spring: "lee el cuerpo JSON y conviértelo en este objeto".
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
    public String obtenerInfoUsuarioPorId(@PathVariable Integer id) {
        return service.obtenerInfoUsuarioPorId(id);
    }
    // Endpoint para obtener el ID de un usuario por su nombre
    @GetMapping("/info/nombre/{nombre}")
    public String obtenerInfoUsuarioPorNombre(@PathVariable String nombre) {
        return service.obtenerInfoUsuarioPorNombre(nombre);
    }
    // Endpoint para verificar si un usuario existe por su ID
    @GetMapping("/checkIfExist/{id}")
    public boolean checkIfExist(@PathVariable Integer id) {
        return service.checkIfExist(id);
    }
}

/**
 *     GET: se utiliza para obtener un recurso, por ejemplo, cuando se accede a una página web.
 *     POST: se utiliza para enviar datos al servidor, por ejemplo, cuando se rellena un formulario y se pulsa el botón de enviar.
 *     PUT: se utiliza para actualizar un recurso, por ejemplo, cuando se actualiza un perfil de usuario.
 *     PATCH: se utiliza para actualizar parcialmente un recurso, por ejemplo, cuando se actualiza el nombre de un perfil de usuario.
 *     DELETE: se utiliza para eliminar un recurso, por ejemplo, cuando se elimina un perfil de usuario.
 *     HEAD: se utiliza para obtener los metadatos de un recurso, por ejemplo, cuando se accede a quiere acceder a los metadatos de una página web.
 */