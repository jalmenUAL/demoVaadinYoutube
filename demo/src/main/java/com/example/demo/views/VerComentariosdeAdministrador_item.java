package com.example.demo.views;

import com.example.demo.factories.ViewFactory;
import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.services.iAdministrador;
import com.example.demo.tables.Comentario;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;

@Route("VerComentariosdeAdministrador_item")
public class VerComentariosdeAdministrador_item extends VerComentarios_item {

    public iAdministrador iAdministrador;
    public Button eliminarButton;

    public VerComentariosdeAdministrador_item(iAdministrador iAdministrador, Comentario comentario,
            ViewFactoryProvider viewFactory) {
        super(comentario, viewFactory);
        this.iAdministrador = iAdministrador;
        

    }

    @Override
    protected void build() {
        super.build();
        eliminarButton = new Button("Eliminar");
        eliminarButton.getStyle()
                .set("background-color", "#007BFF") // azul
                .set("color", "white") // texto blanco
                .set("border-radius", "8px") // bordes redondeados
                .set("padding", "10px 20px") // espaciado interno
                .set("font-weight", "bold");

        HorizontalLayout centrarLayout = new HorizontalLayout(eliminarButton);
        centrarLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        centrarLayout.setWidthFull();

        add(centrarLayout);
    }
    
 
     

    @Override
    protected void bindEvents() {
         eliminarButton.addClickListener(e -> eliminar());
         
       
    }

    public void eliminar() {
        iAdministrador.eliminarComentario(model.getId());
        UI.getCurrent().getPage().reload();
    }
}