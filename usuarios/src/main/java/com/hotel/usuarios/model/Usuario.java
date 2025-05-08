package com.hotel.usuarios.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // Indica que esta clase es una entidad JPA
@Data // Lombok generará automáticamente los métodos getters y setters
@NoArgsConstructor // Lombok generará automáticamente un constructor sin parámetros
@AllArgsConstructor // Lombok generará automáticamente un constructor con todos los parámetros
@Table(name = "usuario") // Nombre de la tabla en la base de datos
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Integer id;

    private String nombre;

    @Column(name = "correo_electronico")
    private String correoElectronico;

    private String direccion;

    private String contrasena;
    // Getters y setters generados automáticamente por Lombok
/*
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getCorreoElectronico() {
        return correoElectronico;
    }
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getContrasena() {
        return contrasena;
    }
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    */

}