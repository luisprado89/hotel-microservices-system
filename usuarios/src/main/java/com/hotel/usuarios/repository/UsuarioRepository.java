package com.hotel.usuarios.repository;

import com.hotel.usuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    //Buscar por nombre y contraseña
    Optional<Usuario> findByNombreAndContrasena(String nombre, String contrasena);
    //Buscar por nombre
    Optional<Usuario> findByNombre(String nombre);
    //Verificar si existe un usuario por ID devuelve true o false
    boolean existsById(Integer id);
}
/*
Utilizados para validar credenciales, obtener usuarios por nombre o ID, y verificar existencia.
 */
/**
 * Métodos heredados más importantes de JpaRepository aplicados a tu entidad Usuario, incluyendo los personalizados y un ejemplo para cada uno:
 * 📘 Métodos de UsuarioRepository
 * Método	Descripción	Ejemplo de uso
 * save(Usuario u)	Inserta o actualiza un usuario	usuarioRepository.save(new Usuario(null, "Luis", ...))
 * findById(Integer id)	Busca un usuario por ID	usuarioRepository.findById(1)
 * findAll()	Lista todos los usuarios	usuarioRepository.findAll()
 * deleteById(Integer id)	Elimina por ID	usuarioRepository.deleteById(3)
 * existsById(Integer id)	Verifica si existe el ID	usuarioRepository.existsById(2)
 * count()	Cuenta total de usuarios	usuarioRepository.count()
 * delete(Usuario u)	Elimina el usuario dado	usuarioRepository.delete(usuario)
 * findAllById(List<Integer> ids)	Busca múltiples por ID	usuarioRepository.findAllById(List.of(1, 2))
 *
 * 🧩 Métodos personalizados de UsuarioRepository
 * Método	Descripción	Ejemplo de uso
 * findByNombreAndContrasena(String n, String c)	Busca por nombre y contraseña	usuarioRepository.findByNombreAndContrasena("Luis", "abc123")
 * findByNombre(String n)	Busca por nombre	usuarioRepository.findByNombre("Ana")
 * existsById(Integer id)	Verifica si el ID existe (heredado pero usado explícitamente)	usuarioRepository.existsById(1)
 */