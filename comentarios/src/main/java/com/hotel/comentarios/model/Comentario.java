package com.hotel.comentarios.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "comentarios")
public class Comentario {
    @Id
    private String id;
    private int usuarioId;
    private int hotelId;
    private int reservaId;
    private double puntuacion;
    private String comentario;
    private Instant fechaCreacion; //Se usa Instant para fechaCreacion, que corresponde al formato "2024-01-01T12:00:00Z" en MongoDB.
/*
Los tipos están alineados con el JSON del enunciado.
El nombre de la colección es "comentarios".
*/
}
