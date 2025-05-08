package com.hotel.reservas.model;

import jakarta.persistence.*;
import lombok.*;

@Entity // Indica que esta clase es una entidad JPA
@Data // Lombok generará automáticamente los métodos getters y setters
@NoArgsConstructor // Lombok generará automáticamente un constructor sin parámetros
@AllArgsConstructor // Lombok generará automáticamente un constructor con todos los parámetros
@Table(name = "hotel")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hotel_id")
    private Integer id;

    private String nombre;

    private String direccion;
}
