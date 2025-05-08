package com.hotel.reservas.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity // Indica que esta clase es una entidad JPA
@Data // Lombok generará automáticamente los métodos getters y setters
@NoArgsConstructor // Lombok generará automáticamente un constructor sin parámetros
@AllArgsConstructor // Lombok generará automáticamente un constructor con todos los parámetros
@Table(name = "reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserva_id")
    private Integer id;

    @Column(name = "usuario_id")
    private Integer usuarioId;

    @ManyToOne
    @JoinColumn(name = "habitacion_id", nullable = false)
    private Habitacion habitacion;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    private String estado; // Pendiente, Confirmada, Cancelada
}
