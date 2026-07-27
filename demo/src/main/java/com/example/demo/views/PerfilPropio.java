package com.example.demo.views;

import com.example.demo.services.iYoutuber;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@Route("PerfilPropio")
@RolesAllowed("ROLE_YOUTUBER")
public class PerfilPropio extends Perfil {

    public com.example.demo.tables.Youtuber _youtuber;
    public PublicarVideo _publicarVideo;
    public Configuracion _configuracion;

    iYoutuber _iYoutuber;

    public PerfilPropio(iYoutuber iYoutuber) {
        super(iYoutuber);
        this._iYoutuber = iYoutuber;

        Button publicarButton = new Button("📤 Publicar video", event -> PublicarVideo());
        Button configButton = new Button("⚙️ Configuración", event -> Configuracion());

        publicarButton.getStyle()
                .set("background-color", "#0d6efd")
                .set("color", "white")
                .set("border-radius", "8px")
                .set("font-weight", "bold");

        configButton.getStyle()
                .set("background-color", "#6c757d")
                .set("color", "white")
                .set("border-radius", "8px")
                .set("font-weight", "bold");

        HorizontalLayout botonesHeader = new HorizontalLayout(publicarButton, configButton);
        botonesHeader.setWidthFull();
        botonesHeader.setJustifyContentMode(JustifyContentMode.END);
        botonesHeader.setPadding(true);

        topLayout.add(botonesHeader);
    }

    public void PublicarVideo() {
        UI.getCurrent().navigate(PublicarVideo.class);
    }

    public void Configuracion() {
        UI.getCurrent().navigate(Configuracion.class);
    }

}