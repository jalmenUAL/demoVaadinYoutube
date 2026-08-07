package com.example.demo.views;

import java.util.List;
import java.util.Vector;

import com.example.demo.patterns.BaseListView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;

@Route("Usuariosdenunciados")
public class Usuariosdenunciados extends BaseListView<com.example.demo.tables.Youtuber> {

        public Administrador _administrador;

        public Vector<Usuariosdenunciados_item> _item = new Vector<>();

        private HorizontalLayout cardsLayout;

        public Usuariosdenunciados(
                        List<com.example.demo.tables.Youtuber> youtubers) {
                super(youtubers);

        }

        @Override
        protected void bindEvents() {

                // No tiene eventos propios

        }

        @Override
        protected void buildContainer() {
                Span titulo = new Span("Usuarios denunciados");

                titulo.getStyle()
                                .set("font-weight", "bold")
                                .set("font-size", "1.5em");

                add(titulo);

                cardsLayout = new HorizontalLayout();

                cardsLayout.setWidthFull();

                if (elements == null || elements.isEmpty()) {

                        Span noUsers = new Span(
                                        "No hay usuarios denunciados.");

                        noUsers.getStyle()
                                        .set("color", "red");

                        cardsLayout.add(noUsers);

                        add(cardsLayout);

                        return;
                }
        }

        @Override
        protected void buildItems() {
                for (com.example.demo.tables.Youtuber youtuber : elements) {

                        Usuariosdenunciados_item item = new Usuariosdenunciados_item(youtuber);

                        _item.add(item);

                        Div card = new Div(item);

                        card.getStyle()
                                        .set(
                                                        "border",
                                                        "1px solid #ccc")
                                        .set(
                                                        "border-radius",
                                                        "8px")
                                        .set(
                                                        "padding",
                                                        "16px")
                                        .set(
                                                        "margin-right",
                                                        "12px")
                                        .set(
                                                        "box-shadow",
                                                        "0 2px 8px rgba(0,0,0,0.05)")
                                        .set(
                                                        "min-width",
                                                        "200px");

                        cardsLayout.add(card);

                }

                add(cardsLayout);
        }

}