package com.hotel.reservas.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    //Esto le dice a Hibernate: “Cuando se elimine un Hotel, elimina también todas sus Habitaciones
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Habitacion> habitaciones;

}
