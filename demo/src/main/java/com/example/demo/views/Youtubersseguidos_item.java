package com.example.demo.views;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.factories.ViewFactory;
import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.patterns.BaseItemView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("Youtuberseguidos_item")

public class Youtubersseguidos_item extends BaseItemView<com.example.demo.tables.Youtuber> {
    public Youtubersseguidos _youtubersseguidos;
    public PerfilAjeno _perfilAjeno;
    protected ViewFactoryProvider viewFactory;

    public Youtubersseguidos_item(com.example.demo.tables.Youtuber youtuber,ViewFactoryProvider viewFactory) {
        super(youtuber);
        this.viewFactory = viewFactory;
         initView();
    }

    public void PerfilAjeno() {
        UI.getCurrent().navigate(viewFactory.getFactory().createPerfilAjeno(), model.getLogin());
    }

    @Override
    protected void build() {
        String nombreUsuario = model.getLogin();
        int seguidores = model.getSeguido_por().size();
        String avatarUrl = model.getFotoPerfil();

        Image avatar = new Image(avatarUrl, "Avatar");
        avatar.setWidth("60px");
        avatar.setHeight("60px");
        avatar.getStyle().set("border-radius", "50%");

        Span nombre = new Span(nombreUsuario);
        nombre.getStyle().set("font-weight", "bold").set("font-size", "18px");

        Span seguidoresLabel = new Span(seguidores + " seguidores");

        Button verPerfilButton = new Button("Ver perfil", e -> PerfilAjeno());
        verPerfilButton.getStyle()
                .set("background-color", "#0d6efd")
                .set("color", "white")
                .set("border-radius", "8px")
                .set("font-weight", "bold");

        VerticalLayout info = new VerticalLayout(nombre, seguidoresLabel, verPerfilButton);
        info.setPadding(false);
        info.setSpacing(false);

        HorizontalLayout fila = new HorizontalLayout(avatar, info);
        fila.setAlignItems(Alignment.CENTER);
        fila.setSpacing(true);
        fila.setWidthFull();

        add(fila);

        getStyle().set("padding", "10px").set("border", "1px solid #ddd").set("border-radius", "10px");

    }

    @Override
    protected void bindEvents() {

    }
}