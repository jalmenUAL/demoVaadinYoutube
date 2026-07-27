package com.example.demo.views;

import java.util.List;

import com.example.demo.tables.Video;
import com.vaadin.flow.router.Route;

@Route("UltimosVideosdeYoutuber")
public class UltimosVideosdeYoutuber extends UltimosVideos {

public UltimosVideosdeYoutuber(List<Video> videos) {
	super(videos);	 
	tituloGaleria.setText("Videos Propios o de Youtubers que sigues");
}
	 
}