package com.example.demo.views.common;

import java.util.Set;

import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.tables.Video;
import com.vaadin.flow.router.Route;

@Route("Videospublicados")

public class Videospublicados extends ListadeVideos {
	public Videospublicados(Set<Video> videos, ViewFactoryProvider viewFactory) {
		super(videos, viewFactory);

	}

}