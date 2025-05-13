package com.hotel.usuarios.repository;

import com.hotel.usuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    //Buscar por nombre y contraseña
    Optional<Usuario> findByNombreAndContrasena(String nombre, String contrasena);
    //Buscar por nombre
    Optional<Usuario> findByNombre(String nombre);
    //Buscar por id
    boolean existsById(Integer id);
}
