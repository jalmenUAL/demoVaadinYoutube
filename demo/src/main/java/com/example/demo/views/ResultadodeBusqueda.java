package com.example.demo.views;

import java.util.List;

import com.example.demo.tables.Video;
import com.vaadin.flow.router.Route;

@Route("ResultadodeBusqueda")

public class ResultadodeBusqueda extends GaleradeVideos {

    public Buscar _buscar;

    public ResultadodeBusqueda(List<Video> resultados) {
        super(resultados);
    }

    @Override
    protected void build() {

        super.build();

        tituloGaleria.setText("Resultados de la búsqueda");

        carrusel.removeAll();

        if (videos == null || videos.isEmpty()) {

             

            return;
        }

        for (Video video : videos) {

            carrusel.add(
                    new ResultadodeBusqueda_item(video)
            );

        }
    }
}