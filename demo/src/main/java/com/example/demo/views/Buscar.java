package com.example.demo.views;

import com.example.demo.services.iInicio;
import com.example.demo.tables.Video;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import java.util.List;

@Route("Buscar")
public class Buscar extends VerticalLayout {
	public Inicio _inicio;
	public ResultadodeBusqueda _resultadodeBusqueda;
	private TextField textobuscar;

	public Button botonbuscar;

	iInicio iInicio;

	List<Video> resultados;

	Buscar(iInicio iInicio) {
		this.iInicio = iInicio;

		textobuscar = new TextField();
		textobuscar.setPlaceholder("Introduzca el nombre del video que quiere buscar");
		textobuscar.setWidthFull();

		botonbuscar = new Button("Buscar");
		botonbuscar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		botonbuscar.addClickListener(e -> {
			String texto = textobuscar.getValue();
		resultados = iInicio.buscar(texto);
		ResultadodeBusqueda();
		});

		HorizontalLayout buscarLayout = new HorizontalLayout(textobuscar, botonbuscar);
		buscarLayout.setWidthFull();
		buscarLayout.setFlexGrow(1, textobuscar);

		add(buscarLayout);

	}


	public void ResultadodeBusqueda() {
		_resultadodeBusqueda = new ResultadodeBusqueda(resultados);

	}


	
};
