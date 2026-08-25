package com.example.demo.views;

import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.tables.Video;
import com.vaadin.flow.router.Route;

@Route("Videosgustados_item")

public class Videosgustados_item extends ListadeVideos_item {

    public Videosgustados_item(Video video, ViewFactoryProvider viewFactory) {
        super(video, viewFactory);

    }
}