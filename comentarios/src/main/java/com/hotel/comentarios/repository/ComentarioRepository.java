package com.hotel.comentarios.repository;

import com.hotel.comentarios.model.Comentario;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ComentarioRepository extends MongoRepository<Comentario, String> {
    boolean existsByUsuarioIdAndHotelIdAndReservaId(int usuarioId, int hotelId, int reservaId);
    List<Comentario> findByHotelId(Integer hotelId);
    List<Comentario> findByUsuarioId(Integer usuarioId);
    Optional<Comentario> findByUsuarioIdAndHotelIdAndReservaId(int usuarioId, int hotelId, int reservaId);
}
