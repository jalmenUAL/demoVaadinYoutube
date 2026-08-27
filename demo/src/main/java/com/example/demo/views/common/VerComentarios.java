package com.example.demo.views.common;

import java.util.Set;

import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.patterns.BaseListView;
import com.example.demo.tables.Comentario;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;

@Route("VerComentarios")

public class VerComentarios
        extends BaseListView<Comentario> {

    protected final ViewFactoryProvider viewFactory;
    protected int idvideo;

    public VerComentarios(
            Set<Comentario> comentarios,
            int idvideo,
            ViewFactoryProvider viewFactory) {

        super(comentarios);

        this.viewFactory = viewFactory;

        this.idvideo = idvideo;

        initView();
    }

    @Override
    protected void buildContainer() {

        setWidthFull();
        setPadding(true);
        setSpacing(false);
        setAlignItems(
                Alignment.STRETCH);
    }

    @Override
    protected void buildItems() {

        if (elements.isEmpty()) {

            Div noComments =
                    new Div();

            noComments.setText(
                    "No hay comentarios disponibles.");

            add(noComments);

        } else {

            for (Comentario comentario : elements) {

                VerComentarios_item item =
                        viewFactory
                                .getFactory()
                                .createVerComentariosItem(
                                        comentario,
                                        viewFactory);

                add(item);
            }
        }

        Div separator =
                new Div();

        separator.getStyle()
                .set("height", "1px")
                .set("background-color", "#ddd")
                .set("width", "100%")
                .set("margin", "8px 0");

        add(separator);
    }

    @Override
    protected void bindEvents() {
        // No tiene eventos propios.
    }
}