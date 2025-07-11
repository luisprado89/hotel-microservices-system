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

   // Metodo para registrar un nuevo usuario devuelve un mensaje de exito o error obligamos al usuario a ingresar todos los datos, ya que la base de datos, ni la entidad tienen notnull*/
    /**
      Endpoint @PostMapping("/registrar") -> crearUsuario
      -> Microservicio Usuarios
     */
    public String crearUsuario(Usuario u) {
        if (u == null ||
                u.getNombre() == null || u.getNombre().isBlank() ||
                u.getCorreoElectronico() == null || u.getCorreoElectronico().isBlank() ||
                u.getDireccion() == null || u.getDireccion().isBlank() ||
                u.getContrasena() == null || u.getContrasena().isBlank()) {
            return "Error al registrar usuario: datos incompletos";
        }
/**
 * repo.save(u)
 * aunque no veamos el método save() en  UsuarioRepository, está disponible porque está heredando de JpaRepository, que lo define.
 * Esto es parte de la “magia” de Spring Data JPA: la interfaz que tú defines es suficiente. Spring se encarga de implementar los métodos en tiempo de ejecución (a través de proxies dinámicos).
 */
        try {
           repo.save(u);// Guardar el usuario en la base de datos
            return "Usuario registrado correctamente";
        } catch (Exception e) {
            return "Error al registrar usuario";
        }
    }

    /**
      Endpoint @PutMapping("/registrar") -> actualizarUsuario
      -> Microservicio Usuarios
     */

    // Metodo para actualizar un usuario
    public String actualizarUsuario(Usuario u) {
        if (u.getId() == null || !repo.existsById(u.getId())) {
            return "Usuario no encontrado";
        }

        try {
            Usuario usuarioExistente = repo.findById(u.getId()).get();
        // Actualizar solo los campos que no son nulos o vacíos pide al usuario que ingrese todos los datos
            if (u.getNombre() != null) {
                usuarioExistente.setNombre(u.getNombre());
            }
            if (u.getCorreoElectronico() != null) {
                usuarioExistente.setCorreoElectronico(u.getCorreoElectronico());
            }
            if (u.getDireccion() != null) {
                usuarioExistente.setDireccion(u.getDireccion());
            }
            if (u.getContrasena() != null) {
                usuarioExistente.setContrasena(u.getContrasena());
            }

            repo.save(usuarioExistente);
            return "Usuario actualizado correctamente";
        } catch (Exception e) {
            return "Error al actualizar usuario";
        }
    }





    /**
      Endpoint @DeleteMapping -> eliminarUsuario
      -> Microservicio Usuarios
     */
    // Metodo para eliminar un usuario devuelve un mensaje de exito o error
    public String eliminarUsuario(String nombre, String contrasena) {
        // Validar que el nombre y la contraseña no sean nulos o vacíos
        if (nombre == null || nombre.trim().isEmpty() || contrasena == null || contrasena.trim().isEmpty()) {
            return "Debe introducir nombre de usuario y contraseña";
        }
        Optional<Usuario> usuario = repo.findByNombreAndContrasena(nombre, contrasena);
        if (usuario.isPresent()) {// Verificar si el usuario existe con las credenciales proporcionadas
            repo.delete(usuario.get());// Eliminar el usuario de la base de datos
            return "Usuario eliminado correctamente";
        } else {
            return "Usuario no encontrado o credenciales incorrectas";
        }
    }

    /**
      Endpoint @PostMapping("/validar") -> validarUsuario
      -> Microservicio Usuarios
     */
    // Metodo para validar un usuario devuelve true si el usuario existe y false si no
    public boolean validarUsuario(String nombre, String contrasena) {
        return repo.findByNombreAndContrasena(nombre, contrasena).isPresent();
    }

    /**
      Endpoint @GetMapping("/info/id/{id}") -> obtenerInfoUsuarioPorId
      -> Microservicio Usuarios
     */
    // Metodo para obtener el nombre de un usuario por su ID
    public String obtenerInfoUsuarioPorId(Integer id) {
        return repo.findById(id).map(Usuario::getNombre).orElse("Usuario no encontrado");
    }

    /**
      Endpoint @GetMapping("/info/nombre/{nombre}") -> obtenerInfoUsuarioPorNombre
      -> Microservicio Usuarios
     */
    // Metodo para obtener el ID de un usuario por su nombre
    public String obtenerInfoUsuarioPorNombre(String nombre) {
        return repo.findByNombre(nombre)
                .map(u -> String.valueOf(u.getId()))
                .orElse("Usuario no encontrado");
    }

    /**
      Endpoint @GetMapping("/checkIfExist/{id}") -> checkIfExist
      -> Microservicio Usuarios
     */
    // Metodo para verificar si un usuario existe por su ID
    public boolean checkIfExist(Integer id) {
        return repo.existsById(id);
    }
}