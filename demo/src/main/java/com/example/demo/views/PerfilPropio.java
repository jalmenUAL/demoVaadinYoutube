package com.example.demo.views;

import com.example.demo.factories.ViewFactory;
import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.services.iYoutuber;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@Route("PerfilPropio")
@RolesAllowed("ROLE_YOUTUBER")
public class PerfilPropio extends Perfil {

        public com.example.demo.tables.Youtuber _youtuber;
        public PublicarVideo _publicarVideo;
        public Configuracion _configuracion;

        private final iYoutuber iYoutuber;

        private Button publicarButton;
        private Button configButton;

        public PerfilPropio(iYoutuber iYoutuber, ViewFactoryProvider viewFactory) {
                super(iYoutuber, viewFactory);
                this.iYoutuber = iYoutuber;
                
        }

        @Override
        protected void build(String parameter) {

                super.build(parameter);

        
                publicarButton = new Button("📤 Publicar video");

                publicarButton.getStyle()
                                .set("background-color", "#0d6efd")
                                .set("color", "white")
                                .set("border-radius", "8px")
                                .set("font-weight", "bold");

                configButton = new Button("⚙️ Configuración");

                configButton.getStyle()
                                .set("background-color", "#6c757d")
                                .set("color", "white")
                                .set("border-radius", "8px")
                                .set("font-weight", "bold");

                HorizontalLayout botonesHeader = new HorizontalLayout(
                                publicarButton,
                                configButton);

                botonesHeader.setWidthFull();
                botonesHeader.setJustifyContentMode(
                                JustifyContentMode.END);
                botonesHeader.setPadding(true);

                topLayout.add(botonesHeader);
        }

        @Override
        protected void bindEvents() {

                super.bindEvents();

                publicarButton.addClickListener(
                                e -> PublicarVideo());

                configButton.addClickListener(
                                e -> Configuracion());
        }

        public void PublicarVideo() {

                UI.getCurrent().navigate(
                                PublicarVideo.class);
        }

        public void Configuracion() {

                UI.getCurrent().navigate(
                                Configuracion.class);
        }

}