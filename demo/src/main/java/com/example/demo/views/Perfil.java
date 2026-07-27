package com.example.demo.views;

import com.example.demo.services.iInicio;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

@Route("Perfil")
public class Perfil extends VerticalLayout implements HasUrlParameter<String> {

    public Videosgustados _videosgustados;
    public Videospublicados _videospublicados;

    HorizontalLayout topLayout = new HorizontalLayout();
    com.example.demo.tables.Youtuber _usuario;
    iInicio _iInicio;

    public Perfil(iInicio iInicio) {
        this._iInicio = iInicio;
        setSizeFull();
        setSpacing(true);
        setPadding(false);
        setAlignItems(Alignment.CENTER);
    }

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        _usuario = _iInicio.findYoutuberById(parameter);

        if (_usuario.getBanner() == null || _usuario.getBanner().isEmpty()) {
            _usuario.setBanner(
                    "https://www.fcbarcelona.com/photo-resources/2025/07/21/ff83df3a-ba37-4603-a4b6-c09cc4f94470/16x9_ESCUDO_GENERIC_2025.jpg?width=1200&height=750"); // URL
                                                                                                                                                                       // de
                                                                                                                                                                       // una
            // imagen de fondo por defecto
        }
        Image imagenDeFondo = new Image(_usuario.getBanner(), "Imagen de fondo");
        imagenDeFondo.setWidth("100%");
        imagenDeFondo.setHeight("300px");
        imagenDeFondo.getStyle().set("object-fit", "cover");

        add(imagenDeFondo);

        if (_usuario.getBloqueado()) {
            H2 Bloqueado = new H2("Este Usuario ha sido Bloqueado");
            Bloqueado.getStyle().set("color", "red");
            add(Bloqueado);
        }

        String nombreUsuario = _usuario.getLogin();
        String avatarUrl = _usuario.getFotoPerfil();
        if (avatarUrl == null || avatarUrl.isEmpty()) {
            avatarUrl = "https://via.placeholder.com/100";
        }
        // Título debajo del fondo
        H2 titulo = new H2("Perfil del Youtuber");
        titulo.getStyle().set("color", "#2c3e50").set("margin-top", "10px");
        add(titulo);

        Avatar avatar = new Avatar(nombreUsuario, avatarUrl);
        avatar.setWidth("100px");
        avatar.setHeight("100px");

        Span nombreSpan = new Span(nombreUsuario);
        nombreSpan.getStyle()
                .set("font-weight", "bold")
                .set("font-size", "1.3em")
                .set("margin-left", "10px");

        Button btnYoutubersSeguidos = new Button("Ver Youtubers Seguidos", new Icon(VaadinIcon.USER_HEART));
        btnYoutubersSeguidos.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnYoutubersSeguidos.getStyle()
                .set("border-radius", "8px")
                .set("font-weight", "bold")
                .set("padding", "8px 16px");

        btnYoutubersSeguidos.addClickListener(e -> {
            getUI().ifPresent(ui -> ui.navigate(Youtubersseguidos.class, _usuario.getLogin()));
        });

        topLayout.add(avatar, nombreSpan, btnYoutubersSeguidos);
        topLayout.setAlignItems(Alignment.CENTER);
        topLayout.setSpacing(true);
        topLayout.setPadding(true);
        add(topLayout);

        _videosgustados = new Videosgustados(_usuario.getLe_gusta());
        _videospublicados = new Videospublicados(_usuario.getHa_publicado());

        VerticalLayout publicadosLayout = new VerticalLayout();
        publicadosLayout.setWidth("45%");
        Span publicadosTitulo = new Span("🎬 Videos Publicados");
        publicadosTitulo.getStyle().set("font-weight", "bold").set("font-size", "1.1em");
        publicadosLayout.add(publicadosTitulo, _videospublicados);

        VerticalLayout gustadosLayout = new VerticalLayout();
        gustadosLayout.setWidth("45%");
        Span gustadosTitulo = new Span("❤️ Videos Gustados");
        gustadosTitulo.getStyle().set("font-weight", "bold").set("font-size", "1.1em");
        gustadosLayout.add(gustadosTitulo, _videosgustados);

        // Layout horizontal para ambas listas

        HorizontalLayout listasLayout = new HorizontalLayout(publicadosLayout, gustadosLayout);
        listasLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        listasLayout.setSpacing(true);
        listasLayout.setWidthFull();

        add(listasLayout);

    }

}