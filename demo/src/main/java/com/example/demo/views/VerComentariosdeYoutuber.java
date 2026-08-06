package com.example.demo.views;

import java.util.Set;

import com.example.demo.factories.ViewFactory;
import com.example.demo.tables.Comentario;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;

@Route("VerComentariosdeYoutuber")
public class VerComentariosdeYoutuber extends VerComentarios {

    private String id;

    public VerComentariosdeYoutuber(ViewFactory factory,Set<Comentario> comentarios, int idvideo) {
        super(factory, comentarios);
        this.id = Integer.toString(idvideo);

         
    }

    public void Comentar() {
        UI.getCurrent().navigate(Comentar.class, id);

    }
}