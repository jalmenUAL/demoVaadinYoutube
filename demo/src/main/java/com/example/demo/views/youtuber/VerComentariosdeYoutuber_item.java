package com.example.demo.views.youtuber;

import com.example.demo.factories.ViewFactory;
import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.tables.Comentario;
import com.example.demo.views.common.VerComentarios_item;
import com.vaadin.flow.router.Route;

@Route("VerComentariosdeYoutuber_item")

public class VerComentariosdeYoutuber_item extends VerComentarios_item {

    public VerComentariosdeYoutuber_item(Comentario comentario, ViewFactoryProvider viewFactory) {
        super(comentario, viewFactory);

    }
}