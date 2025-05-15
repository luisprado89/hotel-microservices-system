package com.hotel.reservas.dto;

import com.hotel.reservas.model.Reserva;
import lombok.Data;

@Data
public class ReservaDTO {
    // DTO usado para crear una nueva reserva
    // Usado en:
    // - POST /reservas → crearReserva()
    private Reserva reserva; // Contiene fechaInicio, fechaFin y habitacion (con ID)
    private UsuarioDTO usuario;  // Usa nombre y contrasena de UsuarioDTO, se usa para validar credenciales del usuario
}
