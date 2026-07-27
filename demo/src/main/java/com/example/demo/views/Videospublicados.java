package com.example.demo.views;

import java.util.Set;

import com.example.demo.tables.Video;
import com.vaadin.flow.router.Route;

@Route("Videospublicados")

public class Videospublicados extends ListadeVideos {
	public Videospublicados(Set<Video> videos) {
		super(videos);
 	}

	public Perfil _perfil;
}