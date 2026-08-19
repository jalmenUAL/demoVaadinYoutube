package com.example.demo.views;

import java.util.List;
import java.util.Vector;

import com.example.demo.factories.ViewFactory;
import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.patterns.BaseListView;
import com.example.demo.tables.Video;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;

@Route("GaleriadeVideos")
public class GaleradeVideos extends BaseListView<Video> {

    public Vector<GaleradeVideos_item> _item = new Vector<>();

    protected HorizontalLayout carrusel;
    protected H2 tituloGaleria;

    protected ViewFactoryProvider viewFactory;

    public GaleradeVideos(List<Video> videos, ViewFactoryProvider factory) {
        super(videos);
        this.viewFactory = factory;
        initView();
    }

     @Override
    protected void build() {
        super.build();
        tituloGaleria.setText("Galería de Videos");

    }

    @Override
    protected void bindEvents() {
        // Esta vista no tiene eventos propios.
    }



    @Override
    protected void buildContainer() {
         setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.START);
        tituloGaleria = new H2("Galería de Videos");

        tituloGaleria.getStyle()
                .set("color", "#2c3e50")
                .set("margin-top", "20px")
                .set("margin-bottom", "10px");


        carrusel = new HorizontalLayout();

        carrusel.setSpacing(true);
        carrusel.setPadding(true);
        carrusel.setWidthFull();
        carrusel.setJustifyContentMode(
                FlexComponent.JustifyContentMode.CENTER
        );


        add(tituloGaleria, carrusel);
    }

    @Override
    protected void buildItems() {
         
           
        for (Video video :  elements) {
        _item.add(new GaleradeVideos_item(video, viewFactory));
         carrusel.add(new GaleradeVideos_item(video, viewFactory));
}
    }

  

}