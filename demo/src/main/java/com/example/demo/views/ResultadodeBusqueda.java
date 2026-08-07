package com.example.demo.views;

import java.util.List;

import com.example.demo.factories.ViewFactory;
import com.example.demo.tables.Video;
import com.vaadin.flow.router.Route;

@Route("ResultadodeBusqueda")

public class ResultadodeBusqueda extends GaleradeVideos {

    public Buscar _buscar;

    public ResultadodeBusqueda(List<Video> resultados, ViewFactory viewFactory) {
        super(resultados, viewFactory);
    }

    @Override
    protected void build() {
        tituloGaleria.setText("Resultados de la búsqueda");
        super.build();

    }

    @Override
    protected void buildItems() {
        for (Video video : elements) {
            ResultadodeBusqueda_item item = new ResultadodeBusqueda_item(video, viewFactory);
            carrusel.add(item);
        }
    }
}