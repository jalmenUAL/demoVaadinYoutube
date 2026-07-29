package com.example.demo.views;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.patterns.BaseView;
import com.example.demo.tables.Comentario;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("VerComentarios_item")

public class VerComentarios_item extends BaseView {
    public VerComentarios _verComentarios;
    public PerfilAjeno _perfilAjeno;
    Comentario comentario;
    Image avatar;
   

    public VerComentarios_item(Comentario comentario) {
        this.comentario = comentario;

       
    }

    
    public void PerfilAjeno() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        com.example.demo.tables.Youtuber usuario = (com.example.demo.tables.Youtuber) auth.getPrincipal();
        if (auth != null && auth.isAuthenticated()) {
            boolean esAdmin = auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMINISTRADOR"));
            boolean esYoutuber = auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_YOUTUBER"));

            if (esAdmin) {
                UI.getCurrent().navigate(PerfilAjenodeAdministrador.class, comentario.getEscrito_por().getLogin());
            } else if (esYoutuber) {

                if (comentario.getEscrito_por().getLogin().equals(usuario.getLogin())) {
                UI.getCurrent().navigate(PerfilPropio.class, comentario.getEscrito_por().getLogin());}
                else { UI.getCurrent().navigate(PerfilAjenodeYoutuber.class, comentario.getEscrito_por().getLogin());  }
            }
        } else {
            UI.getCurrent().navigate(PerfilAjeno.class, comentario.getEscrito_por().getLogin());
        }

    }


    @Override
    protected void build() {
        setPadding(true);
        setSpacing(true);
        setWidthFull();
        setAlignItems(Alignment.START);

        
        avatar = new Image(comentario.getEscrito_por().getFotoPerfil(), "Avatar");
        avatar.setWidth("50px");
        avatar.setHeight("50px");
        avatar.getStyle().set("border-radius", "50%");

      

        Span nombreUsuario = new Span(comentario.getEscrito_por().getLogin());

        VerticalLayout comentarioLayout = new VerticalLayout();
        comentarioLayout.setPadding(false);
        comentarioLayout.setSpacing(false);
        comentarioLayout.setWidthFull();

        Paragraph comentario_texto = new Paragraph(comentario.getTexto());
        comentario_texto.getStyle()
                .set("margin", "0")
                .set("font-size", "0.9em")
                .set("color", "#333");

        comentarioLayout.add(comentario_texto);

        // Añadir avatar y comentario al layout horizontal
        add(avatar, nombreUsuario, comentarioLayout);
    }


    @Override
    protected void bindEvents() {
          avatar.addClickListener(e -> PerfilAjeno());
    }
}
