package com.example.demo.views;

import java.util.Set;

import com.example.demo.services.iAdministrador;
import com.example.demo.tables.Comentario;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;

@Route("VerComentariosdeAdministrador")

public class VerComentariosdeAdministrador extends VerComentarios {
    iAdministrador _iAdministrador;

   

    public VerComentariosdeAdministrador(iAdministrador iAdministrador, Set<Comentario> comentarios) {
        super(comentarios);
        this._iAdministrador = iAdministrador;
        removeAll();
        if (comentarios.isEmpty()) {
           
            Div noComments = new Div();
            noComments.setText("No hay comentarios disponibles.");
            add(noComments);
        } else {
            for (Comentario comentario : comentarios) {
                VerComentariosdeAdministrador_item comentarioItem = new VerComentariosdeAdministrador_item(
                        _iAdministrador, comentario);
                comentarioItem.eliminarButton.addClickListener(e -> UI.getCurrent().getPage().reload());
                add(comentarioItem);
            }
        }
    }

}