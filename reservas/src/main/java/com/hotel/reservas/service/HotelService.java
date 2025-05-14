package com.hotel.reservas.service;

import com.hotel.reservas.dto.HotelDTO;
import com.hotel.reservas.model.Hotel;
import com.hotel.reservas.repository.HabitacionRepository;
import com.hotel.reservas.repository.HotelRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
// Anotación que indica que esta clase es un servicio
@Service
public class HotelService {
    // Inyección de dependencias para el repositorio de hoteles
    @Autowired
    private HotelRepository hotelRepository;


    // Constructor de la clase
//    public String crearHotel(HotelDTO dto) {
//        Hotel hotel = new Hotel();
//        hotel.setNombre(dto.getNombre());
//        hotel.setDireccion(dto.getDireccion());
//
//        try {
//            hotelRepository.save(hotel);
//            return "Hotel creado correctamente";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "Error al crear el hotel";
//        }
//    }
    // Metodo para crear un nuevo hotel obligando a que el nombre y la dirección no estén vacíos
    public String crearHotel(HotelDTO dto) {
        // Validar que los campos no estén vacíos
        if (dto.getNombre() == null || dto.getNombre().isBlank() ||
                dto.getDireccion() == null || dto.getDireccion().isBlank()) {
            return "Error al crear el hotel: nombre o dirección incompletos";
        }

        Hotel hotel = new Hotel();
        hotel.setNombre(dto.getNombre());
        hotel.setDireccion(dto.getDireccion());

        try {
            hotelRepository.save(hotel);
            return "Hotel creado correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al crear el hotel";
        }
    }

    //PATCH puede implicar actualización parcial no esta obligado a actualizar todos los campos
    // Método para actualizar un hotel
    public String actualizarHotel(HotelDTO dto) {
        if (dto.getId() == null || !hotelRepository.existsById(dto.getId())) {
            return "Hotel no encontrado";
        }

        Hotel hotel = new Hotel();
        hotel.setId(dto.getId());
        hotel.setNombre(dto.getNombre());
        hotel.setDireccion(dto.getDireccion());

        try {
            hotelRepository.save(hotel);
            return "Hotel actualizado correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al actualizar el hotel";
        }
    }

    // Método para eliminar un hotel por su ID
    @Transactional // Anotación que indica que este método es transaccional
    public String eliminarHotel(Integer idHotel) {
        Optional<Hotel> hotelOpt = hotelRepository.findById(idHotel);
        if (hotelOpt.isEmpty()) {
            return "Hotel no encontrado";
        }

        try {
            Hotel hotel = hotelOpt.get();

            // Forzar la carga de las habitaciones (y por cascada, sus reservas)
            hotel.getHabitaciones().size();

            hotelRepository.delete(hotel);
            return "Hotel eliminado correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al eliminar el hotel";
        }
    }

    // Método para obtener el ID de un hotel a partir de su nombre
    public String obtenerIdApartirNombre(String nombreHotel) {
        Optional<Hotel> hotelOpt = hotelRepository.findByNombre(nombreHotel);
        return hotelOpt.map(h -> String.valueOf(h.getId()))
                .orElse("Hotel no encontrado");
    }
    // Método para obtener el nombre de un hotel a partir de su ID
    public String obtenerNombreAPartirId(Integer id) {
        Optional<Hotel> hotelOpt = hotelRepository.findById(id);
        return hotelOpt.map(Hotel::getNombre)
                .orElse("Hotel no encontrado");
    }
}
