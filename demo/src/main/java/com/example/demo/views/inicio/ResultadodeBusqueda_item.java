package com.example.demo.views.inicio;

import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.tables.Video;
import com.example.demo.views.common.GaleradeVideos_item;
import com.vaadin.flow.router.Route;

@Route("ResultadodeBusqueda_item")
public class ResultadodeBusqueda_item
        extends GaleradeVideos_item {

    /*
     * Esta clase reutiliza completamente la implementación de
     * GaleradeVideos_item.
     *
     * No necesitamos volver a escribir:
     *  - build()
     *  - bindEvents()
     *  - VerVideo()
     *  - la construcción de la miniatura
     *  - la información del vídeo
     *
     * Esto es posible porque ResultadodeBusqueda_item
     * hereda de GaleradeVideos_item.
     */

    public ResultadodeBusqueda_item(
            Video video,
            ViewFactoryProvider viewFactory) {

        /*
         * El constructor de la clase hija debe llamar al constructor
         * de la clase padre mediante super().
         *
         * El padre se encarga de almacenar el Video y configurar
         * la vista mediante BaseItemView.
         */
        super(video, viewFactory);
    }
}