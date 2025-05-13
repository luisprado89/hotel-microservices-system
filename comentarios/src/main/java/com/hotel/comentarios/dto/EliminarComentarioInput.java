package com.hotel.comentarios.dto;

import lombok.Data;

/** Si nos dice que no necesitamos autenticacion para eliminar un comentario, eliminamos esto
 * DTO usado para eliminar un comentario con autenticación del usuario.
 */
@Data
public class EliminarComentarioInput {
    private String id;              // ID del comentario a eliminar
    private String nombreUsuario;   // Nombre del usuario que elimina
    private String contrasena;      // Contraseña del usuario
}