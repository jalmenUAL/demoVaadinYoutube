package com.example.demo.views;

import java.util.List;
import java.util.function.Consumer;

import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.patterns.BaseView;
import com.example.demo.services.iInicio;
import com.example.demo.tables.Video;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class Buscar extends BaseView {

    private final iInicio _iInicio;
    private final ViewFactoryProvider viewFactory;

    private TextField textoBuscar;
    public Button botonBuscar;

    private Consumer<List<Video>> onResultado;

    public Buscar(
            iInicio iInicio,
            ViewFactoryProvider viewFactory) {

        super();

        this._iInicio = iInicio;
        this.viewFactory = viewFactory;

        initView();
    }

    public void setOnResultado(
            Consumer<List<Video>> onResultado) {

        this.onResultado = onResultado;
    }

    @Override
    protected void build() {

        setWidthFull();

        textoBuscar = new TextField();
        textoBuscar.setPlaceholder(
                "Introduzca el nombre del vídeo que quiere buscar");

        textoBuscar.setWidthFull();

        botonBuscar = new Button("Buscar");
        botonBuscar.addThemeVariants(
                ButtonVariant.LUMO_PRIMARY);

        HorizontalLayout buscarLayout =
                new HorizontalLayout(
                        textoBuscar,
                        botonBuscar);

        buscarLayout.setWidthFull();
        buscarLayout.setFlexGrow(1, textoBuscar);

        add(buscarLayout);
    }

    @Override
    protected void bindEvents() {

        botonBuscar.addClickListener(e -> Buscar());
    }

   public void Buscar() {   
    List<Video> resultados =
            _iInicio.buscar(textoBuscar.getValue());
    if (onResultado != null) {
        onResultado.accept(resultados);
    }  
}
}