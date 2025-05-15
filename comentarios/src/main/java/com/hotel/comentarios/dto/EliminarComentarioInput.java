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

    /**
      DTO usado para eliminar un comentario con autenticación del usuario.

      Este objeto se utiliza cuando un usuario desea eliminar un comentario
      que él mismo ha realizado, y se requiere validar sus credenciales antes
      de permitir la eliminación.

      Se usa en:

      1. GraphQL (comentarios.graphqls)
         - input EliminarComentarioInput
         - mutation eliminarComentarioAutenticado(input: EliminarComentarioInput!): String

      2. ComentarioMutation.java
         - Método:
             eliminarComentarioAutenticado(@Argument EliminarComentarioInput input)

      3. ComentarioService.java
         - Método:
             eliminarPorIdAutenticado(EliminarComentarioInput input)
         - En este método se realiza:
             - Validación del usuario mediante nombreUsuario y contrasena
             - Verificación de que el comentario pertenece al usuario autenticado
             - Eliminación del comentario si corresponde
     */
}