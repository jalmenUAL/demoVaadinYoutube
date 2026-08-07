package com.example.demo.views;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.factories.ViewFactory;
import com.example.demo.services.iYoutuber;
import com.example.demo.tables.Video;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.Route;

@Route("VerVideodeYoutuber")

public class VerVideodeYoutuber extends VerVideo {

    private iYoutuber iYoutuber;
    Button likeButton;

    Boolean legusta;

    public VerVideodeYoutuber(com.example.demo.services.iYoutuber iYoutuber, ViewFactory viewFactory) {
        super(iYoutuber, viewFactory);
        this.iYoutuber = iYoutuber;

    }

    public void like() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            throw new RuntimeException("Usuario no autenticado");
        }

        com.example.demo.tables.Youtuber usuario = (com.example.demo.tables.Youtuber) auth.getPrincipal();
        if (likeButton.getText().equals("Me Gusta")) {
            likeButton.setText("Quitar Me Gusta");

            iYoutuber.likeVideo(usuario.getLogin(), video.getId());
            likeButton.getStyle()
                    .set("background-color", "#0d6efd") // negro
                    .set("color", "white")
                    .set("border-radius", "8px")
                    .set("padding", "10px 20px")
                    .set("font-weight", "bold");

        } else {
            likeButton.setText("Me Gusta");
            iYoutuber.dislikeVideo(usuario.getLogin(), video.getId());
            likeButton.getStyle()
                    .set("background-color", "#0d6efd") // azul
                    .set("color", "white")
                    .set("border-radius", "8px")
                    .set("padding", "10px 20px")
                    .set("font-weight", "bold");

        }

    }

    @Override
    public void VerComentarios() {
        _verComentarios = new VerComentariosdeYoutuber(viewFactory, video.getTiene_comentarios(), video.getId());
        comentarios.add(_verComentarios);
    }

    public void setParameter(BeforeEvent event, Integer parameter) {
        super.setParameter(event, parameter);
        // Crear botón de Like
        likeButton = new Button("", event2 -> like());
        likeButton.setIcon(new Icon(VaadinIcon.THUMBS_UP));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        com.example.demo.tables.Youtuber usuario = (com.example.demo.tables.Youtuber) auth.getPrincipal();

        

        legusta = usuario.getLe_gusta().stream().anyMatch(v -> ((Video) v).getId() == video.getId());

        if (!legusta) {
            likeButton.setText("Me Gusta");
            likeButton.getStyle()
                    .set("background-color", "#0d6efd") // azul
                    .set("color", "white")
                    .set("border-radius", "8px")
                    .set("padding", "10px 20px")
                    .set("font-weight", "bold");
        } else {
            likeButton.setText("Quitar Me Gusta");
            likeButton.getStyle()
                    .set("background-color", "#0d6efd") // azul
                    .set("color", "white")
                    .set("border-radius", "8px")
                    .set("padding", "10px 20px")
                    .set("font-weight", "bold");
        }

        frame_y_comentarios.add(likeButton);

    }
}