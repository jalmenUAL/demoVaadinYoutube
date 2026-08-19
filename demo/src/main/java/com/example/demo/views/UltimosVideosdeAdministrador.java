package com.example.demo.views;

import java.util.List;

import com.example.demo.factories.ViewFactory;
import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.tables.Video;
import com.vaadin.flow.router.Route;

@Route("UltimosVideosdeAdministrador")
public class UltimosVideosdeAdministrador extends UltimosVideos {

public UltimosVideosdeAdministrador(List<Video> videos, ViewFactoryProvider viewFactory) {
		
		super(videos, viewFactory);
		 
			
			
	}
public void build() {
		
		super.build();
		tituloGaleria.setText("Todos los videos");
		
	}
	
}