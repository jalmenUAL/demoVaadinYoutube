package com.example.demo.views;

import java.util.List;

import com.example.demo.tables.Video;
import com.vaadin.flow.router.Route;

@Route("UltimosVideosdeAdministrador")
public class UltimosVideosdeAdministrador extends UltimosVideos {

public UltimosVideosdeAdministrador(List<Video> videos) {

		super(videos);	
		tituloGaleria.setText("Todos los videos");		
	}
}