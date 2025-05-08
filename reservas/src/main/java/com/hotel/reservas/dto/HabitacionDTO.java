package com.hotel.reservas.dto;
/*
Crear un DTO (Data Transfer Object) permite enviar exactamente los datos
requeridos desde el cliente al backend (y viceversa), sin exponer directamente las entidades JPA.
 */
import lombok.Data;

import java.math.BigDecimal;

@Data
public class HabitacionDTO {
    private Integer id;
    private Integer numeroHabitacion;
    private String tipo;
    private BigDecimal precio;
    private Integer idHotel;
    private Boolean disponible;
    private UsuarioDTO usuario;  // Usa nombre y contrasena de UsuarioDTO
}
