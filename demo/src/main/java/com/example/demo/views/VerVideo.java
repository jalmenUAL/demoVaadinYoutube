package com.example.demo.views;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.services.iInicio;
import com.example.demo.tables.Video;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("VerVideo")
@AnonymousAllowed
public class VerVideo extends VerticalLayout implements HasUrlParameter<Integer> {
    public Videosrelacionados _videosrelacionados;
    public ListadeVideos_item _listadeVideos;
    public GaleradeVideos_item _galeradeVideos;
    public VerComentarios _verComentarios;
    public PerfilAjeno _perfilAjeno;
    HorizontalLayout video_y_relacionados = new HorizontalLayout();
    VerticalLayout frame_y_comentarios = new VerticalLayout();
    VerticalLayout comentarios = new VerticalLayout();
    VerticalLayout relacionados = new VerticalLayout();
    Video video;
    iInicio iInicio;

    

    public VerVideo(iInicio iInicio) {
        this.iInicio = iInicio;

    }

    public void setParameter(BeforeEvent event, Integer parameter) {

        removeAll();  
        video_y_relacionados.removeAll();
        frame_y_comentarios.removeAll();
        relacionados.removeAll();
        comentarios.removeAll();

        video = iInicio.findVideoById(parameter);
        add(video_y_relacionados);
        video_y_relacionados.add(frame_y_comentarios);
        video_y_relacionados.getStyle().set("width", "100%");

        
        Image avatar = new Image(video.getEs_de().getFotoPerfil(), "Avatar");
        avatar.setWidth("50px");
        avatar.setHeight("50px");
        avatar.getStyle().set("border-radius", "50%");

        avatar.addClickListener(e -> PerfilAjeno());

        String nombreUsuario = video.getEs_de().getLogin();
        String tituloVideo = video.getTitulo();

        VerticalLayout infoUsuario = new VerticalLayout();
        infoUsuario.setSpacing(false);
        infoUsuario.setPadding(false);
        infoUsuario.add(new Span(nombreUsuario));

        H2 titulo = new H2(tituloVideo);

        HorizontalLayout cabecera = new HorizontalLayout(avatar, infoUsuario);
        cabecera.setAlignItems(Alignment.CENTER);
        cabecera.setSpacing(true);
        cabecera.setWidthFull();

        VerticalLayout cabeceraCompleta = new VerticalLayout(titulo, cabecera);
        cabeceraCompleta.setSpacing(false);
        cabeceraCompleta.setPadding(false);

        frame_y_comentarios.add(cabeceraCompleta);

        
        String videoId = video.getUrl().substring(video.getUrl().lastIndexOf("/") + 1);
        if (videoId.contains("?")) {
            videoId = videoId.substring(0, videoId.indexOf("?"));
        }
        if (videoId.contains("#")) {
            videoId = videoId.substring(0, videoId.indexOf("#"));
        }
        String thumbnailUrl = "https://img.youtube.com/vi/" + videoId + "/hqdefault.jpg";
        Image thumbnail = new Image(thumbnailUrl, "Thumbnail del video");
        thumbnail.setWidth("100%");
        thumbnail.getStyle().set("border-radius", "8px").set("cursor", "pointer");
        String embedUrl = "https://www.youtube.com/embed/" + videoId;

        Div iframeContainer = new Div();
        iframeContainer.getElement().setProperty("innerHTML",
                "<iframe width='100%' height='600' " +
                        "src='" + embedUrl + "' " +
                        "title='YouTube video player' frameborder='0' " +
                        "allow='accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture' "
                        +
                        "allowfullscreen></iframe>");
        iframeContainer.setWidth("100%");

        frame_y_comentarios.add(iframeContainer);
        frame_y_comentarios.getStyle().set("width", "350%");
        Videosrelacionados();
        VerComentarios();
        frame_y_comentarios.add(comentarios);
        video_y_relacionados.add(relacionados);
        getStyle().set("width", "100%");

    }

    public void Videosrelacionados() {
        relacionados.removeAll();
        List<Video> videosrelacionados = iInicio.getVideosRelacionados(video.getId());
        _videosrelacionados = new Videosrelacionados(videosrelacionados);
        relacionados.add(_videosrelacionados);
    }

    public void VerComentarios() {
        comentarios.removeAll();
        _verComentarios = new VerComentarios(video.getTiene_comentarios());
        comentarios.add(_verComentarios);

    }

    public void PerfilAjeno() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated()) {
            com.example.demo.tables.Youtuber usuario = (com.example.demo.tables.Youtuber) auth.getPrincipal();
            boolean esAdmin = auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMINISTRADOR"));
            boolean esYoutuber = auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_YOUTUBER"));

            if (esAdmin) {
                UI.getCurrent().navigate(PerfilAjenodeAdministrador.class, video.getEs_de().getLogin());
            } else if (esYoutuber) {
                if (video.getEs_de().getLogin().equals(usuario.getLogin())) {
                    UI.getCurrent().navigate(PerfilPropio.class, video.getEs_de().getLogin());
                } else {
                    UI.getCurrent().navigate(PerfilAjenodeYoutuber.class, video.getEs_de().getLogin());
                }
            }
        } else {
            UI.getCurrent().navigate(PerfilAjeno.class, video.getEs_de().getLogin());

        }

    }

}
