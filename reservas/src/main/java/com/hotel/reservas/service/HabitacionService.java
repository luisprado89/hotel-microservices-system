package com.hotel.reservas.service;

import com.hotel.reservas.dto.HabitacionDTO;
import com.hotel.reservas.model.Habitacion;
import com.hotel.reservas.model.Hotel;
import com.hotel.reservas.repository.HabitacionRepository;
import com.hotel.reservas.repository.HotelRepository;
import com.hotel.reservas.repository.ReservaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HabitacionService {
    // Inyección de dependencias para el repositorio de habitaciones
    @Autowired
    private HabitacionRepository habitacionRepository;
    // Inyección de dependencias para el repositorio de hoteles
    @Autowired
    private HotelRepository hotelRepository;
    // Inyección de dependencias para el repositorio de reservas
    @Autowired
    private ReservaRepository reservaRepository;

    // Constructor de la clase
    public String crearHabitacion(HabitacionDTO dto) {
        Optional<Hotel> hotelOpt = hotelRepository.findById(dto.getIdHotel());
        if (hotelOpt.isEmpty()) {
            return "Hotel no encontrado";
        }

        Habitacion habitacion = new Habitacion();
        habitacion.setHotel(hotelOpt.get());
        habitacion.setNumeroHabitacion(dto.getNumeroHabitacion());
        habitacion.setTipo(dto.getTipo());
        habitacion.setPrecio(dto.getPrecio());
        habitacion.setDisponible(true); // Por defecto se marca como disponible

        try {
            habitacionRepository.save(habitacion);
            return "Habitación creada correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al crear la habitación";
        }
    }


    // Método para actualizar una habitación
    public String actualizarHabitacion(HabitacionDTO dto) {
        if (dto.getId() == null || !habitacionRepository.existsById(dto.getId())) {
            return "Habitación no encontrada";
        }

        Optional<Hotel> hotelOpt = hotelRepository.findById(dto.getIdHotel());
        if (hotelOpt.isEmpty()) {
            return "Hotel no encontrado";
        }

        Habitacion habitacion = new Habitacion();
        habitacion.setId(dto.getId());
        habitacion.setHotel(hotelOpt.get());
        habitacion.setNumeroHabitacion(dto.getNumeroHabitacion());
        habitacion.setTipo(dto.getTipo());
        habitacion.setPrecio(dto.getPrecio());
        habitacion.setDisponible(dto.getDisponible());

        try {
            habitacionRepository.save(habitacion);
            return "Habitación actualizada correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al actualizar la habitación";
        }
    }
    // Método para eliminar una habitación por su ID
    //Método transaccional para eliminar habitación
    @Transactional
    public String eliminarHabitacion(Integer id) {
        if (!habitacionRepository.existsById(id)) {
            return "Habitación no encontrada";
        }
        try {
            // Primero elimina las reservas asociadas a esta habitación
            reservaRepository.deleteByHabitacionId(id);

            // Luego elimina la habitación
            habitacionRepository.deleteById(id);

            return "Habitación eliminada correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al eliminar la habitación";
        }
    }
}
