# Proyecto 501 — Sistema de Reservas y Comentarios
*(Enunciado completo incluido para lectura sin conexión)*

---

## Proyecto · Entrega

El nombre del fichero será **501.zip** y contendrá el proyecto con cada uno de los módulos de cada microservicio que se haya implementado (5 si se implementan todos) y un fichero **README.txt** o **README.md**.

El fichero README contendrá un breve resumen de los módulos que se han implementado así como la puntuación máxima a la que se opta.

Será necesario usar Maven para la creación de los módulos tal y como se explica en este apartado

### A tener en cuenta

- Si un servicio no cumple las características técnicas descritas será considerado como fallido y su puntuación será de un 0 en su apartado, independientemente de que cumpla con todas las funcionalidades.
- Estará permitido añadir funcionalidades extra en los servicios, siendo obligatorias las descritas en las especificaciones de cada Servicio.
- Deberá utilizarse el método HTTP indicado en las especificaciones con la funcionalidad del método en cuestión.
- Deberá nombrarse los métodos tal y como se indican en las especificaciones.
- Deberá seguirse la estructura de microservicio (Controlador, Servicio, Repositorio), organizándolos en paquetes cuando sea necesario.
- No será corregido y su puntuación será de 0, independientemente de lo que se haya hecho, cualquier código que:
    - Se detecte que es una copia
    - No compile
    - No ejecute

---

## Arquitectura del Sistema

Se desea diseñar un sistema formado por microservicios que permitan gestionar un sistema de reservas y comentarios. Este sistema estará formado por los siguientes microservicios los cuales se podrán comunicar entre ellos.

- Servicio de Reservas de Hoteles
- Servicio de Usuarios
- Servicio de Comentarios y Comentarios
- Servidor Eureka
- API Gateway

A continuación, se detallan las características técnicas de cada uno de los microservicios.

### Sobre las rutas de aplicación

Todas las rutas que se indican en las especificaciones serán para irse acumulando una sobre otra.

Si se comenta que la ruta raíz del microservicio es `/microservicio` y después se comenta que un método se ejecuta sobre la ruta `/metodo`, la ruta resultante será `/microservicio/metodo`.

---

## Servidor Eureka

- El servidor Eureka atenderá las peticiones de conexión en el puerto **8500**.
- Su tarea será la de permitir el registro de los servicios que forman el sistema.

---

## API Gateway

Será el punto de entrada al sistema. A través de él un usuario podrá acceder al resto de microservicios del sistema.

**Características técnicas**
- El servicio se llamará **gateway**.
- Estará ejecutándose en el puerto **8080**.
- Hará uso del servidor Eureka y no tendrá porque aparecer registrado en él.

**Funcionalidades**
- Será el punto de entrada y de utilización de las funcionalidades del sistema.
- Todas las peticiones de los servicios deberán poder ser accesibles y funcionales a través de este sistema.
- Deberá permitir utilizar las API Rest de los servicios que lo implementen y GraphIQL del servicio que lo implemente.

---

## Microservicio de Usuarios

Se encargará de todas las gestiones relacionadas con los usuarios del sistema.

### Características técnicas
- El servicio se llamará **usuarios**.
- Implementará una **API Rest** para la comunicación entre el exterior y el servicio.
- La API tendrá como ruta raíz **/usuarios** y, a partir de ella, se irán construyendo el resto de rutas.
- El servicio estará ejecutándose en el puerto **8502**.
- Tendrá configurado **Hibernate** en modo **validate** para no pisar la estructura de la siguiente base de datos.
- Utilizará una base de datos **MySQL** llamada **usuariosProyecto**.

#### `usuariosProyecto.sql`
```sql
drop database if exists usuariosProyecto;
create database usuariosProyecto;

use usuariosProyecto;

CREATE TABLE usuario (
    usuario_id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    correo_electronico VARCHAR(255),
    direccion VARCHAR(255),
    contrasena VARCHAR(255)
);

INSERT INTO usuario (nombre, correo_electronico, direccion, contrasena)
VALUES
  ('Juan Pérez', 'juan@example.com', 'Calle Principal 123', 'clave123'),
  ('María López', 'maria@example.com', 'Avenida Secundaria 456', 'secreto456'),
  ('Carlos Rodriguez', 'carlos@example.com', 'Plaza Central 789', 'password789'),
  ('Ana Martínez', 'ana@example.com', 'Calle del Parque 987', 'abcxyz');
```
### Funcionalidades del Microservicio de Usuarios


- **Crear un nuevo usuario (`crearUsuario`):**  
  - Se encargará de registrar un nuevo usuario en el sistema.
  - URL de ejecución `/registrar`
  - Método de consulta: `POST`
  - Recibirá un objeto con la información del usuario (`nombre`, `correo_electrónico`, `direccion` y `contraseña`)
  - Devolverá una cadena indicando si la operación se completó correctamente o si hubo algún fallo.
- **Actualizar nuevo usuario (`actualizarUsuario`):**  
  - Se encargará de actualizar los datos un usuario del sistema.
  - URL de ejecución `/registrar`
  - Método de consulta: `PUT`
  - Recibirá un objeto con la información del usuario (`id`, `nombre`, `correo_electrónico`, `direccion` y `contraseña`)
  - Devolverá una cadena indicando si la operación se completó correctamente o si hubo algún fallo.
- **Eliminar usuario (`eliminarUsuario`):**  
  - Eliminará un usuario del sistema.
  - URL de ejecución la raíz del servicio
  - Método de consulta: `DELETE`
  - Recibirá un objeto con la información del usuario (`nombre` y `contraseña`)
  - Devolverá una cadena indicando si la operación se completó correctamente o si hubo algún fallo.
- **Validar usuario (`validarUsuario`):**  
  - Se encargará de comprobar si la tupla usuario y contraseña está en el sistema.
  - URL de ejecución `/validar`
  - Método de consulta: `POST`
  - Recibirá un objeto con la información del usuario (`nombre` y `contraseña`)
  - Devolverá un booleano indicando si es correcto o no.
- **Obtener el nombre de un usuario a partir de su identificador (`obtenerInfoUsuarioPorId`):**  
  - Se encargará de obtener qué nombre de usuario le corresponde a un identificador en particular.
  - URL de ejecución `/info/id/`
  - Método de consulta: `GET`
  - Recibirá un parámetro en la URL denominado `id`.
  - Devolverá un String con el nombre del usuario.
- **Obtener el ID de un usuario a partir de su nombre (`obtenerInfoUsuarioPorNombre`):**  
  - Se encargará de obtener qué ID de un usuario a partir de su nombre.
  - URL de ejecución /info/nombre/
  - Método de consulta: GET
  - Recibirá un parámetro en la URL denominado nombre.
  - Devolverá un String con el ID del usuario.
- **Comprobar si un usuario existe (`checkIfExist`):**  
  - Recibirá un ID de usuario y devolverá un booleano indicando si ese ID existe o no.
  - URL de ejecución `/checkIfExist/`
  - Método de consulta: `GET`
  - Recibirá un parámetro en la URL denominado id.
  - Devolverá un Booleano indicando si el usuario existe o no.

---

# Microservicio de Reservas

Este servicio será el encargado de llevar el registro de los datos de los hoteles, las habitaciones que cada hotel tiene y las reservas hechas por los usuarios.

## Características técnicas:

- El servicio se llamará `reservas`.
- Implementará una **API Rest** para la comunicación entre el exterior y el servicio.
- La ruta raíz de la API será `/reservas`.
- El servicio estará ejecutándose en el puerto `8501`.
- Tendrá configurado Hibernate en modo `validate` para no pisar la estructura de la siguiente base de datos.
- Utilizará una base de datos MySQL llamada `reservasProyecto` que tendrá el siguiente formato:

#### `reservasProyecto.sql`
```sql
drop database if exists reservasProyecto;
create database reservasProyecto;
use reservasProyecto;

-- Crear la tabla de Hoteles
CREATE TABLE hotel (
    hotel_id INT PRIMARY KEY AUTO_INCREMENT, 
    nombre VARCHAR(100),
    direccion VARCHAR(255)
);

-- Crear la tabla de Habitaciones
CREATE TABLE habitacion (
    habitacion_id INT PRIMARY KEY AUTO_INCREMENT,
    hotel_id INT,
    numero_habitacion INT,
    tipo VARCHAR(50), -- Individual, Doble, Triple, Suite
    precio DECIMAL(10, 2),
    disponible BOOLEAN,
    FOREIGN KEY (hotel_id) REFERENCES hotel(hotel_id)
);

-- Crear la tabla de Reservas
CREATE TABLE reserva (
    reserva_id INT PRIMARY KEY auto_increment,
    usuario_id INT,
    habitacion_id INT,
    fecha_inicio DATE,
    fecha_fin DATE,
    estado VARCHAR(20), -- Puede ser "Pendiente", "Confirmada" o "Cancelada".
    FOREIGN KEY (habitacion_id) REFERENCES habitacion(habitacion_id)
);

INSERT INTO hotel (hotel_id, nombre, direccion)
VALUES
  (1, 'Hotel A', 'Calle Principal 123'),
  (2, 'Hotel B', 'Avenida Secundaria 456'),
  (3, 'Hotel C', 'Plaza Central 789');

-- Insertar datos en la tabla Habitacion
INSERT INTO habitacion (habitacion_id, hotel_id, numero_habitacion, tipo, precio, disponible)
VALUES
  (1, 1, 101, 'Individual', 75.00, true),
  (2, 1, 102, 'Doble', 120.00, true),
  (3, 2, 103, 'Suite', 200.00, false),
  (4, 3, 104,  'Individual', 80.00, true),
  (5, 3, 105,  'Doble', 130.00, true);

-- Insertar datos en la tabla Reserva
INSERT INTO reserva (usuario_id, habitacion_id, fecha_inicio, fecha_fin, estado)
VALUES
  (1, 1, '2024-02-15', '2024-02-20', 'Confirmada'),
  (2, 2, '2024-03-10', '2024-03-15', 'Pendiente'),
  (3, 3, '2024-04-01', '2024-04-05', 'Cancelada'),
  (1, 4, '2024-05-15', '2024-05-20', 'Pendiente'),
  (2, 5, '2024-06-01', '2024-06-05', 'Confirmada');
```
## Funcionalidades:

> Salvo que se indique lo contrario, todas las peticiones de los siguientes métodos recibirán el `usuario` y `contraseña` del usuario.  
> Se validará dicha información frente al servicio `usuarios` y permitirán realizar la consulta si el nombre y contraseña concuerdan.



### Gestión de habitaciones:
Los métodos siguientes se ejecutarán añadiendo a la ruta raíz de la API la siguiente ruta **/habitacion**
- **Crear una nueva habitación (`crearHabitación`):**  
  - Se encargará de dar de alta una nueva habitación en un hotel.
  - URL de ejecución: la ruta raíz del gestor de habitaciones.
  - Método de consulta: `POST`.
  - Recibirá un objeto con la información de la habitación (`numeroHabitacion`, `tipo`, `precio` y `idHotel`)
  - Devolverá una cadena indicando si la operación se completó correctamente o si hubo algún fallo.
- **Actualizar información de una habitación (`actualizarHabitacion`):**  
  - Se encargará de actualizar los datos de una habitación en un hotel.
  - URL de ejecución: la ruta raíz del gestor de habitaciones.
  - Método de consulta: `PATCH`.
  - Recibirá un objeto con la información de la habitación (`id`, `numeroHabitacion`, `tipo`, `precio`, `idHotel` y `disponible`)
  - Devolverá una cadena indicando si la operación se completó correctamente o si hubo algún fallo.
- **Eliminar habitación (`eliminarHabitacion`):**  
  - Se encargará de eliminar los datos de una habitación.
  - URL de ejecución: la ruta raíz del gestor de habitaciones.
  - Método de consulta: `DELETE`.
  - Recibirá a través de la URL el identificador de la habitación
  - Devolverá una cadena indicando si la operación se completó correctamente o si hubo algún fallo.
---
### Gestión de hoteles


Los métodos siguientes se ejecutarán añadiendo a la ruta raíz de la API la siguiente ruta: **/hotel**.
- **Crear un nuevo hotel (`crearHotel`):**  
  - Se encargará de dar de alta un nuevo hotel.
  - URL de ejecución: la ruta raíz del gestor de hoteles.
  - Método de consulta: `POST`.
  - Recibirá un objeto con la información del hotel (`nombre` y `direccion`)
  - Devolverá una cadena indicando si la operación se completó correctamente o si hubo algún fallo.
- **Modificar información de un hotel (`actualizarHotel`):**  
  - Se encargará de actualizar los datos de un hotel.
  - URL de ejecución: la ruta raíz del gestor de hoteles.
  - Método de consulta: `PATCH`.
  - Recibirá un objeto con la información de la habitación (`id`, `nombre` y `direccion`)
  - Devolverá una cadena indicando si la operación se completó correctamente o si hubo algún fallo.
- **Eliminar hotel (`eliminarHotel`):**  
  - Se encargará de eliminar los datos de un hotel junto con todas sus habitaciones.
  - URL de ejecución: la ruta raíz del gestor de hoteles.
  - Método de consulta: `DELETE`.
  - Recibirá a través de la URL el identificador del hotel.
  - Devolverá una cadena indicando si la operación se completó correctamente o si hubo algún fallo.
- **Obtener el ID de un hotel a partir de su nombre (`obtenerIdApartirNombre`):**  
  - Buscará el ID asociado al nombre del hotel.
  - URL de ejecución: `/id`.
  - Método de consulta: `POST`.
  - Recibirá a través de la URL el `nombre` del hotel.
  - Devolverá una cadena indicando el ID del hotel en cuestión.
- **Obtener el nombre de un hotel a partir de su identificador (`obtenerNombreAPartirId`):**  
  - Buscará el nombre asociado a un ID.
  - URL de ejecución: `/nombre`.
  - Método de consulta: `POST`.
  - Recibirá a través de la URL el `id` del hotel.
  - Devolverá una cadena indicando el nombre del hotel en cuestión.
---
### Gestión de reservas

Los métodos siguientes se ejecutarán sobre la raíz del microservicio.
- **Crear reserva (`crearReserva`):**  
  - Se encargará de crear una nueva reserva.
  - URL de ejecución: la ruta raíz del microservicio.
  - Método de consulta: `POST`.
  - Recibirá un objeto con la información de la reserva (`fecha_inicio`, `fecha_fin` y `habitacion_id`)
  - Devolverá una cadena indicando si la operación se completó correctamente o si hubo algún fallo.
- **Cambiar estado de una reserva (`cambiarEstado`):**  
  - Se encargará de modificar el estado de una reserva.
  - URL de ejecución: la ruta raíz del microservicio.
  - Método de consulta: `PATCH`.
  - Recibirá un objeto con la información de la reserva (`reserva_id` y `estado`)
  - Devolverá una cadena indicando si la operación se completó correctamente o si hubo algún fallo.
- **Listar reservas del usuario (`listarReservasUsuario`):**  
  - Se encargará de listar las reservas que están asociadas al usuario.
  - URL de ejecución: la ruta raíz del microservicio.
  - Método de consulta: `GET`.
  - Solo recibirá la información de usuario y contraseña.
  - Devolverá una lista con la información de las reservas (`fecha_inicio`, `fecha_fin` y `habitacion_id`).
- **Listar reservas según estado  (`listarReservasSegunEstado`):**  
  - Se encargará de listar todas las reservas que tengan un determinado estado independientemente del usuario que la haya hecho.
  - URL de ejecución: la ruta raíz del microservicio.
  - Método de consulta: `GET`.
  - Recibirá a través de la URL el `estado` de la habitación.
  - Devolverá una lista con la información de las reservas (`fecha_inicio`, `fecha_fin` y `habitacion_id`).

- **Comprobar reserva (`checkReserva`):**  
  - Se encargará de validar si una reserva está asociada a un usuario y a un hotel en concreto.
  - URL de ejecución: `/check`
  - Método de consulta: `GET`.
  - Recibirá a través de la URL el `idUsuario`, el `idHotel` y el `idReserva`.
  - Devolverá una booleano indicando si existe o si no.
  - Este método no recibirá la información de usuario y contraseña.


---
# Microservicio de Comentarios

Este servicio se encargará de gestionar los comentarios que los usuarios hacen sobre las reservas.

### Características técnicas:

- El servicio se llamará **comentarios**.
- Implementará una **API GraphQL** para la comunicación entre el exterior y el servicio.
  - Deberá habilitarse **GraphIQL** para la realización de peticiones.
  - **GraphIQL** deberá utilizar el endpoint `/comentarios` para resolver las peticiones.
- El servicio estará ejecutándose en el **puerto 8503**.
- Utilizará una base de datos **MongoDB** llamada `comentariosProyecto`.
- La base de datos `comentariosProyecto` tendrá una **colección** llamada `comentarios` donde los documentos tendrán la siguiente estructura:
#### `comentarios.json`
```sql
{
    "_id": ObjectId(),
    "usuarioId": 4,
    "hotelId": 1,
    "reservaId": 1,
    "puntuacion": 6.5,
    "comentario": "Excelente servicio",
    "fechaCreacion": "2024-01-01T12:00:00Z"
}
{
    "_id": ObjectId(),
    "usuarioId": 1,
    "hotelId": 3,
    "reservaId": 4,
    "puntuacion": 5,
    "comentario": "Excelente servicio",
    "fechaCreacion": "2024-01-01T12:00:00Z"
}
{
    "_id": ObjectId(),
    "usuarioId": 2,
    "hotelId": 1,
    "reservaId": 2,
    "puntuacion": 4,
    "comentario": "Buena experiencia",
    "fechaCreacion": "2024-01-02T12:30:00Z"
}

{
    "_id": ObjectId(),
    "usuarioId": 3,
    "hotelId": 2,
    "reservaId": 3,
    "puntuacion": 3,
    "comentario": "Podría mejorar",
    "fechaCreacion": "2024-01-03T13:00:00Z"
}
```

---

### Usuario y contraseña

Salvo que se indique lo contrario, todas las peticiones de las siguientes funcionalidades recibirán el `usuario` y `contraseña` del usuario. Se validará dicha información frente al servicio `usuarios` y permitirán realizar la consulta si el nombre y contraseña concuerdan.

---

### Información recibida e información almacenada

Salvo que se indique lo contrario, todos los parámetros que recibirán las siguientes funcionalidades serán nombres, es decir, recibirán el nombre del hotel y el nombre del usuario.  
Los únicos valores numéricos serán: la **puntuación** y el **identificador de la reserva**.

A la hora de almacenarlos en la base de datos se almacenarán los identificadores de los nombres por lo que habrá que hacer las llamadas a los servicios necesarios para hacer las conversiones.

---

### Funcionalidades:

#### Crear comentario (`crearComentario`)
- Se encargará de crear y almacenar el comentario de un usuario sobre una reserva en un determinado hotel.
- Recibirá un objeto con la información del comentario (`nombreHotel`, `id de reserva`, `puntuación` y `comentario`)
- Devolverá el mismo objeto recibido a modo de confirmación.
- Consultará el microservicio de reservas para obtener el **id de hotel** a partir del `nombreHotel`.
- Consultará el microservicio de usuarios para obtener el **id de usuario** a partir del nombre de usuario.
- Deberá comprobar frente al microservicio reservas (**método `checkReserva`**) si la combinación (`idUsuario - idHotel - idReserva`) existe antes de poder crear el comentario.
- Si el usuario ya hizo un comentario sobre esa combinación (`idUsuario - idHotel - idReserva`) **no se podrá realizar el comentario**.

#### Eliminar todos los comentarios (`eliminarComentarios`)
- Se encargará de eliminar todos los comentarios del sistema.
- Devolverá una cadena indicando si la operación se completó correctamente o si hubo algún fallo.
- Este método **no recibirá** la información de usuario y contraseña.

#### Eliminar un comentario de un usuario (`eliminarComentarioDeUsuario`)
- Se encargará de eliminar un determinado comentario hecho por un usuario.
- Recibirá un identificador indicando el comentario en cuestión a eliminar.
- Devolverá una cadena indicando si la operación se completó correctamente o si hubo algún fallo.

#### Listar comentarios de un hotel (`listarComentariosHotel`)
- Se encargará de mostrar todos los comentarios hechos por todos los usuarios a reservas de un determinado hotel.
- Recibirá un parámetro indicando el nombre del hotel.
- Devolverá una lista con los comentarios (`nombreHotel`, `reserva_id`, `puntuacion` y `comentario`).

#### Listar comentarios de un usuario (`listarComentariosUsuario`)
- Se encargará de mostrar todos los comentarios hechos por un usuario.
- Solo recibirá la información de usuario y contraseña.
- Devolverá una lista con los comentarios (`nombreHotel`, `reserva_id`, `puntuacion` y `comentario`).

#### Mostrar comentario de un usuario en una reserva (`mostrarComentarioUsuarioReserva`)
- Mostrará el comentario hecho por un usuario en una determinada reserva.
- Recibirá un parámetro indicando el identificador de la reserva.
- Devolverá una lista con los comentarios (`nombreHotel`, `reserva_id`, `puntuacion` y `comentario`).

#### Puntuación media de un hotel (`puntuacionMediaHotel`)
- Mostrará la puntuación media de un hotel a partir de la puntuación de sus reservas.
- Recibirá un parámetro indicando el nombre del hotel.
- Devolverá un `double` con el resultado.

#### Puntuación media de un usuario (`puntuacionesMediasUsuario`)
- Se encargará de mostrar la puntuación media de los comentarios hechos por un usuario en sus reservas.
- Solo recibirá la información de usuario y contraseña.
- Devolverá un `double` con el resultado.
