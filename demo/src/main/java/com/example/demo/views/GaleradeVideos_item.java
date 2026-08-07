package com.example.demo.views;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.factories.ViewFactory;
import com.example.demo.patterns.BaseItemView;
import com.example.demo.tables.Video;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;

@Route("GaleriadeVideos_item")
public class GaleradeVideos_item extends BaseItemView<Video> {

    public GaleradeVideos _galeradeVideos;
    public VerVideo _verVideo;
    private Image thumbnail;
    protected ViewFactory viewFactory;

    public GaleradeVideos_item(Video video, ViewFactory viewFactory) {
       super(video);      	 
        this.viewFactory = viewFactory;
    }
 

    @Override
    protected void build() {
         setWidthFull();
        setSpacing(true);
        Span tituloSpan = new Span(model.getTitulo());

        tituloSpan.getStyle()
                .set("font-weight", "bold")
                .set("font-size", "1.2em");


        int numMeGustas = model.getLe_gusta_a().size();
        int numComentarios = model.getTiene_comentarios().size();


        Avatar propietarioAvatar =
                new Avatar(
                        model.getEs_de().getLogin(),
                        model.getEs_de().getFotoPerfil()
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
                    videoId.substring(0, videoId.indexOf("?"));
        }

        if (videoId.contains("#")) {
            videoId =
                    videoId.substring(0, videoId.indexOf("#"));
        }


        String thumbnailUrl =
                "https://img.youtube.com/vi/"
                        + videoId
                        + "/hqdefault.jpg";


        thumbnail =
                new Image(
                        thumbnailUrl,
                        "Thumbnail del video"
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

        UI.getCurrent().navigate(
                viewFactory.createVideo(),
                model.getId()
        );
    }
}