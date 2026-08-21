package com.example.demo.views;

import java.util.Set;

import com.example.demo.factories.ViewFactory;
import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.factories.YoutuberViewFactory;
import com.example.demo.tables.Comentario;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;

@Route("VerComentariosdeYoutuber")
public class VerComentariosdeYoutuber extends VerComentarios {

    
    public VerComentariosdeYoutuber(Set<Comentario> comentarios, int idvideo, ViewFactoryProvider viewFactory) {
        super(comentarios, idvideo, viewFactory);
       
        

    }

    @Override
    protected void build() {
        super.build();
        Button comentarButton = new Button("Comentar");
       comentarButton.getStyle()
        .set("background-color", "#FF0000")
        .set("color", "white")
        .set("font-weight", "bold")
        .set("border-radius", "6px");
        comentarButton.addClickListener(e -> Comentar());
        HorizontalLayout buttonLayout = new HorizontalLayout(comentarButton);
        
        buttonLayout.setWidthFull();
        buttonLayout.setJustifyContentMode(JustifyContentMode.END);
        add(buttonLayout);
    }

    public void Comentar() {
        UI.getCurrent().navigate(Comentar.class, String.valueOf(idvideo));

    }
}