package com.example.demo.views;

import com.example.demo.factories.ViewFactory;
import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.tables.Video;
import com.vaadin.flow.router.Route;

@Route("UltimosVideos_item")

public class UltimosVideos_item extends GaleradeVideos_item {
    public UltimosVideos_item(Video video, ViewFactoryProvider viewFactory) {

        super(video, viewFactory);
        

    }
}
