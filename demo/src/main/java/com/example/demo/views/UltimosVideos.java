package com.example.demo.views;

import java.util.List;

import com.example.demo.factories.ViewFactory;
import com.example.demo.tables.Video;
import com.vaadin.flow.router.Route;

@Route("UltimosVideos")
public class UltimosVideos extends GaleradeVideos {

    public Inicio _inicio;

    public UltimosVideos(List<Video> videos, ViewFactory viewFactory) {
        super(videos, viewFactory);
    }

    @Override
    protected void build() {

       

        super.build();
         tituloGaleria.setText("Últimos Videos");

    }
}