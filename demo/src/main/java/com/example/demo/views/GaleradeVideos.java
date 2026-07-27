package com.example.demo.views;

import java.util.List;
import java.util.Vector;

import com.example.demo.tables.Video;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("GaleriadeVideos")
public class GaleradeVideos extends VerticalLayout {
    public Vector<GaleradeVideos_item> _item = new Vector<GaleradeVideos_item>();

    HorizontalLayout carrusel = new HorizontalLayout();
    public H2 tituloGaleria = new H2("Galería de Videos");

    public GaleradeVideos(List<Video> videos) {

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.START);

        tituloGaleria.getStyle()
                .set("color", "#2c3e50")
                .set("margin-top", "20px")
                .set("margin-bottom", "10px");

        carrusel.setSpacing(true);
        carrusel.setPadding(true);
        carrusel.setWidthFull();
        carrusel.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        add(tituloGaleria, carrusel);

    }
}