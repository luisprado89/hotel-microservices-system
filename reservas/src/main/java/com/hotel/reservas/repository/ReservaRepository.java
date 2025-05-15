package com.hotel.reservas.repository;

import com.hotel.reservas.dto.ReservaUsuarioDTO;
import com.hotel.reservas.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    List<Reserva> findByUsuarioId(Integer usuarioId); // -> listarReservasUsuario
    List<ReservaUsuarioDTO> findByEstado(String estado);  // -> listarReservasSegunEstado
    boolean existsByIdAndUsuarioIdAndHabitacion_Hotel_Id(Integer reservaId, Integer usuarioId, Integer hotelId); // -> checkReserva
    // Eliminar reservas por ID de habitación
    void deleteByHabitacionId(Integer habitacionId); //no se usa en tu código actual. Siempre que se mantenga cascade = CascadeType.ALL y orphanRemoval = true en la relación Habitacion → Reserva.
    //Eso significa que cuando eliminas una habitación, Hibernate elimina automáticamente todas las reservas relacionadas, sin necesidad de llamar manualmente a deleteByHabitacionId(...).
}
