package com.example.demo.views;

import com.example.demo.factories.ViewFactory;
import com.example.demo.tables.Video;
import com.vaadin.flow.router.Route;

@Route("Videospublicados_item")

public class Videospublicados_item extends ListadeVideos_item {

    public Videospublicados_item(Video video, ViewFactory viewFactory) {
        super(video, viewFactory);
     }
}