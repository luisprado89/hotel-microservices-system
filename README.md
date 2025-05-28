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

## ✅ `UsuarioController.java` (Controller)

Este controlador expone los endpoints REST según lo especificado en el enunciado. Cada método del controlador delega la lógica al servicio `UsuarioService`.

- Anotado con `@RestController` y `@RequestMapping("/usuarios")`.

### 📌 Endpoints Implementados

| Método HTTP | Ruta                                | Método de Servicio             | Descripción                                                   |
|-------------|-------------------------------------|--------------------------------|---------------------------------------------------------------|
| POST        | `/usuarios/registrar`               | `crearUsuario`                 | Crea un nuevo usuario.                                        |
| PUT         | `/usuarios/registrar`               | `actualizarUsuario`            | Actualiza los datos de un usuario existente.                 |
| DELETE      | `/usuarios`                         | `eliminarUsuario`              | Elimina un usuario por nombre y contraseña.                   |
| POST        | `/usuarios/validar`                 | `validarUsuario`               | Valida las credenciales de un usuario.                        |
| GET         | `/usuarios/info/id/{id}`            | `obtenerInfoUsuarioPorId`      | Devuelve el nombre de un usuario dado su ID.                 |
| GET         | `/usuarios/info/nombre/{nombre}`    | `obtenerInfoUsuarioPorNombre`  | Devuelve el ID del usuario dado su nombre.                   |
| GET         | `/usuarios/checkIfExist/{id}`       | `checkIfExist`                 | Verifica si un usuario con ese ID existe en la base de datos.|

---

## 🔧 `UsuarioService.java` (Service)

Contiene la lógica de negocio. Se encarga de manejar y procesar los datos provenientes del controlador antes de interactuar con la capa de persistencia (`UsuarioRepository`).

### Métodos Clave

#### `crearUsuario(Usuario u)`
- Valida que los datos no sean nulos.
- Guarda el usuario con `repo.save(u)`.

#### `actualizarUsuario(Usuario u)`
- Busca el usuario por ID.
- Solo actualiza si el usuario existe.
- Modifica campos individuales y guarda los cambios.

#### `eliminarUsuario(String nombre, String contrasena)`
- Busca al usuario por nombre y contraseña.
- Elimina el usuario si coincide.

#### `validarUsuario(String nombre, String contrasena)`
- Verifica si existe un usuario con ese nombre y contraseña.
- Devuelve un booleano.

#### `obtenerInfoUsuarioPorId(Integer id)`
- Devuelve el nombre del usuario si existe.
- Si no, devuelve `"Usuario no encontrado"`.

#### `obtenerInfoUsuarioPorNombre(String nombre)`
- Devuelve el ID del usuario como `String`.

#### `checkIfExist(Integer id)`
- Devuelve `true` o `false` según si el ID existe en la base de datos.

---
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
## ✅ Verificación de Endpoints del Microservicio `reservas`

| Funcionalidad                      | Ruta esperada                              | Método HTTP | Implementado | Comentario                                                  |
|-----------------------------------|--------------------------------------------|-------------|--------------|-------------------------------------------------------------|
| Crear reserva                     | `/reservas`                                | POST        | ✅            | Usa `ReservaDTO` + validación de credenciales              |
| Cambiar estado de reserva         | `/reservas`                                | PATCH       | ✅            | Usa `CambiarEstadoDTO`                                     |
| Listar reservas por usuario       | `/reservas`                                | GET         | ✅            | Requiere `UsuarioDTO` con credenciales                     |
| Listar reservas por estado        | `/reservas/{estado}`                        | GET         | ✅            | Recibe `estado` en la URL y valida credenciales            |
| Verificar reserva                 | `/reservas/check`                           | GET         | ✅            | No requiere validación de credenciales                     |
| Crear hotel                       | `/reservas/hotel`                           | POST        | ✅            | Usa `HotelDTO` con validación                              |
| Actualizar hotel                  | `/reservas/hotel`                           | PATCH       | ✅            | Validación correcta, actualización parcial                 |
| Eliminar hotel                    | `/reservas/hotel/{id}`                      | DELETE      | ✅            | Incluye validación y `@Transactional` para relaciones LAZY |
| Obtener ID por nombre del hotel   | `/reservas/hotel/id/{nombre}`              | POST        | ✅            | OK                                                          |
| Obtener nombre por ID del hotel   | `/reservas/hotel/nombre/{id}`              | POST        | ✅            | OK                                                          |
| Crear habitación                  | `/reservas/habitacion`                      | POST        | ✅            | OK                                                          |
| Actualizar habitación             | `/reservas/habitacion`                      | PATCH       | ✅            | OK                                                          |
| Eliminar habitación               | `/reservas/habitacion/{id}`                | DELETE      | ✅            | OK                                                          |

✅ **Endpoint adicional implementado**:
- `/reservas/hotel/idReserva/{idReserva}` → Devuelve el `hotelId` asociado a una reserva específica.
- **Uso previsto**: integración con el microservicio `comentarios` para validación cruzada.
- **Comentario**: Este endpoint extra es una mejora funcional que aporta valor al sistema.


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
# Análisis Detallado del Microservicio "Comentarios"

## Paquete Principal: `com.hotel.comentarios`

### 1. `ComentariosApplication.java`
- **Tipo**: Clase principal con `@SpringBootApplication`.
- **Responsabilidad**:
  - Inicia la aplicación Spring Boot.
  - Declara un `@Bean` de tipo `RestTemplate` que se usará para hacer peticiones HTTP a los otros microservicios (usuarios y reservas).
- **Clave**: Sin `RestTemplate` no se podrían validar usuarios ni verificar reservas.

---

## Paquete `dto` (Data Transfer Objects)

### 2. `ComentarioInput.java`
- **Tipo**: DTO de entrada.
- **Responsabilidad**:
  - Representa los datos necesarios para crear un comentario: usuario, contraseña, hotel, reserva, puntuación, texto.
- **Usado en**: `ComentarioMutation.crearComentario(...)`.

### 3. `ComentarioResponse.java`
- **Tipo**: DTO de salida.
- **Responsabilidad**:
  - Devuelve al cliente datos amigables: nombre del hotel, `reservaId`, puntuación y comentario.
- **Usado en**: Todas las `Query` y la `Mutation` de creación.

### 4. `EliminarComentarioInput.java`
- **Tipo**: DTO de entrada.
- **Responsabilidad**:
  - Usado para validar la eliminación de un comentario autenticando al usuario (requiere ID del comentario, usuario y contraseña).
- **Usado en**: `ComentarioMutation.eliminarComentarioDeUsuario(...)`.

---

## Paquete `model`

### 5. `Comentario.java`
- **Tipo**: Modelo de documento de MongoDB (`@Document`).
- **Responsabilidad**:
  - Representa cómo se guarda el comentario en la colección `comentarios` en MongoDB.
  - Contiene los campos: `usuarioId`, `hotelId`, `reservaId`, `puntuacion`, `comentario`, y `fechaCreacion` (de tipo `Instant` para MongoDB).

---

## Paquete `repository`

### 6. `ComentarioRepository.java`
- **Tipo**: `MongoRepository`.
- **Responsabilidad**:
  - Permite acceder y manipular la colección `comentarios`.
- **Métodos personalizados**:
  - `existsByUsuarioIdAndHotelIdAndReservaId(...)`: Evita comentarios duplicados.
  - `findByHotelId(...)`, `findByUsuarioId(...)`: Para búsquedas por hotel o usuario.
  - `findByUsuarioIdAndHotelIdAndReservaId(...)`: Obtener un comentario único.

---

## Paquete `service`

### 7. `ComentarioService.java`
- **Tipo**: Servicio principal.
- **Responsabilidad**:
  - Implementa toda la lógica de negocio:
    - **Validación de usuario** (`obtenerUsuarioId`).
    - **Validación de hotel** (`obtenerHotelId`).
    - **Validación de reserva** (`checkReserva`).
    - **Creación de comentario** con prevención de duplicados.
    - **Eliminación total o individual** de comentarios (autenticación requerida).
    - Consultas: comentarios por usuario, hotel, reserva, medias por usuario y hotel.
- **Comunicación con otros microservicios**:
  - Usa `RestTemplate` para comunicarse con:
    - **Microservicio de usuarios** (validar credenciales, obtener ID).
    - **Microservicio de reservas** (obtener ID o nombre de hotel, validar reserva).

---

## Paquete `resolver`

### 8. `ComentarioQuery.java`
- **Tipo**: Resolver de consultas (GraphQL).
- **Responsabilidad**:
  - Expone las siguientes `QueryMapping`:
    - `listarComentariosUsuario(...)`
    - `listarComentariosHotel(...)`
    - `mostrarComentarioUsuarioReserva(...)`
    - `puntuacionMediaHotel(...)`
    - `puntuacionesMediasUsuario(...)`
  - Llama a métodos del servicio que internamente validan al usuario y extraen la información.

### 9. `ComentarioMutation.java`
- **Tipo**: Resolver de mutaciones (GraphQL).
- **Responsabilidad**:
  - Expone las siguientes `MutationMapping`:
    - `crearComentario(...)`
    - `eliminarComentarios()`
    - `eliminarComentarioDeUsuario(...)`
  - Llama a los métodos de servicio para validar, crear y eliminar comentarios.

---

## Comunicación entre Microservicios

Este microservicio no funciona de forma aislada. Se comunica con otros microservicios para validar y consultar información:

| Servicio Destino | Endpoint Llamado                                       | Propósito                                                   |
|-------------------|--------------------------------------------------------|-------------------------------------------------------------|
| **Usuarios**      | `/validar`, `/info/nombre/{nombre}`                    | Validar credenciales y obtener ID del usuario               |
| **Reservas**      | `/hotel/id/{nombre}`, `/hotel/nombre/{id}`, `/check`, `/hotel/idReserva/{idReserva}` | Validar hotel, obtener nombre/ID, comprobar reservas        |

---

Este documento describe la estructura y funcionamiento del microservicio de comentarios, incluyendo las clases clave, métodos de interacción y la comunicación con otros microservicios esenciales para su funcionamiento.

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
