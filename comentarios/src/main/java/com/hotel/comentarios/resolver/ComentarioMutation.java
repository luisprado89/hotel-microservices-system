package com.hotel.comentarios.resolver;

import com.hotel.comentarios.dto.ComentarioInput;
import com.hotel.comentarios.dto.ComentarioResponse;
import com.hotel.comentarios.dto.EliminarComentarioInput;
import com.hotel.comentarios.service.ComentarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ComentarioMutation {

    private final ComentarioService comentarioService;

    @MutationMapping
    public ComentarioResponse crearComentario(@Argument ComentarioInput input) {
        return comentarioService.crearComentario(input);
    }

    @MutationMapping
    public String eliminarComentarios() {
        return comentarioService.eliminarTodos();
    }

    @MutationMapping
    public String eliminarComentarioDeUsuario(@Argument String id) {
        return comentarioService.eliminarPorId(id);
    }

    @MutationMapping
    public String eliminarComentarioAutenticado(@Argument EliminarComentarioInput input) {
        return comentarioService.eliminarPorIdAutenticado(input);
    }

}
