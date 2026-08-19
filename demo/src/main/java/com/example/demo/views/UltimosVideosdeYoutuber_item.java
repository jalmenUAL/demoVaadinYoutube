package com.example.demo.views;

import com.example.demo.factories.ViewFactory;
import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.tables.Video;
import com.vaadin.flow.router.Route;

@Route("UltimosVideosdeYoutuber_item")
public class UltimosVideosdeYoutuber_item extends UltimosVideos_item {

    public UltimosVideosdeYoutuber_item(Video video, ViewFactoryProvider viewFactory) {
        super(video, viewFactory);
         
    }
	
	
}