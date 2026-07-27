package com.example.demo.views;

import java.util.List;
import java.util.Vector;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;

@Route("Usuariosdenunciados")
public class Usuariosdenunciados extends VerticalLayout {
    public Administrador _administrador;
    public Vector<Usuariosdenunciados_item> _item = new Vector<Usuariosdenunciados_item>();

    public Usuariosdenunciados(List<com.example.demo.tables.Youtuber> youtubers) {

        Span titulo = new Span("Usuarios denunciados");
        titulo.getStyle().set("font-weight", "bold").set("font-size", "1.5em");
        add(titulo);

        HorizontalLayout cardsLayout = new HorizontalLayout();
        cardsLayout.setWidthFull();
        if (youtubers == null || youtubers.isEmpty()) {
            Span noUsers = new Span("No hay usuarios denunciados.");
            noUsers.getStyle().set("color", "red");
            cardsLayout.add(noUsers);
            add(cardsLayout);
            return;
        } else {
            for (int i = 0; i < youtubers.size(); i++) {
                Usuariosdenunciados_item item = new Usuariosdenunciados_item(youtubers.get(i));
                _item.add(item);

                Div card = new Div(item);
                card.getStyle()
                        .set("border", "1px solid #ccc")
                        .set("border-radius", "8px")
                        .set("padding", "16px")
                        .set("margin-right", "12px")
                        .set("box-shadow", "0 2px 8px rgba(0,0,0,0.05)")
                        .set("min-width", "200px");

                cardsLayout.add(card);
            }
            add(cardsLayout);
        }
    }
}