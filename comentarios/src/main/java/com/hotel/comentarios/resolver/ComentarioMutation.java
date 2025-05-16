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
    /**
      Mutación: crearComentario
     Crea un nuevo comentario validando:
      - Usuario y contraseña (contra microservicio usuarios)
      - Nombre del hotel (convertido a ID usando microservicio reservas)
      - Reserva existente y válida (checkReserva)
      - No existencia previa de comentario duplicado
     Llama a: ComentarioService.crearComentario()
     */
    //Metodo en java, Mutation en GraphQL
    @MutationMapping
    public ComentarioResponse crearComentario(@Argument ComentarioInput input) {
        return comentarioService.crearComentario(input);
    }

    /**
      Mutación: eliminarComentarios
      Elimina todos los comentarios del sistema sin requerir autenticación.
      Llama a: ComentarioService.eliminarTodos()
     */
    //Metodo en java, Mutation en GraphQL
    @MutationMapping
    public String eliminarComentarios() {
        return comentarioService.eliminarTodos();
    }


    /**
     * Mutación adicional: eliminarComentarioDeUsuario
     *
     * Elimina un comentario si el usuario autenticado.
     * Respues del correo es que El usuario y la contraseña van en todas las funciones.
     *
     * Llama a: ComentarioService.eliminarPorIdAutenticado(input)
     */
    @MutationMapping
    public String eliminarComentarioDeUsuario(@Argument EliminarComentarioInput input) {
        return comentarioService.eliminarComentarioDeUsuario(input);
    }

}
