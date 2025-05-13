package com.hotel.usuarios.service;


import com.hotel.usuarios.model.Usuario;
import com.hotel.usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {
    // Inyeccion de dependencias para el repositorio de usuarios
    @Autowired
    private UsuarioRepository repo;
    // Metodo para registrar un nuevo usuario
    public String crearUsuario(Usuario u) {
        try {
            repo.save(u);
            return "Usuario registrado correctamente";
        } catch (Exception e) {
            return "Error al registrar usuario";
        }
    }
  // Metodo para actualizar un usuario
    public String actualizarUsuario(Usuario u) {
        if (u.getId() == null || !repo.existsById(u.getId())) {
            return "Usuario no encontrado";
        }
        try {
            repo.save(u);
            return "Usuario actualizado correctamente";
        } catch (Exception e) {
            return "Error al actualizar usuario";
        }
    }
    // Metodo para eliminar un usuario devuelve un mensaje de exito o error
    public String eliminarUsuario(String nombre, String contrasena) {
        Optional<Usuario> usuario = repo.findByNombreAndContrasena(nombre, contrasena);
        if (usuario.isPresent()) {
            repo.delete(usuario.get());
            return "Usuario eliminado correctamente";
        } else {
            return "Usuario no encontrado o credenciales incorrectas";
        }
    }
    // Metodo para validar un usuario devuelve true si el usuario existe y false si no
    public boolean validarUsuario(String nombre, String contrasena) {
        return repo.findByNombreAndContrasena(nombre, contrasena).isPresent();
    }
    // Metodo para obtener el nombre de un usuario por su ID
    public String obtenerInfoUsuarioPorId(Integer id) {
        return repo.findById(id).map(Usuario::getNombre).orElse("Usuario no encontrado");
    }
    // Metodo para obtener el ID de un usuario por su nombre
    public String obtenerInfoUsuarioPorNombre(String nombre) {
        return repo.findByNombre(nombre)
                .map(u -> String.valueOf(u.getId()))
                .orElse("Usuario no encontrado");
    }
    // Metodo para verificar si un usuario existe por su ID
    public boolean checkIfExist(Integer id) {
        return repo.existsById(id);
    }
}