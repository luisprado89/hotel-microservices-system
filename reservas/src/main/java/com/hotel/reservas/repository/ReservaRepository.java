package com.hotel.reservas.repository;

import com.hotel.reservas.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    List<Reserva> findByUsuarioId(Integer usuarioId);
    List<Reserva> findByEstado(String estado);
    boolean existsByIdAndUsuarioIdAndHabitacion_Hotel_Id(Integer reservaId, Integer usuarioId, Integer hotelId);
    // Eliminar reservas por ID de habitación
    void deleteByHabitacionId(Integer habitacionId);
}
