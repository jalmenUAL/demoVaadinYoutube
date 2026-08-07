package com.example.demo.views;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.patterns.BaseParameterizedView;
import com.example.demo.services.iYoutuber;
import com.example.demo.tables.Video;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@Route("Comentar")
@RolesAllowed("ROLE_YOUTUBER")
public class Comentar extends BaseParameterizedView<String> {

    private final iYoutuber _iYoutuber;

    private TextField campoComentario;
    private Button btnPublicar;

    private int id;

    public Comentar(iYoutuber iYoutuber) {
        this._iYoutuber = iYoutuber;
    }

    @Override
    protected void build(String parameter) {
        id = Integer.parseInt(parameter);

    }

    @Override
    protected void bindEvents() {

        btnPublicar.addClickListener(e -> {
            publicarComentario();
            campoComentario.clear();
        });

    }

    public void publicarComentario() {

        Video video = _iYoutuber.findVideoById(id);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        com.example.demo.tables.Youtuber usuario = (com.example.demo.tables.Youtuber) auth.getPrincipal();

        _iYoutuber.publicarComentario(
                usuario.getLogin(),
                String.valueOf(video.getId()),
                campoComentario.getValue());

        UI.getCurrent().getPage().getHistory().back();
    }

    @Override
    protected void build() {
        setWidthFull();
        setPadding(true);
        setSpacing(true);
        setAlignItems(Alignment.STRETCH);
        campoComentario = new TextField("Escribe un comentario");
        campoComentario.setWidthFull();

        btnPublicar = new Button("Publicar comentario");
        btnPublicar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnPublicar.setWidthFull();

        add(campoComentario, btnPublicar);
    }

}
