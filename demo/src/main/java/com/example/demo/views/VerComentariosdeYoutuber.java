package com.example.demo.views;

import java.util.Set;

import com.example.demo.domain.Comentario;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;

@Route("VerComentariosdeYoutuber")
public class VerComentariosdeYoutuber extends VerComentarios {

    private String id;

    public VerComentariosdeYoutuber(Set<Comentario> comentarios, int idvideo) {
        super(comentarios);
        this.id = Integer.toString(idvideo);

        removeAll();

        Button comentarButton = new Button("Comentar", event -> Comentar());

        comentarButton.getStyle()
                .set("background-color", "#28a745") // verde
                .set("color", "white")
                .set("border-radius", "8px")
                .set("padding", "10px 20px")
                .set("font-weight", "bold");

        HorizontalLayout centrarLayout = new HorizontalLayout(comentarButton);
        centrarLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        centrarLayout.setWidthFull();

        add(centrarLayout);

        if (comentarios.isEmpty()) {

            Div noComments = new Div();
            noComments.setText("No hay comentarios disponibles.");
            add(noComments);
        } else {
            for (Comentario comentario : comentarios) {
                VerComentariosdeYoutuber_item comentarioItem = new VerComentariosdeYoutuber_item(comentario);
                add(comentarioItem);
            }
        }
    }

    public void Comentar() {
        UI.getCurrent().navigate(Comentar.class, id);

    }
}