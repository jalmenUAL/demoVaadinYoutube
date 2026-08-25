package com.example.demo.views;

import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.tables.Video;
import com.vaadin.flow.router.Route;

@Route("UltimosVideos_item")
public class UltimosVideos_item
        extends GaleradeVideos_item {

    /*
     * UltimosVideos_item reutiliza completamente la
     * implementación de GaleradeVideos_item.
     *
     * Hereda:
     *  - la información del vídeo
     *  - la miniatura
     *  - los likes y comentarios
     *  - la navegación al vídeo
     *  - el layout
     *  - los eventos
     */

    public UltimosVideos_item(
            Video video,
            ViewFactoryProvider viewFactory) {

        /*
         * Delegamos en el constructor de la clase padre.
         */
        super(video, viewFactory);
    }
}