package com.example.demo.views;

import com.example.demo.factories.ViewFactory;
import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.services.iAdministrador;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.Route;

@Route("VerVideodeAdministrador")

public class VerVideodeAdministrador extends VerVideo {

    iAdministrador iAdministrador;

    public VerVideodeAdministrador(iAdministrador iAdministrador, ViewFactoryProvider viewFactory) {

        super(iAdministrador, viewFactory);
        this.iAdministrador = iAdministrador;

    }

    public void borrar() {

        iAdministrador.borrarVideo(video.getId());
        UI.getCurrent().getPage().getHistory().back();

    }

    public void setParameter(BeforeEvent event, Integer parameter) {
        super.setParameter(event, parameter);

        Button borrarButton = new Button("🗑️ Borrar video", event2 -> borrar());

        borrarButton.getStyle()
                .set("background-color", "#dc3545") // rojo
                .set("color", "white")
                .set("border-radius", "8px")
                .set("padding", "10px 20px")
                .set("font-weight", "bold");

        frame_y_comentarios.add(borrarButton);

    }

    @Override
    public void VerComentarios() {

        _verComentarios = new VerComentariosdeAdministrador( iAdministrador, video.getTiene_comentarios(), video.getId(), viewFactory   );
        comentarios.add(_verComentarios);

    }

}