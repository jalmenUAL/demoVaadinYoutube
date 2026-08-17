package com.example.demo.views;

import com.example.demo.factories.ViewFactory;
import com.example.demo.patterns.BaseView;
import com.example.demo.services.iInicio;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("Inicio")
@AnonymousAllowed

public abstract class Inicio extends BaseView {

    protected final iInicio iInicio;

    protected Buscar _buscar;
    protected UltimosVideos _ultimosVideos;

    protected HorizontalLayout header;
    protected VerticalLayout body;

    protected ViewFactory viewFactory;

    public Inicio(iInicio iInicio,ViewFactory viewFactory) {
        super();
        this.iInicio = iInicio;
        this.viewFactory = viewFactory;
    }

   

    @Override
    protected void build() {
        setWidthFull();
        setPadding(true);
        setSpacing(true);
        setAlignItems(Alignment.CENTER);
        H1 heading = new H1("YouTube");
        header = new HorizontalLayout();
        body = new VerticalLayout();
        heading.getStyle()
                .set("background-color", "#FF0000")
                .set("color", "white")
                .set("padding", "0.5em 1.5em")
                .set("border-radius", "10px")
                .set("font-size", "2.5em")
                .set("box-shadow", "0 4px 8px rgba(0,0,0,0.2)");

        header.setWidthFull();
        header.setJustifyContentMode(
                JustifyContentMode.CENTER);

        header.add(heading);

        add(header, body);

        _buscar = new Buscar(iInicio, viewFactory);
        header.add(_buscar);
        UltimosVideos();
       
    }
 

    @Override
    protected void bindEvents() {
        _buscar.botonBuscar.addClickListener(e -> {
            body.removeAll();
            body.add(_buscar._resultadodeBusqueda);
        });
        
    }

   
 

     protected abstract void UltimosVideos();
}
