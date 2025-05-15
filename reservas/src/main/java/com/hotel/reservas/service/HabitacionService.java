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


    /**
     Endpoint @PostMapping("/habitacion") -> crearHabitacion  - HabitacionController
     -> Microservicio Reservas
     */
    // Metodo para crear una habitación
    public String crearHabitacion(HabitacionDTO dto) {
        // Validación de campos obligatorios
        if (dto.getNumeroHabitacion() == null ||
                dto.getTipo() == null ||
                dto.getPrecio() == null ||
                dto.getIdHotel() == null) {
            return "Error: Todos los campos (numeroHabitacion, tipo, precio, idHotel) son obligatorios.";
        }
        Optional<Hotel> hotelOpt = hotelRepository.findById(dto.getIdHotel());
        if (hotelOpt.isEmpty()) {
            return "Hotel no encontrado";
        }
        // Verificar si la habitación ya existe
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
//    public String actualizarHabitacion(HabitacionDTO dto) {
//        if (dto.getId() == null || !habitacionRepository.existsById(dto.getId())) {
//            return "Habitación no encontrada";
//        }
//
//        Optional<Hotel> hotelOpt = hotelRepository.findById(dto.getIdHotel());
//        if (hotelOpt.isEmpty()) {
//            return "Hotel no encontrado";
//        }
//
//        Habitacion habitacion = new Habitacion();
//        habitacion.setId(dto.getId());
//        habitacion.setHotel(hotelOpt.get());
//        habitacion.setNumeroHabitacion(dto.getNumeroHabitacion());
//        habitacion.setTipo(dto.getTipo());
//        habitacion.setPrecio(dto.getPrecio());
//        habitacion.setDisponible(dto.getDisponible());
//
//        try {
//            habitacionRepository.save(habitacion);
//            return "Habitación actualizada correctamente";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "Error al actualizar la habitación";
//        }
//    }


    /**
     Endpoint @PatchMapping("/habitacion") -> actualizarHabitacion  - HabitacionController
     -> Microservicio Reservas
     */
    // Método para actualizar una habitación que obliga a pasar todos los campos
    public String actualizarHabitacion(HabitacionDTO dto) {
        // Validar que todos los campos estén completos
        if (dto.getId() == null ||
                dto.getNumeroHabitacion() == null ||
                dto.getTipo() == null || dto.getTipo().isBlank() ||
                dto.getPrecio() == null ||
                dto.getIdHotel() == null ||
                dto.getDisponible() == null) {
            return "Error al actualizar la habitación: datos incompletos";
        }

        // Verificar si la habitación existe
        if (!habitacionRepository.existsById(dto.getId())) {
            return "Habitación no encontrada";
        }

        // Verificar si el hotel existe
        Optional<Hotel> hotelOpt = hotelRepository.findById(dto.getIdHotel());
        if (hotelOpt.isEmpty()) {
            return "Hotel no encontrado";
        }

        // Construir y guardar la entidad actualizada
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

    /**
     Endpoint @DeleteMapping("/{id}") -> eliminarHabitacion  - HabitacionController
     -> Microservicio Reservas
    */

    /** Metodo anotado de @Transactional porque:
    - Se accede a la relación de la habitación con sus reservas mediante habitacion.getReservas().
    - Esa relación probablemente esté configurada como 'lazy' (carga perezosa), y necesita una transacción activa para evitar errores como LazyInitializationException.
    - Al acceder al tamaño de la lista con .size(), se fuerza la carga de las reservas asociadas antes de eliminar.
    La transacción garantiza que si ocurre algún fallo durante la operación (por ejemplo, al eliminar),
    no se dejarán cambios inconsistentes en la base de datos.
    *

     */

    // Método para eliminar una habitación por su ID de esta
    @Transactional //Método transaccional para eliminar habitación
    public String eliminarHabitacion(Integer id) {
        Optional<Habitacion> habitacionOpt = habitacionRepository.findById(id);
        if (habitacionOpt.isEmpty()) {
            return "Habitación no encontrada";
        }

        try {
            Habitacion habitacion = habitacionOpt.get();
            habitacion.getReservas().size(); // fuerza carga de reservas
            habitacionRepository.delete(habitacion);
            return "Habitación eliminada correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al eliminar la habitación";
        }
    }
}
