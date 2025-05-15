package com.hotel.reservas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ReservaUsuarioDTO {
    // DTO usado para devolver información básica de una reserva al usuario
    // Usado en:
    // - GET /reservas → listarReservasUsuario()
    // - GET /reservas/{estado} → listarReservasSegunEstado()

    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Integer habitacionId;
}
