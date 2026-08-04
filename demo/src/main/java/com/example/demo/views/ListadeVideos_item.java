package com.example.demo.views;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.patterns.BaseItemView;
import com.example.demo.tables.Video;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;

@Route("ListadeVideos_item")
public class ListadeVideos_item extends BaseItemView<Video> {

    public ListadeVideos _listadeVideos;
    public VerVideo _verVideo;

    private Image thumbnail;


    public ListadeVideos_item(Video video) {
        super(video);
    }

    @Override
    protected void build() {
          setWidthFull();
        setSpacing(true);
        String tituloVideo = model.getTitulo();
        String propietarioNombre = model.getEs_de().getLogin();
        String propietarioFotoUrl = model.getEs_de().getFotoPerfil();

        int numMeGustas =
                model.getLe_gusta_a().size();

        int numComentarios =
                model.getTiene_comentarios().size();


        Span tituloSpan =
                new Span(tituloVideo);

        tituloSpan.getStyle()
                .set("font-weight", "bold")
                .set("font-size", "1.2em");


        Avatar propietarioAvatar =
                new Avatar(
                        propietarioNombre,
                        propietarioFotoUrl
                );


        HorizontalLayout infoLayout =
                new HorizontalLayout(
                        propietarioAvatar,
                        tituloSpan
                );

        infoLayout.setAlignItems(Alignment.CENTER);
        infoLayout.setSpacing(true);


        Span meGustasSpan =
                new Span("👍 " + numMeGustas);

        Span comentariosSpan =
                new Span("💬 " + numComentarios);


        HorizontalLayout statsLayout =
                new HorizontalLayout(
                        meGustasSpan,
                        comentariosSpan
                );

        statsLayout.setSpacing(true);


        String videoId =
                model.getUrl()
                        .substring(
                                model.getUrl().lastIndexOf("/") + 1
                        );


        if (videoId.contains("?")) {
            videoId =
                    videoId.substring(
                            0,
                            videoId.indexOf("?")
                    );
        }


        if (videoId.contains("#")) {
            videoId =
                    videoId.substring(
                            0,
                            videoId.indexOf("#")
                    );
        }


        String thumbnailUrl =
                "https://img.youtube.com/vi/"
                        + videoId
                        + "/hqdefault.jpg";


        thumbnail =
                new Image(
                        thumbnailUrl,
                        "Miniatura del video"
                );

        thumbnail.setWidth("100%");

        thumbnail.getStyle()
                .set("border-radius", "8px")
                .set("cursor", "pointer");


        add(
                infoLayout,
                statsLayout,
                thumbnail
        );

    }


    @Override
    protected void bindEvents() {

        thumbnail.addClickListener(e -> VerVideo());

    }


  


    public void VerVideo() {

        Authentication auth =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();


        if (auth != null && auth.isAuthenticated()) {


            boolean esAdmin =
                    auth.getAuthorities()
                            .stream()
                            .anyMatch(a ->
                                    a.getAuthority()
                                            .equals("ROLE_ADMINISTRADOR"));


            boolean esYoutuber =
                    auth.getAuthorities()
                            .stream()
                            .anyMatch(a ->
                                    a.getAuthority()
                                            .equals("ROLE_YOUTUBER"));


            if (esAdmin) {

                UI.getCurrent()
                        .navigate(
                                VerVideodeAdministrador.class,
                                model.getId()
                        );


            } else if (esYoutuber) {

                UI.getCurrent()
                        .navigate(
                                VerVideodeYoutuber.class,
                                model.getId()
                        );

            }


        } else {

            UI.getCurrent()
                    .navigate(
                            VerVideo.class,
                            model.getId()
                    );

        }

    }
}