package com.example.demo.views;

import java.util.Vector;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.domain.Video;
import com.example.demo.service.iYoutuber;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@Route("Youtuber")
@RolesAllowed("ROLE_YOUTUBER")

public class Youtuber extends Registrado {

    private final iYoutuber iYoutuber;

    private PerfilPropio perfilPropio;
    private UltimosVideosdeYoutuber ultimosVideos;

    private Button perfilBtn;

    public Youtuber(iYoutuber iYoutuber) {
        super(iYoutuber);
        this.iYoutuber = iYoutuber;
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
                e -> mostrarPerfilPropio());
    }

    public void mostrarPerfilPropio() {

        Authentication auth =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        com.example.demo.domain.Youtuber usuario =
                (com.example.demo.domain.Youtuber)
                        auth.getPrincipal();

        UI.getCurrent().navigate(
                PerfilPropio.class,
                usuario.getLogin());
    }

    @Override
    public void UltimosVideos() {

        Authentication auth =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        com.example.demo.domain.Youtuber usuario =
                (com.example.demo.domain.Youtuber)
                        auth.getPrincipal();

        Vector<Video> videos =
                new Vector<>();

        for (Object obj : usuario.getSeguidor_de()) {
            com.example.demo.domain.Youtuber seguido =
                    (com.example.demo.domain.Youtuber) obj;

            videos.addAll(seguido.getHa_publicado());
        }

        videos.addAll(usuario.getHa_publicado());

        ultimosVideos =
                new UltimosVideosdeYoutuber(videos);

        body.add(ultimosVideos);
    }

    

}