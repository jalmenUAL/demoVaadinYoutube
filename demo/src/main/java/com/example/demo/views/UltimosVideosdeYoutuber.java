package com.example.demo.views;

import java.util.List;

import com.example.demo.factories.ViewFactory;
import com.example.demo.tables.Video;
import com.vaadin.flow.router.Route;

@Route("UltimosVideosdeYoutuber")
public class UltimosVideosdeYoutuber extends UltimosVideos {

public UltimosVideosdeYoutuber(List<Video> videos, ViewFactory viewFactory) {
	super(videos, viewFactory);	 
	
}

public void build() {
		tituloGaleria.setText("Videos Propios o de Youtubers que sigues");
		super.build();
		
	}
	 
}