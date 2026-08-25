package com.example.demo.views;

import java.util.Set;

import com.example.demo.factories.ViewFactory;
import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.services.iAdministrador;
import com.example.demo.tables.Comentario;
import com.vaadin.flow.router.Route;

@Route("VerComentariosdeAdministrador")

public class VerComentariosdeAdministrador extends VerComentarios {

    public VerComentariosdeAdministrador(iAdministrador iAdministrador,
            Set<Comentario> comentarios, int idvideo, ViewFactoryProvider viewFactory) {

        super(comentarios, idvideo, viewFactory);

    }

}