package com.example.demo.views;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.services.iYoutuber;
import com.example.demo.tables.Video;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@Route("Comentar")
@RolesAllowed("ROLE_YOUTUBER")
public class Comentar extends VerticalLayout implements HasUrlParameter<String> {
    public VerComentariosdeYoutuber _verComentariosdeYoutuber;
    private TextField campoComentario;
    public iYoutuber _iYoutuber;
    public int id;

    public Comentar(iYoutuber iYoutuber) {
        this._iYoutuber = iYoutuber;
        setWidthFull();
        setPadding(true);
        setSpacing(true);
        setAlignItems(Alignment.STRETCH);

        campoComentario = new TextField("Escribe un comentario");
        campoComentario.setWidthFull();

        Button btnPublicar = new Button("Publicar Comentario");

        btnPublicar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnPublicar.setWidthFull();

        btnPublicar.addClickListener(e -> {
            publicar_comentario();
            campoComentario.clear();
        });

        add(campoComentario, btnPublicar);
    }

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        id = Integer.valueOf(parameter);
    }

    private void publicar_comentario() {
        Video video = _iYoutuber.findVideoById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            throw new RuntimeException("Usuario no autenticado");
        }

        com.example.demo.tables.Youtuber usuario = (com.example.demo.tables.Youtuber) auth.getPrincipal();

        _iYoutuber.publicarComentario(usuario.getLogin(), String.valueOf(video.getId()), campoComentario.getValue());

        UI.getCurrent().getPage().getHistory().back();
    }

}
