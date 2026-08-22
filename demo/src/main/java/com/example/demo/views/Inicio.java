package com.example.demo.views;

import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.patterns.BaseAppView;
import com.example.demo.services.iInicio;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("Inicio")
@AnonymousAllowed

public abstract class Inicio extends BaseAppView {

    protected final iInicio iInicio;

    protected Buscar _buscar;
    protected UltimosVideos _ultimosVideos;

    protected ViewFactoryProvider viewFactory;

    public Inicio(iInicio iInicio, ViewFactoryProvider viewFactory) {
        super();
        this.iInicio = iInicio;
        this.viewFactory = viewFactory;

    }

    @Override
    protected void build() {

        Div youtubeLogo = new Div();
        youtubeLogo.setText("YouTube");

        youtubeLogo.getStyle()
                .set("background-color", "#FF0000")
                .set("color", "white")
                .set("font-weight", "bold")
                .set("font-size", "1.8em")
                .set("padding", "10px 22px")
                .set("border-radius", "8px");

        header.setAlignItems(Alignment.CENTER);
        header.setSpacing(true);

        header.add(youtubeLogo);

        _buscar = new Buscar(iInicio, viewFactory);

        header.add(_buscar);
    }

    @Override
    protected void bindEvents() {

        _buscar.setOnResultado(resultados -> {

            body.removeAll();
            if (resultados.isEmpty()) {
                Div noResultsDiv = new Div();
                noResultsDiv.getStyle()
                        .set("font-size", "1.7em")
                        .set("color", "#555")
                        .set("padding", "20px")
                        .set("text-align", "center");
                noResultsDiv.setText("No se encontraron resultados.");
                body.add(noResultsDiv);
                return;
            } 
            ResultadodeBusqueda vista = new ResultadodeBusqueda(
                    resultados,
                    viewFactory);

            body.add(vista);
        });
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        UltimosVideos();
    }

    protected abstract void UltimosVideos();
}