package com.hotel.reservas.dto;

import lombok.Data;

@Data
public class HotelDTO {
    // DTO usado para crear y actualizar hoteles
    // Usado en:
    // - POST /reservas/hotel → crearHotel()
    // - PATCH /reservas/hotel → actualizarHotel()
    private Integer id;      // Solo requerido al actualizar
    private String nombre;
    private String direccion;
    private UsuarioDTO usuario;  // Usa nombre y contrasena de UsuarioDTO, se usa para validar credenciales del usuario
}
