package com.example.demo.views;

import java.util.Vector;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.example.demo.factories.YoutuberViewFactory;
import com.example.demo.services.iYoutuber;
import com.example.demo.tables.Video;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@Route("Youtuber")
@RolesAllowed("ROLE_YOUTUBER")
@Component
public class Youtuber extends Registrado {

        protected final iYoutuber iYoutuber;

        protected PerfilPropio _PerfilPropio;

        private Button perfilBtn;

        public Youtuber(iYoutuber iYoutuber, YoutuberViewFactory viewFactory) {
                super(iYoutuber, viewFactory);
                this.iYoutuber = iYoutuber;
        }

        @Override
        public void UltimosVideos() {

                Authentication auth = SecurityContextHolder
                                .getContext()
                                .getAuthentication();

                com.example.demo.tables.Youtuber usuario = (com.example.demo.tables.Youtuber) auth.getPrincipal();

                Vector<Video> videos = new Vector<>();

                for (Object obj : usuario.getSeguidor_de()) {
                        com.example.demo.tables.Youtuber seguido = (com.example.demo.tables.Youtuber) obj;

                        videos.addAll(seguido.getHa_publicado());
                }

                videos.addAll(usuario.getHa_publicado());

                _ultimosVideos = new UltimosVideosdeYoutuber(videos, viewFactory);

                body.add(_ultimosVideos);
        }

        public void PerfilPropio() {

                Authentication auth = SecurityContextHolder
                                .getContext()
                                .getAuthentication();

                com.example.demo.tables.Youtuber usuario = (com.example.demo.tables.Youtuber) auth.getPrincipal();

                UI.getCurrent().navigate(
                                PerfilPropio.class,
                                usuario.getLogin());
        }

        @Override
        protected void build() {
                super.build();

                perfilBtn = new Button(
                                "Mi Perfil",
                                new Icon(VaadinIcon.USER));

                perfilBtn.addThemeVariants(
                                ButtonVariant.LUMO_PRIMARY);

                perfilBtn.getStyle()
                                .set("margin", "10px")
                                .set("border-radius", "8px");

                header.setWidthFull();
                header.setJustifyContentMode(
                                JustifyContentMode.END);

                header.setPadding(true);
                header.add(perfilBtn);
        }

        @Override
        protected void bindEvents() {
                super.bindEvents();

                perfilBtn.addClickListener(
                                e -> PerfilPropio());
        }
 
}