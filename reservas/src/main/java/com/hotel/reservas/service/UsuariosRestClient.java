package com.hotel.reservas.service;

import com.hotel.reservas.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

@Service // Anotación que indica que esta clase es un servicio
public class UsuariosRestClient { // Clase que se encarga de validar las credenciales de un usuario
    // Inyección de dependencias para el cliente REST
    private final RestTemplate restTemplate = new RestTemplate();
    // URL base del servicio de usuarios
    @Value("${servicio.usuarios.url:http://localhost:8502}")
    private String baseUrl;
    // Constructor de la clase
    public boolean validarCredenciales(String nombre, String contrasena) {
        String url = baseUrl + "/usuarios/validar"; // URL del endpoint para validar usuarios
        UsuarioDTO usuarioDTO = new UsuarioDTO();// Objeto que representa al usuario
        usuarioDTO.setNombre(nombre);// Nombre del usuario
        usuarioDTO.setContrasena(contrasena);// Contraseña del usuario

        try {// Intenta enviar la solicitud al servicio de usuarios
            ResponseEntity<Boolean> response = restTemplate.postForEntity(
                    url, usuarioDTO, Boolean.class// Objeto que representa la respuesta del servicio
            );
            return Boolean.TRUE.equals(response.getBody()); // Devuelve true si la respuesta es verdadera
        } catch (Exception e) { // Captura cualquier excepción que ocurra durante la solicitud
            System.out.println("Error al validar usuario: " + e.getMessage());
            return false;   // Devuelve false si ocurre un error
        }
    }

    // Obtener ID del usuario por su nombre (GET /usuarios/info/nombre/{nombre})
    public Integer obtenerIdUsuarioPorNombre(String nombre) {
        String url = baseUrl + "/usuarios/info/nombre/" + nombre;

        try {
            return restTemplate.getForObject(url, Integer.class);
        } catch (Exception e) {
            System.out.println("Error al obtener ID del usuario: " + e.getMessage());
            return null;
        }
    }
}
