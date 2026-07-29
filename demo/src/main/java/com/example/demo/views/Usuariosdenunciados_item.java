package com.example.demo.views;

import com.example.demo.patterns.BaseView;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("Usuariosdenunciados_item")
public class Usuariosdenunciados_item extends BaseView {

    public Usuariosdenunciados _usuariosdenunciados;

    private final com.example.demo.tables.Youtuber youtuber;

    private Image avatar;
    private Span nombreSpan;

    public Usuariosdenunciados_item(
            com.example.demo.tables.Youtuber youtuber) {

        this.youtuber = youtuber;

        
    }

     

    @Override
    protected void build() {

        avatar = new Image(
                youtuber.getFotoPerfil(),
                youtuber.getLogin()
        );

        avatar.setWidth("50px");
        avatar.setHeight("50px");

        avatar.getStyle()
                .set("border-radius", "50%");


        nombreSpan = new Span(
                youtuber.getLogin()
        );

        nombreSpan.getStyle()
                .set("font-weight", "bold")
                .set("font-size", "1.1em");


        HorizontalLayout infoLayout =
                new HorizontalLayout(
                        avatar,
                        nombreSpan
                );

        infoLayout.setAlignItems(
                Alignment.CENTER
        );


        add(infoLayout);

    }


    //CAMBIOS PARA NAVEGAR AL PERFIL DEL YOUTUBER DENUNCIADO DESDE EL ADMINISTRADOR

    @Override
    protected void bindEvents() {

        avatar.addClickListener(
                event -> navegarAlPerfil()
        );

    }

    private void navegarAlPerfil() {

        getUI().ifPresent(
                ui -> ui.navigate(
                        "PerfilAjenodeAdministrador/"
                                + youtuber.getLogin()
                )
        );

    }



}