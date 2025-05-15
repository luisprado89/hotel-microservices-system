package com.hotel.comentarios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ComentarioResponse {
    private String nombreHotel; // Nombre del hotel (se resuelve con hotelId al consultar)
    private Integer reservaId; // ID de la reserva comentada
    private Double puntuacion;
    private String comentario;

    /**
      DTO de salida hacia GraphQL para representar un comentario.

      Este objeto es el que se devuelve al cliente después de realizar una consulta o crear un comentario.
      Contiene los datos visibles que se deben mostrar al usuario.

      Se usa en:

      1. GraphQL (comentarios.graphqls)
         - type ComentarioResponse
         - Se devuelve en:
             - crearComentario
             - listarComentariosUsuario
             - listarComentariosHotel
             - mostrarComentarioUsuarioReserva

      2. ComentarioMutation.java
         - Método:
             crearComentario(...) → Devuelve un ComentarioResponse

      3. ComentarioQuery.java
         - Métodos:
             listarComentariosUsuario(...) → List<ComentarioResponse>
             listarComentariosHotel(...) → List<ComentarioResponse>
             mostrarComentarioUsuarioReserva(...) → List<ComentarioResponse>

      4. ComentarioService.java
         - Método:
             crearComentario(...) → construye y devuelve un ComentarioResponse
         - También se construye a partir de los datos obtenidos de la base de datos en:
             - obtenerComentariosPorUsuario(...)
             - obtenerComentariosPorHotel(...)
             - obtenerComentarioDeReserva(...)
     */
}
