package com.hotel.reservas.service;

import com.hotel.reservas.dto.ReservaUsuarioDTO;
import com.hotel.reservas.model.Habitacion;
import com.hotel.reservas.model.Reserva;
import com.hotel.reservas.repository.HabitacionRepository;
import com.hotel.reservas.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Anotación que indica que esta clase es un servicio
public class ReservaService { // Clase de servicio para manejar la lógica de negocio relacionada con las reservas
    // Inyección de dependencias para el repositorio de reservas
    @Autowired
    private ReservaRepository reservaRepository;
    // Inyección de dependencias para el repositorio de habitaciones
    @Autowired
    private HabitacionRepository habitacionRepository;
    // Inyección de dependencias para el cliente REST de usuarios
    @Autowired
    private UsuariosRestClient usuariosRestClient;
    // Constructor de la clase
    public String crearReserva(Reserva reserva) {
        Optional<Habitacion> habitacion = habitacionRepository.findById(reserva.getHabitacion().getId());
        if (habitacion.isEmpty() || !habitacion.get().getDisponible()) {
            return "Habitación no disponible";
        }

        reserva.setHabitacion(habitacion.get());
        try {
            reservaRepository.save(reserva);
            return "Reserva creada correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al crear la reserva";
        }
    }

    // Método para cambiar el estado de una reserva
    public String cambiarEstado(Integer reservaId, String estado) {
        Optional<Reserva> reserva = reservaRepository.findById(reservaId);
        if (reserva.isEmpty()) {
            return "Reserva no encontrada";
        }
        reserva.get().setEstado(estado);
        try {
            reservaRepository.save(reserva.get());
            return "Estado de reserva actualizado";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al cambiar estado";
        }
    }


    // Método para listar reservas por ID de usuario
    public List<ReservaUsuarioDTO> listarReservasUsuario(String nombreUsuario) {
        Integer usuarioId = usuariosRestClient.obtenerIdUsuarioPorNombre(nombreUsuario);
        if (usuarioId == null) {
            return List.of();
        }

        return reservaRepository.findByUsuarioId(usuarioId).stream()
                .map(reserva -> new ReservaUsuarioDTO(
                        reserva.getFechaInicio(),
                        reserva.getFechaFin(),
                        reserva.getHabitacion().getId()))
                .toList();
    }
    // Método para listar reservas por estado
    public List<ReservaUsuarioDTO> listarReservasSegunEstado(String estado) {
        return reservaRepository.findByEstado(estado);
    }
    // Método para verificar si una reserva existe por ID de usuario, ID de hotel y ID de reserva
    public boolean checkReserva(Integer usuarioId, Integer hotelId, Integer reservaId) {
        return reservaRepository.existsByIdAndUsuarioIdAndHabitacion_Hotel_Id(reservaId, usuarioId, hotelId);
    }
}
