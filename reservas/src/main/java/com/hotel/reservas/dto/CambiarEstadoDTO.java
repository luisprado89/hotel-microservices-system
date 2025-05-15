package com.hotel.reservas.dto;

import lombok.Data;

@Data
public class CambiarEstadoDTO {
    // DTO usado para cambiar el estado de una reserva existente
    // Usado en:
    // - PATCH /reservas → cambiarEstado()

    private Integer reservaId;  // ID de la reserva a modificar
    private String estado;       // Nuevo estado: Pendiente, Confirmada, Cancelada
    private UsuarioDTO usuario;  // Usa nombre y contrasena de UsuarioDTO, se usa para validar credenciales del usuario
}
