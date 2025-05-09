package com.hotel.reservas.dto;

import com.hotel.reservas.model.Reserva;
import lombok.Data;

@Data
public class ReservaDTO {
    private Reserva reserva;
    private UsuarioDTO usuario;
}
