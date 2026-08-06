package com.example.demo.views;

import java.util.List;

import com.example.demo.patterns.BaseView;
import com.example.demo.services.iInicio;
import com.example.demo.tables.Video;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("Buscar")
public class Buscar extends BaseView {

    public ResultadodeBusqueda _resultadodeBusqueda;

    private final iInicio _iInicio;

    private TextField textoBuscar;
    public Button botonBuscar;

    private List<Video> resultados;

    public Buscar(iInicio iInicio) {
		 
        this._iInicio = iInicio;
    }
 

    @Override
    protected void build() {
		 setWidthFull();
        textoBuscar = new TextField();
        textoBuscar.setPlaceholder(
                "Introduzca el nombre del vídeo que quiere buscar");
        textoBuscar.setWidthFull();

        botonBuscar = new Button("Buscar");
        botonBuscar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        HorizontalLayout buscarLayout =
                new HorizontalLayout(textoBuscar, botonBuscar);
        buscarLayout.setWidthFull();
        buscarLayout.setFlexGrow(1, textoBuscar);

        add(buscarLayout);
    }

    @Override
    protected void bindEvents() {

        botonBuscar.addClickListener(e -> {
            resultados = _iInicio.buscar(textoBuscar.getValue());
            ResultadodeBusqueda();
        });

    }

 

    public void ResultadodeBusqueda() {
        _resultadodeBusqueda = new ResultadodeBusqueda(resultados);
    }
}
