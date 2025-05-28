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