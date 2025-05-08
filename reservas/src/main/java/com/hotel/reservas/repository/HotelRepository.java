package com.hotel.reservas.repository;

import com.hotel.reservas.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    Optional<Hotel> findByNombre(String nombre);
}
