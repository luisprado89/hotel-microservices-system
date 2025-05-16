# Hotel Microservices System

Este proyecto implementa un sistema distribuido para la gestión de usuarios, reservas y comentarios en un entorno hotelero. Está diseñado siguiendo una arquitectura de microservicios utilizando **Spring Boot**, **Spring Cloud**, **Eureka**, **API Gateway**, y se apoya en bases de datos **MySQL** y **MongoDB**.

El objetivo es permitir a los usuarios registrarse, iniciar sesión, realizar reservas en hoteles, consultar información y dejar comentarios, todo ello de forma desacoplada, escalable y gestionada por servicios independientes.


## Arquitectura del Sistema

El sistema está compuesto por cinco microservicios principales:

### 1. **usuarios**

* **Descripción**: Servicio REST encargado del registro, validación, actualización y eliminación de usuarios.
* **Puerto**: 8502
* **Base de datos**: MySQL (`usuariosProyecto`)
* **Ruta raíz**: `/usuarios`
* **Rutas y funcionalidades**:

   * `POST /usuarios/registrar` - Crear nuevo usuario
   * `PUT /usuarios/registrar` - Actualizar usuario
   * `DELETE /usuarios` - Eliminar usuario (nombre y contraseña)
   * `POST /usuarios/validar` - Validar credenciales
   * `GET /usuarios/info/id/{id}` - Obtener nombre a partir de ID
   * `GET /usuarios/info/nombre/{nombre}` - Obtener ID a partir del nombre
   * `GET /usuarios/checkIfExist/{id}` - Comprobar si el ID existe

### 2. **reservas**

* **Descripción**: Servicio REST para gestionar hoteles, habitaciones y reservas. Valida al usuario mediante el microservicio `usuarios`.
* **Puerto**: 8501
* **Base de datos**: MySQL (`reservasProyecto`)
* **Ruta raíz**: `/reservas`
* **Funcionalidades**:

  #### Gestión de habitaciones `/reservas/habitacion`

   * `POST` - Crear habitación
   * `PATCH` - Actualizar habitación
   * `DELETE /{id}` - Eliminar habitación por ID

  #### Gestión de hoteles `/reservas/hotel`

   * `POST` - Crear hotel
   * `PATCH` - Actualizar hotel
   * `DELETE /{id}` - Eliminar hotel y sus habitaciones
   * `POST /id/{nombre}` - Obtener ID a partir del nombre
   * `POST /nombre/{id}` - Obtener nombre a partir del ID

  #### Gestión de reservas `/reservas`

   * `POST` - Crear reserva
   * `PATCH` - Cambiar estado de reserva
   * `GET` - Listar reservas del usuario
   * `GET /estado/{estado}` - Listar reservas por estado
   * `GET /check?idUsuario=&idHotel=&idReserva=` - Validar combinación reserva

### 3. **comentarios**

* **Descripción**: Servicio GraphQL que permite a los usuarios dejar comentarios sobre hoteles.
* **Puerto**: 8503
* **Base de datos**: MongoDB (`comentariosProyecto`)
* **Endpoint GraphQL**: `/comentarios`
* **Consola GraphiQL**: habilitada en `/graphiql`
* **Colección**: `comentarios`
## Usuario y Contraseña

En este proyecto, todas las funcionalidades que requieren autenticación solicitan al usuario su nombre de usuario y contraseña. Estas credenciales son validadas a través del microservicio **usuarios**, que verifica su autenticidad. Si la validación es exitosa, el sistema permite que el usuario realice la operación solicitada.

---

## Información Recibida e Información Almacenada

Las operaciones en el sistema reciben parámetros como **nombres** (por ejemplo, el nombre de un hotel o un usuario). Sin embargo, algunos datos, como las **puntuaciones** de los comentarios y el **ID de las reservas**, son numéricos.

### Conversión de nombres a identificadores

Los nombres de hoteles y usuarios recibidos en las consultas se convierten a sus respectivos **identificadores** antes de ser almacenados en la base de datos. Para esto, se realizan llamadas a los microservicios correspondientes para obtener los **IDs** necesarios, asegurando que, aunque las interacciones sean a través de nombres legibles, internamente se manejen los identificadores únicos que el sistema requiere.

#### Funcionalidades:

* `crearComentario`: Crea y almacena un comentario. Valida usuario, hotel y reserva. Impide duplicados.

* `eliminarComentarios`: Elimina todos los comentarios. No requiere autenticación.

* `eliminarComentarioDeUsuario`: Elimina un comentario de un usuario autenticado.

* `listarComentariosHotel`: Lista todos los comentarios sobre un hotel.

* `listarComentariosUsuario`: Lista todos los comentarios hechos por un usuario.

* `mostrarComentarioUsuarioReserva`: Muestra el comentario de un usuario en una reserva específica.

* `puntuacionMediaHotel`: Muestra la puntuación media de un hotel.

* `puntuacionesMediasUsuario`: Muestra la puntuación media de un usuario.

* **Consultas GraphQL (Query)**:

   * `listarComentariosUsuario(nombreUsuario, contrasena)`
   * `listarComentariosHotel(nombreHotel, nombreUsuario, contrasena)`
   * `mostrarComentarioUsuarioReserva(nombreUsuario, contrasena, reservaId)`
   * `puntuacionMediaHotel(nombreHotel, nombreUsuario, contrasena)`
   * `puntuacionesMediasUsuario(nombreUsuario, contrasena)`

* **Mutaciones GraphQL (Mutation)**:

   * `crearComentario(input: ComentarioInput)`
   * `eliminarComentarios`
   * `eliminarComentarioDeUsuario(input: EliminarComentarioInput)`

* **Esquema GraphQL**:

```graphql
# Tipo de dato que representa la respuesta cuando se devuelve un comentario.
type ComentarioResponse {
  nombreHotel: String
  reservaId: Int
  puntuacion: Float
  comentario: String
}

# Entrada para crear un nuevo comentario.
input ComentarioInput {
  nombreUsuario: String!
  contrasena: String!
  nombreHotel: String!
  reservaId: Int!
  puntuacion: Float!
  comentario: String!
}

# Consultas que se pueden hacer al sistema.
type Query {
  listarComentariosUsuario(nombreUsuario: String!, contrasena: String!): [ComentarioResponse]
  listarComentariosHotel(nombreHotel: String!, nombreUsuario: String!, contrasena: String!): [ComentarioResponse]
  mostrarComentarioUsuarioReserva(nombreUsuario: String!, contrasena: String!, reservaId: Int!): [ComentarioResponse]
  puntuacionMediaHotel(nombreHotel: String!, nombreUsuario: String!, contrasena: String!): Float
  puntuacionesMediasUsuario(nombreUsuario: String!, contrasena: String!): Float
}

# Mutaciones que permiten modificar los datos.
type Mutation {
  crearComentario(input: ComentarioInput!): ComentarioResponse
  eliminarComentarios: String
  eliminarComentarioDeUsuario(input: EliminarComentarioInput!): String
}

# Entrada para eliminar un comentario validando al usuario.
input EliminarComentarioInput {
  id: ID!
  nombreUsuario: String!
  contrasena: String!
}
```

### 4. **eureka-server**

* **Descripción**: Servidor Eureka para registro y descubrimiento de microservicios.
* **Puerto**: 8500
* **Interfaz Web**: `http://localhost:8500`

### 5. **gateway**

* **Descripción**: API Gateway con Spring Cloud Gateway que enruta peticiones REST y GraphQL.
* **Puerto**: 8080
* **Funcionalidades**:

   * Ruteo completo hacia `/usuarios`, `/reservas`, `/comentarios`
   * Soporte para `/graphiql` a través de redirección y encabezados
   - **Ejemplos**:
      - `http://localhost:8080/usuarios`
      - `http://localhost:8080/reservas`
      - `http://localhost:8080/comentarios`
---

## Tecnologías utilizadas

* Spring Boot
* Spring Cloud (Eureka, Gateway)
* Maven multi-módulo
* MySQL y MongoDB
* GraphQL / GraphiQL
* Postman (para pruebas manuales)


---


---
# 📝 Evaluación de Servicios (Escalado a 10 puntos)

> Todas las funcionalidades están implementadas en el proyecto (✔️).  
> Las puntuaciones han sido escaladas correctamente (factor de escala: **2.5**).

| Funcionalidad                      | Servicio            | Estado | Puntos |
|-----------------------------------|---------------------|--------|--------|
| Cumple todas las especificaciones | Comentarios         | ✔️     | 0.375  |
| crearComentario                   | Comentarios         | ✔️     | 0.25   |
| eliminarComentarios               | Comentarios         | ✔️     | 0.25   |
| eliminarComentarioDeUsuario       | Comentarios         | ✔️     | 0.375  |
| listarComentariosHotel            | Comentarios         | ✔️     | 0.5    |
| listarComentariosUsuario          | Comentarios         | ✔️     | 0.5    |
| mostrarComentarioUsuarioReserva   | Comentarios         | ✔️     | 0.5    |
| puntuacionMediaHotel              | Comentarios         | ✔️     | 0.5    |
| puntuacionesMediasUsuario         | Comentarios         | ✔️     | 0.5    |
| **Subtotal Comentarios**          |                     |        | **3.75** |
| Cumple todas las especificaciones | Usuario             | ✔️     | 0.375  |
| crearUsuario                      | Usuario             | ✔️     | 0.25   |
| actualizarUsuario                 | Usuario             | ✔️     | 0.25   |
| eliminarUsuario                   | Usuario             | ✔️     | 0.25   |
| validarUsuario                    | Usuario             | ✔️     | 0.25   |
| obtenerInfoUsuarioPorId           | Usuario             | ✔️     | 0.25   |
| obtenerInfoUsuarioPorNombre       | Usuario             | ✔️     | 0.25   |
| checkIfExist                      | Usuario             | ✔️     | 0.25   |
| **Subtotal Usuario**              |                     |        | **2.125** |
| Cumple todas las especificaciones | Reservas            | ✔️     | 0.25   |
| crearHabitación                   | Reservas            | ✔️     | 0.125  |
| actualizarHabitacion              | Reservas            | ✔️     | 0.125  |
| eliminarHabitacion                | Reservas            | ✔️     | 0.125  |
| crearHotel                        | Reservas            | ✔️     | 0.125  |
| actualizarHotel                   | Reservas            | ✔️     | 0.125  |
| eliminarHotel                     | Reservas            | ✔️     | 0.125  |
| obtenerIdApartirNombre            | Reservas            | ✔️     | 0.375  |
| obtenerNombreAPartirId            | Reservas            | ✔️     | 0.375  |
| crearReserva                      | Reservas            | ✔️     | 0.125  |
| cambiarEstado                     | Reservas            | ✔️     | 0.125  |
| listarReservasUsuario             | Reservas            | ✔️     | 0.375  |
| listarReservasSegunEstado         | Reservas            | ✔️     | 0.375  |
| checkReserva                      | Reservas            | ✔️     | 0.375  |
| **Subtotal Reservas**             |                     |        | **3.125** |
| Eureka Server y Client            | Infraestructura     | ✔️     | 0.5    |
| API Gateway                       | Infraestructura     | ✔️     | 0.5    |
| **TOTAL GENERAL**                 |                     |        | **10.0** |
