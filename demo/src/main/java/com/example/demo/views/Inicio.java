package com.example.demo.views;

import com.example.demo.factories.ViewFactory;
import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.patterns.BaseView;
import com.example.demo.services.iInicio;
import com.vaadin.flow.component.AttachEvent;
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

    protected ViewFactoryProvider viewFactory;

    public Inicio(iInicio iInicio,ViewFactoryProvider viewFactory) {
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
        
       
    }
 
    //Es necesario que se llame a este método en el onAttach de las clases que heredan de Inicio, para que se muestren los últimos videos al cargar la vista.
 @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        UltimosVideos();
    }
    

   
 

     protected abstract void UltimosVideos();
}
