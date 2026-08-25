package com.example.demo.views;

import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.tables.Video;
import com.vaadin.flow.router.Route;

@Route("UltimosVideosdeAdministrador_item")
public class UltimosVideosdeAdministrador_item
        extends UltimosVideos_item {

    /*
     * Esta clase representa el elemento de vídeo que utilizará
     * el administrador.
     *
     * Reutiliza toda la implementación de UltimosVideos_item,
     * que a su vez hereda de GaleradeVideos_item.
     *
     * Actualmente no necesita añadir ninguna lógica propia.
     */

    public UltimosVideosdeAdministrador_item(
            Video video,
            ViewFactoryProvider viewFactory) {

        /*
         * Delegamos en el constructor de la clase padre.
         */
        super(video, viewFactory);
    }
}