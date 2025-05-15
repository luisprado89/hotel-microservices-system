package com.hotel.reservas.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity // Indica que esta clase es una entidad JPA
@Data // Lombok generará automáticamente los métodos getters y setters
@NoArgsConstructor // Lombok generará automáticamente un constructor sin parámetros
@AllArgsConstructor // Lombok generará automáticamente un constructor con todos los parámetros
@Table(name = "habitacion") // Nombre de la tabla en la base de datos
public class Habitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "habitacion_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @Column(name = "numero_habitacion")
    private Integer numeroHabitacion;

    private String tipo; // Individual, Doble, Triple, Suite

    @Column(precision = 10, scale = 2)
    private BigDecimal precio;

    private Boolean disponible;
    //Esto le dice a Hibernate: “Cuando se elimine una Habitacion, elimina también sus Reservas”.
    @OneToMany(mappedBy = "habitacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> reservas;
}
