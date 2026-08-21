package com.example.demo.views;

import java.util.List;

import com.example.demo.factories.ViewFactory;
import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.patterns.BaseParameterizedView;
import com.example.demo.services.iInicio;
import com.example.demo.tables.Video;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@AnonymousAllowed
@Route("VerVideo")
public class VerVideo extends BaseParameterizedView<Integer> {

    public Videosrelacionados _videosrelacionados;
    public VerComentarios _verComentarios;
    public PerfilAjeno _perfilAjeno;

    protected final iInicio iInicio;
    protected final ViewFactoryProvider viewFactory;

    protected Video video;

    protected HorizontalLayout video_y_relacionados;
    protected VerticalLayout frame_y_comentarios;
    protected VerticalLayout comentarios;
    protected VerticalLayout relacionados;

    protected Image avatar;

    public VerVideo(iInicio iInicio, ViewFactoryProvider viewFactory) {
        this.iInicio = iInicio;
        this.viewFactory = viewFactory;

    }

    @Override
    protected void bindEvents() {

        avatar.addClickListener(e -> PerfilAjeno());

    }

    public void Videosrelacionados() {
        relacionados.removeAll();
        List<Video> videosrelacionados = iInicio.getVideosRelacionados(video.getId());
        _videosrelacionados = new Videosrelacionados(videosrelacionados, viewFactory);
        relacionados.add(_videosrelacionados);
    }

    public void VerComentarios() {
        comentarios.removeAll();
        _verComentarios = viewFactory.getFactory().createVerComentarios(
                video.getTiene_comentarios(), video.getId(), viewFactory);
        comentarios.add(_verComentarios);
    }

    public void PerfilAjeno() {

        UI.getCurrent().navigate(
                viewFactory.getFactory().createPerfilAjeno(), video.getEs_de().getLogin());
    }

    @Override
    protected void build(Integer parameter) {

        video = iInicio.findVideoById(parameter);
        video_y_relacionados = new HorizontalLayout();
        frame_y_comentarios = new VerticalLayout();
        comentarios = new VerticalLayout();
        relacionados = new VerticalLayout();

        add(video_y_relacionados);

        video_y_relacionados.add(frame_y_comentarios);
        video_y_relacionados.getStyle().set("width", "100%");

        avatar = new Image(
                video.getEs_de().getFotoPerfil(),
                "Avatar");

        avatar.setWidth("50px");
        avatar.setHeight("50px");
        avatar.getStyle().set("border-radius", "50%");

        String nombreUsuario = video.getEs_de().getLogin();
        String tituloVideo = video.getTitulo();

        VerticalLayout infoUsuario = new VerticalLayout();
        infoUsuario.setSpacing(false);
        infoUsuario.setPadding(false);
        infoUsuario.add(new Span(nombreUsuario));

        H2 titulo = new H2(tituloVideo);

        HorizontalLayout cabecera = new HorizontalLayout(
                avatar,
                infoUsuario);

        cabecera.setAlignItems(Alignment.CENTER);
        cabecera.setSpacing(true);
        cabecera.setWidthFull();

        VerticalLayout cabeceraCompleta = new VerticalLayout(
                titulo,
                cabecera);

        cabeceraCompleta.setSpacing(false);
        cabeceraCompleta.setPadding(false);

        frame_y_comentarios.add(cabeceraCompleta);

        String videoId = video.getUrl().substring(
                video.getUrl().lastIndexOf("/") + 1);

        if (videoId.contains("?")) {
            videoId = videoId.substring(
                    0,
                    videoId.indexOf("?"));
        }

        if (videoId.contains("#")) {
            videoId = videoId.substring(
                    0,
                    videoId.indexOf("#"));
        }

        String embedUrl = "https://www.youtube.com/embed/" + videoId;

        Div iframeContainer = new Div();

        iframeContainer.getElement().setProperty(
                "innerHTML",
                "<iframe width='100%' height='600' "
                        + "src='" + embedUrl + "' "
                        + "title='YouTube video player' "
                        + "frameborder='0' "
                        + "allow='accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture' "
                        + "allowfullscreen></iframe>");

        iframeContainer.setWidth("100%");

        frame_y_comentarios.add(iframeContainer);
        frame_y_comentarios.getStyle().set("width", "350%");

        Videosrelacionados();
        VerComentarios();

        frame_y_comentarios.add(comentarios);
        video_y_relacionados.add(relacionados);

        getStyle().set("width", "100%");
    }
}