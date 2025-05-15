package com.hotel.reservas.dto;
/*
Crear un DTO (Data Transfer Object) permite enviar exactamente los datos
requeridos desde el cliente al backend (y viceversa), sin exponer directamente las entidades JPA.
 */
import lombok.Data;

import java.math.BigDecimal;

@Data
public class HabitacionDTO {
    // DTO usado para crear y actualizar habitaciones
    // Usado en:
    // - POST /reservas/habitacion → crearHabitacion()
    // - PATCH /reservas/habitacion → actualizarHabitacion()
    private Integer id;  // Solo requerido al actualizar
    private Integer numeroHabitacion;
    private String tipo;
    private BigDecimal precio;
    private Integer idHotel;
    private Boolean disponible;  // Solo requerido al actualizar
    private UsuarioDTO usuario;  // Usa nombre y contrasena de UsuarioDTO, se usa para validar credenciales del usuario
}
