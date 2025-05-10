package com.hotel.comentarios.dto;

import lombok.Data;
/*(DTO de entrada desde GraphQL)
Este DTO contiene los valores que se reciben desde GraphQL, y todos están alineados con el enunciado.

Se usará como argumento de entrada en crearComentario.
*/
@Data
public class ComentarioInput {
    private String nombreUsuario;
    private String contrasena;
    private String nombreHotel;
    private Integer reservaId;
    private Double puntuacion;
    private String comentario;
}
