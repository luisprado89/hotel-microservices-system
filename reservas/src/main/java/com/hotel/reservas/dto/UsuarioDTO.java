package com.hotel.reservas.dto;
/*
Crear un DTO (Data Transfer Object) permite enviar exactamente los datos
requeridos desde el cliente al backend (y viceversa), sin exponer directamente las entidades JPA.
 */
import lombok.Data;

@Data // Lombok generará automáticamente los métodos getters y setters
public class UsuarioDTO { // DTO para la entidad Usuario
    // DTO utilizado para enviar las credenciales de usuario (nombre y contraseña)
    // Se usa en la mayoría de los endpoints para autenticar al usuario
    private String nombre;
    private String contrasena;
}
