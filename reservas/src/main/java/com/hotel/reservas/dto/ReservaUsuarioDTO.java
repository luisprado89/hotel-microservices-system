package com.hotel.reservas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ReservaUsuarioDTO {
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Integer habitacionId;
}
