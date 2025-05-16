package com.hotel.reservas.repository;

import com.hotel.reservas.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {

    // Método para verificar si un hotel existe por su nombre y dirección
    boolean existsByNombreAndDireccion(String nombre, String direccion);

    // Método para buscar un hotel por su nombre se usa en 'obtenerIdApartirNombre'
    Optional<Hotel> findByNombre(String nombre);
}
