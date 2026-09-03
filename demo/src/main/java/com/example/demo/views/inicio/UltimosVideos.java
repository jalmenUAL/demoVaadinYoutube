package com.example.demo.views.inicio;

import java.util.List;
import java.util.Set;

import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.tables.Video;
import com.example.demo.views.common.GaleradeVideos;
import com.vaadin.flow.router.Route;

@Route("UltimosVideos")
public class UltimosVideos
        extends GaleradeVideos {

    /*
     * Esta clase reutiliza toda la estructura de GaleradeVideos:
     *
     *  - el contenedor de la galería
     *  - el título
     *  - el carrusel
     *  - la construcción de los elementos
     *  - los elementos GaleradeVideos_item
     *
     * Solo necesitamos modificar el título para indicar
     * que estamos mostrando los últimos vídeos.
     */

    

    public UltimosVideos(
            List<Video> videos,
            ViewFactoryProvider viewFactory) {

        super(videos, viewFactory);
    }

    @Override
    protected void buildContainer() {

        /*
         * Construimos primero la vista de la clase padre.
         */
        super.buildContainer();

        /*
         * Especializamos la vista cambiando únicamente
         * el título.
         */
        tituloGaleria.setText("Últimos Videos");
    }
}