# Hotel Microservices System

Este proyecto implementa un sistema distribuido para la gestión de usuarios, reservas y comentarios en un entorno hotelero. Está diseñado siguiendo una arquitectura de microservicios utilizando **Spring Boot**, **Spring Cloud**, **Eureka**, **API Gateway**, y se apoya en bases de datos **MySQL** y **MongoDB**.

El objetivo es permitir a los usuarios registrarse, iniciar sesión, realizar reservas en hoteles, consultar información y dejar comentarios, todo ello de forma desacoplada, escalable y gestionada por servicios independientes.

## Arquitectura del Sistema

El sistema está compuesto por cinco microservicios principales:

1. **usuarios**
    - **Descripción**: Servicio REST encargado del registro, validación, actualización y eliminación de usuarios.
    - **Puerto**: 8502
    - **Base de datos**: MySQL (`usuariosProyecto`)
    - **Rutas principales**:
        - `POST /usuarios` - Crear usuario
        - `GET /usuarios/{id}` - Obtener por ID
        - `POST /usuarios/validar` - Validar credenciales

2. **reservas**
    - **Descripción**: Servicio REST para gestionar hoteles, habitaciones y reservas. Valida al usuario consultando el microservicio `usuarios`.
    - **Puerto**: 8501
    - **Base de datos**: MySQL (`reservasProyecto`)
    - **Rutas principales**:
        - `POST /reservas/hotel` - Crear hotel
        - `POST /reservas/reserva` - Crear reserva
        - `GET /reservas/{id}` - Buscar reserva

3. **comentarios**
    - **Descripción**: Servicio GraphQL que permite a los usuarios dejar comentarios sobre hoteles.
    - **Puerto**: 8503
    - **Base de datos**: MongoDB (`comentariosProyecto`)
    - **Consultas GraphQL** disponibles a través del Gateway:
        - `query { comentariosPorHotel(hotelId: "1") { texto fecha } }`
        - `mutation { crearComentario(...) }`

4. **eureka-server**
    - **Descripción**: Servidor de descubrimiento Eureka que permite a todos los microservicios registrarse y descubrirse dinámicamente.
    - **Puerto**: 8500
    - **Interfaz Web**: `http://localhost:88500`

5. **gateway**
    - **Descripción**: API Gateway basado en Spring Cloud Gateway. Rutea las peticiones a los servicios REST (`usuarios`, `reservas`) y a GraphQL (`comentarios`).
    - **Puerto**: 8080
    - **Ejemplos**:
        - `http://localhost:8080/usuarios`
        - `http://localhost:8080/reservas`
        - `http://localhost:8080/comentarios`

## Tecnologías utilizadas

- Spring Boot
- Spring Cloud (Eureka, Gateway)
- MySQL, MongoDB
- Maven multi-módulo
- GraphQL
- Postman (para pruebas)

## Puntuación máxima

| Servicio           | Puntos |
|--------------------|--------|
| Comentarios        | 1.50   |
| Usuarios           | 0.85   |
| Reservas           | 1.25   |
| Eureka Server      | 0.20   |
| API Gateway        | 0.20   |
| **Total**          | **4.00 puntos** |

Este proyecto implementa todos los módulos y funcionalidades requeridas, por lo que se opta a la **puntuación máxima de 4.00 puntos**.