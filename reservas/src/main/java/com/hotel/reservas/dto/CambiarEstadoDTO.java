package com.hotel.reservas.dto;

import lombok.Data;

@Data
public class CambiarEstadoDTO {
    private Integer reservaId;
    private String estado;
    private UsuarioDTO usuario;
}
