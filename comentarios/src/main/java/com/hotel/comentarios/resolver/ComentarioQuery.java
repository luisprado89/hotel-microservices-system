package com.hotel.comentarios.resolver;

import com.hotel.comentarios.dto.ComentarioResponse;
import com.hotel.comentarios.service.ComentarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ComentarioQuery {

    private final ComentarioService comentarioService;

    /**
     * Query: listarComentariosUsuario
     *
     * Devuelve todos los comentarios hechos por un usuario autenticado.
     *
     * Llama a: ComentarioService.obtenerComentariosPorUsuario(...)
     */
    @QueryMapping
    public List<ComentarioResponse> listarComentariosUsuario(@Argument String nombreUsuario, @Argument String contrasena) {
        return comentarioService.obtenerComentariosPorUsuario(nombreUsuario, contrasena);
    }

    /**
     * Query: listarComentariosHotel
     *
     * Devuelve todos los comentarios hechos a un hotel (requiere autenticación).
     *
     * Llama a: ComentarioService.obtenerComentariosPorHotel(...)
     */
    @QueryMapping
    public List<ComentarioResponse> listarComentariosHotel(@Argument String nombreHotel, @Argument String nombreUsuario, @Argument String contrasena) {
        return comentarioService.obtenerComentariosPorHotel(nombreHotel, nombreUsuario, contrasena);
    }

    /**
     * Query: mostrarComentarioUsuarioReserva
     *
     * Devuelve el comentario que un usuario hizo en una reserva específica (requiere autenticación).
     *
     * Llama a: ComentarioService.obtenerComentarioDeReserva(...)
     */
    @QueryMapping
    public List<ComentarioResponse> mostrarComentarioUsuarioReserva(@Argument String nombreUsuario, @Argument String contrasena, @Argument Integer reservaId) {
        return comentarioService.obtenerComentarioDeReserva(nombreUsuario, contrasena, reservaId);
    }

    /**
     * Query: puntuacionMediaHotel
     *
     * Devuelve la puntuación media de los comentarios de un hotel (requiere autenticación).
     *
     * Llama a: ComentarioService.mediaHotel(...)
     */
    @QueryMapping
    public Double puntuacionMediaHotel(@Argument String nombreHotel, @Argument String nombreUsuario, @Argument String contrasena) {
        return comentarioService.mediaHotel(nombreHotel, nombreUsuario, contrasena);
    }

    /**
     * Query: puntuacionesMediasUsuario
     *
     * Devuelve la puntuación media de todos los comentarios hechos por un usuario autenticado.
     *
     * Llama a: ComentarioService.mediaUsuario(...)
     */
    @QueryMapping
    public Double puntuacionesMediasUsuario(@Argument String nombreUsuario, @Argument String contrasena) {
        return comentarioService.mediaUsuario(nombreUsuario, contrasena);
    }
}
