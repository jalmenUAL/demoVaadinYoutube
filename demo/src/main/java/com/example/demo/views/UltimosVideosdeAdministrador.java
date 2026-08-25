package com.example.demo.views;

import java.util.List;

import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.tables.Video;
import com.vaadin.flow.router.Route;

@Route("UltimosVideosdeAdministrador")
public class UltimosVideosdeAdministrador
        extends UltimosVideos {

    public UltimosVideosdeAdministrador(
            List<Video> videos,
            ViewFactoryProvider viewFactory) {

        super(videos, viewFactory);
    }

    /*
     * Personalizamos la vista para el administrador.
     *
     * Aunque la clase se llama "UltimosVideos", en el caso
     * del administrador realmente se muestran todos los vídeos.
     */
    @Override
    protected void buildContainer() {

        super.buildContainer();

        tituloGaleria.setText("Todos los videos");
    }
}