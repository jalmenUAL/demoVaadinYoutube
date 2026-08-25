package com.example.demo.views;

import java.util.Set;

import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.tables.Video;
import com.vaadin.flow.router.Route;

@Route("Videosgustados")

public class Videosgustados extends ListadeVideos {
	public Videosgustados(Set<Video> videos, ViewFactoryProvider viewFactory) {
		super(videos, viewFactory);

	}

}