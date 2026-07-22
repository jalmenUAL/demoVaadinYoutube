package com.example.demo.views;

import com.example.demo.service.iInicio;
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

    protected Buscar buscar;
    protected UltimosVideos _ultimosVideos;

    protected HorizontalLayout header;
    protected VerticalLayout body;

    public Inicio(iInicio iInicio) {
        super();
        this.iInicio = iInicio;
       
    }

    @Override
    protected void configure() {
        setWidthFull();
        setPadding(true);
        setSpacing(true);
        setAlignItems(Alignment.CENTER);
    }

    @Override
    protected void build() {
        header = new HorizontalLayout();
        body = new VerticalLayout();

        H1 heading = new H1("YouTube");
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

         buscar = new Buscar(iInicio);
        header.add(buscar);
    }
 

    @Override
    protected void bindEvents() {
        buscar.botonbuscar.addClickListener(e -> {
            body.removeAll();
            body.add(buscar._resultadodeBusqueda);
        });
    }

    @Override
    protected void configureNavigation() {
        // No es necesario en esta vista.
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        UltimosVideos();
    }

    protected abstract void UltimosVideos();

}
