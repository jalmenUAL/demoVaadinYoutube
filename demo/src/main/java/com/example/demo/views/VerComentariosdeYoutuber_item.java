package com.example.demo.views;

import com.example.demo.factories.ViewFactory;
import com.example.demo.tables.Comentario;
import com.vaadin.flow.router.Route;

@Route("VerComentariosdeYoutuber_item")

public class VerComentariosdeYoutuber_item extends VerComentarios_item {

     

    public VerComentariosdeYoutuber_item(Comentario comentario, ViewFactory viewFactory) {
        super(viewFactory, comentario);

    }
}