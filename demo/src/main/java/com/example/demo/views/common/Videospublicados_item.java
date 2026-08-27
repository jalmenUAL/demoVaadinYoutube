package com.example.demo.views.common;

import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.tables.Video;
import com.vaadin.flow.router.Route;

@Route("Videospublicados_item")

public class Videospublicados_item extends ListadeVideos_item {

    public Videospublicados_item(Video video, ViewFactoryProvider viewFactory) {
        super(video, viewFactory);

    }
}