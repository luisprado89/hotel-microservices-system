package com.hotel.usuarios.repository;

import com.hotel.usuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByNombreAndContrasena(String nombre, String contrasena);

    Optional<Usuario> findByNombre(String nombre);

    boolean existsById(Integer id);
}
