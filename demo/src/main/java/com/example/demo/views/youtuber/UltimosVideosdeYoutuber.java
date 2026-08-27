package com.example.demo.views.youtuber;

import java.util.List;

import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.tables.Video;
import com.example.demo.views.inicio.UltimosVideos;
import com.vaadin.flow.router.Route;

@Route("UltimosVideosdeYoutuber")
public class UltimosVideosdeYoutuber
        extends UltimosVideos {

    public UltimosVideosdeYoutuber(
            List<Video> videos,
            ViewFactoryProvider viewFactory) {

        super(videos, viewFactory);
    }

    @Override
    protected void buildContainer() {

        super.buildContainer();

        tituloGaleria.setText(
                "Videos Propios o de Youtubers que sigues");
    }
}