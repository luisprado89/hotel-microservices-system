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

    @QueryMapping
    public List<ComentarioResponse> listarComentariosUsuario(@Argument String nombreUsuario, @Argument String contrasena) {
        return comentarioService.obtenerComentariosPorUsuario(nombreUsuario, contrasena);
    }

    @QueryMapping
    public List<ComentarioResponse> listarComentariosHotel(@Argument String nombreHotel, @Argument String nombreUsuario, @Argument String contrasena) {
        return comentarioService.obtenerComentariosPorHotel(nombreHotel, nombreUsuario, contrasena);
    }


    @QueryMapping
    public List<ComentarioResponse> mostrarComentarioUsuarioReserva(@Argument String nombreUsuario, @Argument String contrasena, @Argument Integer reservaId) {
        return comentarioService.obtenerComentarioDeReserva(nombreUsuario, contrasena, reservaId);
    }

    @QueryMapping
    public Double puntuacionMediaHotel(@Argument String nombreHotel, @Argument String nombreUsuario, @Argument String contrasena) {
        return comentarioService.mediaHotel(nombreHotel, nombreUsuario, contrasena);
    }


    @QueryMapping
    public Double puntuacionesMediasUsuario(@Argument String nombreUsuario, @Argument String contrasena) {
        return comentarioService.mediaUsuario(nombreUsuario, contrasena);
    }
}
