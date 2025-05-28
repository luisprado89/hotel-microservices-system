# UsuarioRepository - Métodos de Acceso y Operaciones

Este archivo README proporciona una descripción detallada de los métodos disponibles en el repositorio `UsuarioRepository` para gestionar las operaciones CRUD, consultas personalizadas y lógica de negocio con la entidad `Usuario` en una aplicación Spring Boot utilizando JPA.

---

## 📘 **Métodos de UsuarioRepository**

Estos métodos son parte de la interfaz `UsuarioRepository` y proporcionan funciones estándar para la gestión de usuarios.

| **Método**                      | **Descripción**                                   | **Ejemplo de uso**                                              |
|----------------------------------|---------------------------------------------------|-----------------------------------------------------------------|
| `save(Usuario u)`                | Inserta o actualiza un usuario.                   | `usuarioRepository.save(new Usuario(null, "Luis", ...))`        |
| `findById(Integer id)`           | Busca un usuario por ID.                          | `usuarioRepository.findById(1)`                                 |
| `findAll()`                       | Lista todos los usuarios.                         | `usuarioRepository.findAll()`                                   |
| `deleteById(Integer id)`         | Elimina un usuario por ID.                        | `usuarioRepository.deleteById(3)`                               |
| `existsById(Integer id)`         | Verifica si existe un usuario con el ID dado.     | `usuarioRepository.existsById(2)`                               |
| `count()`                        | Devuelve el total de usuarios.                    | `usuarioRepository.count()`                                     |
| `delete(Usuario u)`              | Elimina el usuario dado.                          | `usuarioRepository.delete(usuario)`                             |
| `findAllById(List<Integer> ids)` | Busca múltiples usuarios por sus ID.              | `usuarioRepository.findAllById(List.of(1, 2))`                  |

---

## 🧩 **Métodos personalizados de UsuarioRepository**

Métodos personalizados definidos en la interfaz `UsuarioRepository` para realizar consultas específicas sobre los usuarios.

| **Método**                                  | **Descripción**                                     | **Ejemplo de uso**                                              |
|--------------------------------------------|-----------------------------------------------------|-----------------------------------------------------------------|
| `findByNombreAndContrasena(String n, String c)` | Busca un usuario por nombre y contraseña.          | `usuarioRepository.findByNombreAndContrasena("Luis", "abc123")` |
| `findByNombre(String n)`                    | Busca un usuario por nombre.                       | `usuarioRepository.findByNombre("Ana")`                         |
| `existsById(Integer id)`                    | Verifica si el ID existe (heredado pero usado explícitamente). | `usuarioRepository.existsById(1)` |

---

## 🔹 **Métodos CRUD heredados de JpaRepository**

`UsuarioRepository` extiende `JpaRepository`, lo que le permite utilizar los métodos estándar de JPA para la gestión de entidades `Usuario`.

| **Método**                                  | **Qué hace**                                           | **Ejemplo de uso**                                              |
|--------------------------------------------|--------------------------------------------------------|-----------------------------------------------------------------|
| `save(Usuario u)`                          | Guarda o actualiza un usuario.                         | `usuarioRepository.save(new Usuario(null, "Ana", "ana@mail.com", "Calle Falsa", "123"))` |
| `findById(Integer id)`                     | Busca un usuario por ID.                               | `usuarioRepository.findById(1)`                                 |
| `findAll()`                                | Devuelve todos los usuarios.                          | `List<Usuario> lista = usuarioRepository.findAll();`            |
| `deleteById(Integer id)`                   | Elimina un usuario por ID.                             | `usuarioRepository.deleteById(5);`                              |
| `delete(Usuario u)`                        | Elimina una instancia de usuario.                      | `usuarioRepository.delete(usuario);`                            |
| `deleteAll()`                              | Elimina todos los usuarios.                           | `usuarioRepository.deleteAll();`                                |
| `deleteAll(List<Usuario> usuarios)`        | Elimina usuarios específicos.                         | `usuarioRepository.deleteAll(listaUsuarios);`                   |
| `existsById(Integer id)`                   | Verifica si un ID existe.                              | `if (usuarioRepository.existsById(1)) { ... }`                  |
| `count()`                                  | Devuelve el número total de usuarios.                  | `long total = usuarioRepository.count();`                       |

---

## 🔹 **Métodos de consulta por lógica de nombres (Query Methods)**

Spring Data JPA genera automáticamente estos métodos de consulta basados en el nombre del método.

| **Método**                                  | **Qué hace**                                           | **Ejemplo de uso**                                              |
|--------------------------------------------|--------------------------------------------------------|-----------------------------------------------------------------|
| `findByNombre(String nombre)`               | Busca un usuario por nombre.                           | `usuarioRepository.findByNombre("Luis")`                        |
| `findByCorreoElectronico(String correo)`    | Busca un usuario por correo electrónico.               | `usuarioRepository.findByCorreoElectronico("ana@mail.com")`     |
| `findByNombreAndContrasena(String n, String c)` | Busca un usuario por nombre y contraseña.              | `usuarioRepository.findByNombreAndContrasena("Luis", "abc123")` |
| `findByDireccionContaining(String texto)`   | Busca usuarios cuya dirección contenga un texto específico. | `usuarioRepository.findByDireccionContaining("Avenida")`        |
| `findByNombreLike(String pattern)`          | Busca usuarios por patrón (LIKE).                      | `usuarioRepository.findByNombreLike("%an%")`                    |

---

## 🔹 **Métodos con ordenación**

Métodos para obtener resultados ordenados según un criterio especificado.

| **Método**                                  | **Qué hace**                                           | **Ejemplo de uso**                                              |
|--------------------------------------------|--------------------------------------------------------|-----------------------------------------------------------------|
| `findAll(Sort sort)`                       | Lista todos los usuarios ordenados.                    | `usuarioRepository.findAll(Sort.by("nombre").ascending())`      |
| `findAllByOrderByNombreAsc()`              | Devuelve usuarios ordenados por nombre (automático).   | `usuarioRepository.findAllByOrderByNombreAsc()`                 |

---

## **Ejemplos de uso de métodos CRUD:**

### `save(S entity)`
Guarda o actualiza un usuario.

```java
Usuario nuevo = new Usuario(null, "Luis", "luis@email.com", "Calle 123", "clave123");
usuarioRepository.save(nuevo);

```
### `findById(ID id)`
Busca un usuario por su ID.

```java
Optional<Usuario> usuario = usuarioRepository.findById(1);
usuario.ifPresent(u -> System.out.println(u.getNombre()));

```
### `findAll()`
Devuelve todos los usuarios.

```java
List<Usuario> lista = usuarioRepository.findAll();

```
### `existsById(ID id)`
Verifica si existe un usuario con un ID específico.

```java
boolean existe = usuarioRepository.existsById(5);
```
### `count()`
Devuelve el número total de usuarios.

```java
long total = usuarioRepository.count();
```
### `delete(Usuario entity)`
Elimina una instancia concreta de usuario.

```java
usuarioRepository.delete(usuarioEncontrado);
```
### `findAllById(Iterable<ID> ids)`
Devuelve una lista de usuarios que coinciden con los IDs dados.

```java
List<Integer> ids = Arrays.asList(1, 2, 3);
List<Usuario> usuarios = usuarioRepository.findAllById(ids);

```
### `✅ CONSEJO PRÁCTICO`
Puedes combinar nombres de campos para crear consultas sin escribir SQL. Ejemplos:

```java
Optional<Usuario> findByCorreoElectronicoAndContrasena(String correo, String contrasena);
List<Usuario> findByNombreContaining(String texto); // búsqueda parcial
List<Usuario> findByDireccionStartsWith(String prefijo);

```
### `🔹 MÉTODOS PERSONALIZADOS CON @Query (si lo necesitas)`

```java
@Query("SELECT u FROM Usuario u WHERE u.nombre = :nombre")
Optional<Usuario> buscarPorNombre(@Param("nombre") String nombre);


```

# Consultas con @Query usando JPQL y SQL Nativo

Ejemplos de cómo realizar consultas con `@Query` en Spring Data JPA utilizando tanto **JPQL** (Java Persistence Query Language) como **SQL nativo**, así como cómo realizar operaciones de **actualización** con `@Modifying` y `@Transactional`.

---

## 🔷 1. **Consultas con @Query usando JPQL (Java Persistence Query Language)**

**JPQL** es un lenguaje orientado a objetos que trabaja con las entidades y sus campos, no con tablas ni columnas directamente. Las consultas JPQL se escriben en términos de las entidades definidas en el modelo.

### 🔹 **Buscar por nombre exacto**

```java
@Query("SELECT u FROM Usuario u WHERE u.nombre = :nombre")
Optional<Usuario> buscarPorNombre(@Param("nombre") String nombre);
```
### 🔹 **Buscar por nombre y contraseña**

```java
@Query("SELECT u FROM Usuario u WHERE u.nombre = :nombre AND u.contrasena = :contrasena")
Optional<Usuario> buscarPorCredenciales(@Param("nombre") String nombre, @Param("contrasena") String contrasena);
```
### 🔹 **Buscar todos los usuarios con una dirección que contenga cierto texto**

```java
@Query("SELECT u FROM Usuario u WHERE u.direccion LIKE %:texto%")
List<Usuario> buscarPorDireccion(@Param("texto") String texto);
```
### 🔹 **Obtener solo los nombres de los usuarios**

```java
@Query("SELECT u.nombre FROM Usuario u")
List<String> obtenerNombres();
```

