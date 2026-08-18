package com.example.demo.views;

import com.example.demo.factories.ViewFactory;
import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.tables.Video;
import com.vaadin.flow.router.Route;

@Route("ResultadodeBusqueda_item")
public class ResultadodeBusqueda_item extends GaleradeVideos_item {

    public ResultadodeBusqueda_item(Video video, ViewFactoryProvider viewFactory) {
        super(video, viewFactory);

    }
}