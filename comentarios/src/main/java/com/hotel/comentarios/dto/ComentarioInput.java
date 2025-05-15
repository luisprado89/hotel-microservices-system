package com.hotel.comentarios.dto;

import lombok.Data;
/*(DTO de entrada desde GraphQL)
Este DTO contiene los valores que se reciben desde GraphQL, y todos están alineados con el enunciado.

Se usará como argumento de entrada en crearComentario.
*/
@Data
public class ComentarioInput {
    // Campos que se reciben desde GraphQL
    private String nombreUsuario;
    private String contrasena;
    private String nombreHotel;
    private Integer reservaId;
    private Double puntuacion;
    private String comentario;

     /**    Campos que se reciben desde GraphQL
    1. GraphQL (comentarios.graphqls)
       - input ComentarioInput
       - mutation crearComentario(input: ComentarioInput!): ComentarioResponse
   2. ComentarioMutation.java
     - Método:
         @MutationMapping
         public ComentarioResponse crearComentario(@Argument ComentarioInput input)

   3. ComentarioService.java
     - Método:
        public ComentarioResponse crearComentario(ComentarioInput input)
    - Dentro de este método se realizan:
        - Validación de usuario (nombreUsuario, contrasena)
        - Obtención de ID del hotel (nombreHotel)
        - Validación de existencia de reserva (reservaId)
        - Validación de duplicidad de comentario
        - Persistencia del comentario en la colección MongoDB
 */

}
