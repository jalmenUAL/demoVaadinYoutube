package com.example.demo.views.common;

import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.patterns.BaseItemView;
import com.example.demo.tables.Comentario;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("VerComentarios_item")

public class VerComentarios_item
        extends BaseItemView<Comentario> {

    protected final ViewFactoryProvider viewFactory;

    private Image avatar;

    PerfilAjeno _perfilAjeno;

    public VerComentarios_item(
            Comentario comentario,
            ViewFactoryProvider viewFactory) {

        super(comentario);

        this.viewFactory = viewFactory;

        initView();
    }

    public void PerfilAjeno() {

        UI.getCurrent().navigate(
                viewFactory.getFactory().createPerfilAjeno(),
                model.getEscrito_por().getLogin());
    }

    @Override
    protected void build() {

        setPadding(true);
        setSpacing(true);
        setWidthFull();
        setAlignItems(Alignment.START);

        avatar = new Image(
                model.getEscrito_por().getFotoPerfil(),
                "Avatar");

        avatar.setWidth("50px");
        avatar.setHeight("50px");

        avatar.getStyle()
                .set("border-radius", "50%");

        Span nombreUsuario =
                new Span(
                        model.getEscrito_por().getLogin());

        VerticalLayout comentarioLayout =
                new VerticalLayout();

        comentarioLayout.setPadding(false);
        comentarioLayout.setSpacing(false);
        comentarioLayout.setWidthFull();

        Paragraph comentarioTexto =
                new Paragraph(
                        model.getTexto());

        comentarioTexto.getStyle()
                .set("margin", "0")
                .set("font-size", "0.9em")
                .set("color", "#333");

        comentarioLayout.add(
                comentarioTexto);

        add(
                avatar,
                nombreUsuario,
                comentarioLayout);
    }

    @Override
    protected void bindEvents() {

        avatar.addClickListener(
                e -> PerfilAjeno());
    }
}