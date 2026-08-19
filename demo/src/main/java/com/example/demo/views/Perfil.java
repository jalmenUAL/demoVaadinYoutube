package com.example.demo.views;

import com.example.demo.factories.ViewFactory;
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

    public Perfil(iInicio iInicio, ViewFactoryProvider viewFactory) {
        this._iInicio = iInicio;
        this.viewFactory = viewFactory;
         
        
    }
 
    @Override
    protected void bindEvents() {

        btnYoutubersSeguidos.addClickListener(
                e -> Youtubersseguidos());

    }

   

    public void Youtubersseguidos() {

        UI.getCurrent()
                .navigate(
                        Youtubersseguidos.class,
                        _usuario.getLogin());

    }

    @Override
    protected void build(String parameter) {
         _usuario = _iInicio.findYoutuberById(parameter);
        setSizeFull();
        setSpacing(true);
        setPadding(false);
        setAlignItems(Alignment.CENTER);
        topLayout = new HorizontalLayout();

        topLayout.setAlignItems(Alignment.CENTER);
        topLayout.setSpacing(true);
        topLayout.setPadding(true);

        btnYoutubersSeguidos = new Button(
                "Ver Youtubers Seguidos",
                new Icon(VaadinIcon.USER_HEART));

        btnYoutubersSeguidos.addThemeVariants(
                ButtonVariant.LUMO_PRIMARY);

        btnYoutubersSeguidos.getStyle()
                .set("border-radius", "8px")
                .set("font-weight", "bold")
                .set("padding", "8px 16px");

        publicadosLayout = new VerticalLayout();
        publicadosLayout.setWidth("45%");

        Span publicadosTitulo = new Span("🎬 Videos Publicados");

        publicadosTitulo.getStyle()
                .set("font-weight", "bold")
                .set("font-size", "1.1em");

        publicadosLayout.add(publicadosTitulo);

        gustadosLayout = new VerticalLayout();
        gustadosLayout.setWidth("45%");

        Span gustadosTitulo = new Span("❤️ Videos Gustados");

        gustadosTitulo.getStyle()
                .set("font-weight", "bold")
                .set("font-size", "1.1em");

        gustadosLayout.add(gustadosTitulo);

        HorizontalLayout listasLayout = new HorizontalLayout(
                publicadosLayout,
                gustadosLayout);

        listasLayout.setJustifyContentMode(
                JustifyContentMode.CENTER);

        listasLayout.setSpacing(true);
        listasLayout.setWidthFull();

        add(
                topLayout,
                listasLayout);

        Image imagenDeFondo = new Image(
                _usuario.getBanner(),
                "Imagen de fondo");

        if (_usuario.getBanner() == null
                || _usuario.getBanner().isEmpty()) {

            imagenDeFondo.setSrc(
                    "https://via.placeholder.com/1200x300");

        }

        imagenDeFondo.setWidth("100%");
        imagenDeFondo.setHeight("300px");

        imagenDeFondo.getStyle()
                .set("object-fit", "cover");

        addComponentAtIndex(
                0,
                imagenDeFondo);

        if (_usuario.getBloqueado()) {

            H2 bloqueado = new H2(
                    "Este Usuario ha sido Bloqueado");

            bloqueado.getStyle()
                    .set("color", "red");

            addComponentAtIndex(
                    1,
                    bloqueado);

        }

        H2 titulo = new H2(
                "Perfil del Youtuber");

        titulo.getStyle()
                .set("color", "#2c3e50")
                .set("margin-top", "10px");

        Avatar avatar = new Avatar(
                _usuario.getLogin(),
                _usuario.getFotoPerfil());

        avatar.setWidth("100px");
        avatar.setHeight("100px");

        Span nombre = new Span(
                _usuario.getLogin());

        nombre.getStyle()
                .set("font-weight", "bold")
                .set("font-size", "1.3em")
                .set("margin-left", "10px");

        topLayout.add(
                titulo,
                avatar,
                nombre,
                btnYoutubersSeguidos);

        Videospublicados();

        Videosgustados();
    }

     public void Videospublicados() {

        _videospublicados = new Videospublicados(
                _usuario.getHa_publicado(), viewFactory);

        publicadosLayout.add(
                _videospublicados);

    }

    public void Videosgustados() {

        _videosgustados = new Videosgustados(
                _usuario.getLe_gusta(), viewFactory);

        gustadosLayout.add(
                _videosgustados);

    }

}