package com.hotel.reservas.repository;

import com.hotel.reservas.model.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitacionRepository extends JpaRepository<Habitacion, Integer> {

    // Método para verificar si existe una habitación por su ID de hotel y número de habitación
    boolean existsByHotelIdAndNumeroHabitacion(Integer hotelId, Integer numeroHabitacion);

    // Método para buscar habitaciones por su ID de hotel
    void deleteByHotel_Id(Integer hotelId);
    /*Gracias a la configuración de JPA/Hibernate. Siempre que se mantenga cascade = CascadeType.ALL y orphanRemoval = true
     cuando eliminas un hotel, automáticamente se eliminan todas sus
    habitaciones asociadas, sin necesidad de invocar manualmente deleteByHotel_Id(...).
     Solo lo necesitariamos si fueramos a eliminar habitaciones manualmente sin eliminar el hotel.*/
}
