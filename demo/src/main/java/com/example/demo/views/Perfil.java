package com.example.demo.views;

import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.patterns.BaseParameterizedView;
import com.example.demo.services.iInicio;
import com.vaadin.flow.component.UI;
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
import com.vaadin.flow.router.Route;

@Route("Perfil")

public class Perfil extends BaseParameterizedView<String> {

    public Videosgustados _videosgustados;
    public Videospublicados _videospublicados;

    protected HorizontalLayout topLayout;

    private VerticalLayout publicadosLayout;
    private VerticalLayout gustadosLayout;

    private Button btnYoutubersSeguidos;

    private final iInicio _iInicio;

    protected com.example.demo.tables.Youtuber _usuario;
    protected ViewFactoryProvider viewFactory;

    public Perfil(
            iInicio iInicio,
            ViewFactoryProvider viewFactory) {

        this._iInicio = iInicio;
        this.viewFactory = viewFactory;
    }

    @Override
    protected void bindEvents() {

        btnYoutubersSeguidos.addClickListener(
                e -> Youtubersseguidos());
    }

    @Override
    protected void build(String parameter) {

        // --------------------------------
        // Obtener usuario
        // --------------------------------

        _usuario = _iInicio.findYoutuberById(parameter);

        // --------------------------------
        // Configuración general
        // --------------------------------

        setSizeFull();
        setSpacing(true);
        setPadding(false);
        setAlignItems(Alignment.CENTER);

        // --------------------------------
        // Banner
        // --------------------------------

         

       String bannerUrl = _usuario.getBanner();

if (bannerUrl == null
        || bannerUrl.isBlank()
        || bannerUrl.startsWith("VAADIN/dynamic/resource")) {

    bannerUrl =
            "https://images.unsplash.com/photo-1485846234645-a62644f84728"
            + "?auto=format&fit=crop&w=1600&q=80";
}
        Image imagenDeFondo = new Image(
                bannerUrl,
                "Imagen de fondo");

        imagenDeFondo.setWidth("100%");
        imagenDeFondo.setHeight("300px");

        imagenDeFondo.getStyle()
                .set("object-fit", "cover");

        // --------------------------------
        // Usuario bloqueado
        // --------------------------------

        H2 bloqueado = null;

        if (_usuario.getBloqueado()) {

            bloqueado = new H2(
                    "Este Usuario ha sido Bloqueado");

            bloqueado.getStyle()
                    .set("color", "red");
        }

        // --------------------------------
        // Layout superior
        // --------------------------------

        topLayout = new HorizontalLayout();

        topLayout.setWidthFull();
        topLayout.setAlignItems(Alignment.CENTER);
        topLayout.setJustifyContentMode(
                JustifyContentMode.CENTER);
        topLayout.setSpacing(true);
        topLayout.setPadding(true);

        // --------------------------------
        // Título
        // --------------------------------

        H2 titulo = new H2(
                "Perfil del Youtuber");

        titulo.getStyle()
                .set("color", "#2c3e50")
                .set("margin-top", "10px");

        // --------------------------------
        // Avatar
        // --------------------------------

        String avatarUrl = _usuario.getFotoPerfil();

if (avatarUrl == null
        || avatarUrl.isBlank()
        || avatarUrl.startsWith("VAADIN/dynamic/resource")) {

    avatarUrl = "https://i.pravatar.cc/150";
}

Avatar avatar = new Avatar(
        _usuario.getLogin(),
        avatarUrl);

        avatar.setWidth("100px");
        avatar.setHeight("100px");

        // --------------------------------
        // Nombre
        // --------------------------------

        Span nombre = new Span(
                _usuario.getLogin());

        nombre.getStyle()
                .set("font-weight", "bold")
                .set("font-size", "1.3em")
                .set("margin-left", "10px");

        // --------------------------------
        // Botón Youtubers seguidos
        // --------------------------------

        btnYoutubersSeguidos = new Button(
                "Ver Youtubers Seguidos",
                new Icon(VaadinIcon.USER_HEART));

        btnYoutubersSeguidos.addThemeVariants(
                ButtonVariant.LUMO_PRIMARY);

        btnYoutubersSeguidos.getStyle()
                .set("border-radius", "8px")
                .set("font-weight", "bold")
                .set("padding", "8px 16px");

        // --------------------------------
        // Añadir elementos al header del perfil
        // --------------------------------

        topLayout.add(
                titulo,
                avatar,
                nombre,
                btnYoutubersSeguidos);

        // --------------------------------
        // Videos publicados
        // --------------------------------

        publicadosLayout = new VerticalLayout();

        publicadosLayout.setWidth("45%");
        publicadosLayout.setPadding(true);

        Span publicadosTitulo =
                new Span("🎬 Videos Publicados");

        publicadosTitulo.getStyle()
                .set("font-weight", "bold")
                .set("font-size", "1.1em");

        publicadosLayout.add(
                publicadosTitulo);

        // --------------------------------
        // Videos gustados
        // --------------------------------

        gustadosLayout = new VerticalLayout();

        gustadosLayout.setWidth("45%");
        gustadosLayout.setPadding(true);

        Span gustadosTitulo =
                new Span("❤️ Videos Gustados");

        gustadosTitulo.getStyle()
                .set("font-weight", "bold")
                .set("font-size", "1.1em");

        gustadosLayout.add(
                gustadosTitulo);

        // --------------------------------
        // Layout de las dos listas
        // --------------------------------

        HorizontalLayout listasLayout =
                new HorizontalLayout(
                        publicadosLayout,
                        gustadosLayout);

        listasLayout.setWidthFull();

        listasLayout.setJustifyContentMode(
                JustifyContentMode.CENTER);

        listasLayout.setSpacing(true);

        // --------------------------------
        // Añadir todo en orden
        // --------------------------------

        add(imagenDeFondo);

        if (bloqueado != null) {
            add(bloqueado);
        }

        add(
                topLayout,
                listasLayout);

        // --------------------------------
        // Crear listas
        // --------------------------------

        Videospublicados();

        Videosgustados();
    }

    // --------------------------------
    // Videos publicados
    // --------------------------------

    public void Videospublicados() {

        _videospublicados =
                new Videospublicados(
                        _usuario.getHa_publicado(),
                        viewFactory);

        publicadosLayout.add(
                _videospublicados);
    }

    // --------------------------------
    // Videos gustados
    // --------------------------------

    public void Videosgustados() {

        _videosgustados =
                new Videosgustados(
                        _usuario.getLe_gusta(),
                        viewFactory);

        gustadosLayout.add(
                _videosgustados);
    }

    // --------------------------------
    // Youtubers seguidos
    // --------------------------------

    public void Youtubersseguidos() {

        UI.getCurrent().navigate(
                Youtubersseguidos.class,
                _usuario.getLogin());
    }
}