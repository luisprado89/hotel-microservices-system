package com.hotel.comentarios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ComentarioResponse {
    private String nombreHotel;
    private Integer reservaId;
    private Double puntuacion;
    private String comentario;
}
