package com.example.demo.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.example.demo.factories.ViewFactory;
import com.example.demo.patterns.BaseListView;
import com.example.demo.services.iAdministrador;
import com.example.demo.tables.Comentario;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;

@Route("VerComentarios")

public class VerComentarios extends BaseListView<Comentario> {
    public VerVideo _verVideo;
    public List<VerComentarios_item> _item = new ArrayList<>();
    protected ViewFactory factory;
    
 

    public VerComentarios(ViewFactory factory, Set<Comentario> comentarios) {
        super(comentarios);
        this.factory = factory;
       
    }
 
    

    @Override
    protected void bindEvents() {
        
    }

    @Override
    protected void buildContainer() {
        setWidthFull();
        setPadding(true);
        setSpacing(false);  
        setAlignItems(Alignment.STRETCH);
    }

    @Override
    protected void buildItems() {
        

        if (elements.isEmpty()) {
          
            Div noComments = new Div();
            noComments.setText("No hay comentarios disponibles.");
            add(noComments);
        } else {

            for (Comentario e:elements) {
                 
                VerComentarios_item comentario = factory.createComentarioItem(e);
                add(comentario);
            }

                 
            }
        
         Div separator = new Div();
        separator.getStyle()
                .set("height", "1px")
                .set("background-color", "#ddd")
                .set("width", "100%")
                .set("margin", "8px 0");
        add(separator);
    }

}