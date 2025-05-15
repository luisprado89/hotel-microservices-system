package com.hotel.comentarios.repository;

import com.hotel.comentarios.model.Comentario;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ComentarioRepository extends MongoRepository<Comentario, String> {
    /*
      Verifica si ya existe un comentario hecho por un usuario específico
      sobre una reserva concreta en un hotel determinado.
      Usado en:
      - ComentarioService.crearComentario(...) → para evitar duplicados.
     */
    boolean existsByUsuarioIdAndHotelIdAndReservaId(int usuarioId, int hotelId, int reservaId);
    /**
      Obtiene todos los comentarios asociados a un hotel dado su ID.
      Usado en:
      - ComentarioService.obtenerComentariosPorHotel(...)
      - ComentarioService.mediaHotel(...)
     */
    List<Comentario> findByHotelId(Integer hotelId);
    /*
      Obtiene todos los comentarios realizados por un usuario específico.
      Usado en:
      - ComentarioService.obtenerComentariosPorUsuario(...)
      - ComentarioService.mediaUsuario(...)
     */
    List<Comentario> findByUsuarioId(Integer usuarioId);
    /*
      Busca un comentario concreto que coincida con una combinación
      de usuarioId, hotelId y reservaId.
      Usado en:
      - ComentarioService.obtenerComentarioDeReserva(...)
      - ComentarioService.eliminarPorIdAutenticado(...) para validar si el comentario pertenece al usuario.
     */
    Optional<Comentario> findByUsuarioIdAndHotelIdAndReservaId(int usuarioId, int hotelId, int reservaId);
}
