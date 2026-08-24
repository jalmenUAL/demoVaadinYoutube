package com.example.demo.views;

import com.example.demo.patterns.BaseItemView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;

@Route("Usuariosdenunciados_item")
public class Usuariosdenunciados_item extends BaseItemView<com.example.demo.tables.Youtuber> {

        public Usuariosdenunciados _usuariosdenunciados;

        private Image avatar;
        private Span nombreSpan;

        public Usuariosdenunciados_item(
                        com.example.demo.tables.Youtuber youtuber) {

                super(youtuber);
                 initView();

        }

        @Override
        protected void build() {

                avatar = new Image(
                                model.getFotoPerfil(),
                                model.getLogin());

                avatar.setWidth("50px");
                avatar.setHeight("50px");

                avatar.getStyle()
                                .set("border-radius", "50%");

                nombreSpan = new Span(
                                model.getLogin());

                nombreSpan.getStyle()
                                .set("font-weight", "bold")
                                .set("font-size", "1.1em");

                HorizontalLayout infoLayout = new HorizontalLayout(
                                avatar,
                                nombreSpan);

                infoLayout.setAlignItems(
                                Alignment.CENTER);

                add(infoLayout);

        }

      

        @Override
        protected void bindEvents() {

                avatar.addClickListener(
                                event -> UI.getCurrent().navigate(PerfilAjenodeAdministrador.class, model.getLogin() )) ; 

        }
 

}