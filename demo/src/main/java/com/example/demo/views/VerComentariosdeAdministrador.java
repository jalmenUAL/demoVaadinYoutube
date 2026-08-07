package com.example.demo.views;

import java.util.Set;

import com.example.demo.factories.ViewFactory;
import com.example.demo.services.iAdministrador;
import com.example.demo.tables.Comentario;
import com.vaadin.flow.router.Route;

@Route("VerComentariosdeAdministrador")

public class VerComentariosdeAdministrador extends VerComentarios {

    public VerComentariosdeAdministrador(ViewFactory viewFactory, iAdministrador iAdministrador,
            Set<Comentario> comentarios) {

        super(viewFactory, comentarios);

    }

}