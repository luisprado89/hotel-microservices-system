package com.hotel.reservas.dto;

import lombok.Data;

@Data
public class HotelDTO {
    private Integer id;
    private String nombre;
    private String direccion;
    private UsuarioDTO usuario;
}
