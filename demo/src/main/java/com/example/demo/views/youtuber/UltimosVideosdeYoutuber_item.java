package com.example.demo.views.youtuber;

import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.tables.Video;
import com.example.demo.views.inicio.UltimosVideos_item;
import com.vaadin.flow.router.Route;

@Route("UltimosVideosdeYoutuber_item")
public class UltimosVideosdeYoutuber_item
        extends UltimosVideos_item {

    /*
     * Elemento de vídeo específico para el Youtuber.
     *
     * Hereda toda la implementación de UltimosVideos_item,
     * por lo que actualmente no necesita añadir lógica propia.
     */

    public UltimosVideosdeYoutuber_item(
            Video video,
            ViewFactoryProvider viewFactory) {

        super(video, viewFactory);
    }
}