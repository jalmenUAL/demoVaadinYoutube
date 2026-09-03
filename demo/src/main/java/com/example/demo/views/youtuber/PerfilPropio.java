package com.example.demo.views.youtuber;

import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.services.interfaces.iYoutuber;
import com.example.demo.views.inicio.Perfil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@Route("PerfilPropio")
@RolesAllowed("ROLE_YOUTUBER")
public class PerfilPropio extends Perfil {

    /*
     * Esta vista representa el perfil del propio usuario autenticado.
     *
     * Hereda de Perfil, por lo que reutiliza:
     *
     * - La estructura general del perfil.
     * - El banner.
     * - El avatar.
     * - Los vídeos publicados.
     * - Los vídeos que le gustan.
     * - La lista de Youtubers seguidos.
     *
     * Esta clase únicamente añade las acciones que tiene sentido
     * mostrar en el perfil propio:
     *
     * - Publicar un vídeo.
     * - Modificar la configuración.
     */

    private final iYoutuber iYoutuber;

    private Button publicarButton;
    private Button configButton;
    Configuracion _configuracion;
    PublicarVideo _publicarVideo;

    public PerfilPropio(
            iYoutuber iYoutuber,
            ViewFactoryProvider viewFactory) {

        /*
         * Pasamos el servicio y la factoría a la clase padre.
         *
         * Perfil se encargará de construir toda la estructura común
         * del perfil.
         */
        super(iYoutuber, viewFactory);

        this.iYoutuber = iYoutuber;
    }

    /*
     * Añadimos al perfil los botones que solamente tiene sentido
     * mostrar cuando estamos viendo nuestro propio perfil.
     */
    @Override
    protected void build(String parameter) {

        /*
         * Primero construimos todo lo que ya proporciona Perfil.
         *
         * Es importante llamar a super.build(parameter), porque ahí
         * se crea topLayout y se carga _usuario utilizando el parámetro
         * recibido en la URL.
         */
        super.build(parameter);

        // -------------------------------------------------
        // Botón publicar vídeo
        // -------------------------------------------------

        publicarButton =
                new Button("📤 Publicar video");

        publicarButton.getStyle()
                .set("background-color", "#0d6efd")
                .set("color", "white")
                .set("border-radius", "8px")
                .set("font-weight", "bold");

        // -------------------------------------------------
        // Botón configuración
        // -------------------------------------------------

        configButton =
                new Button("⚙️ Configuración");

        configButton.getStyle()
                .set("background-color", "#6c757d")
                .set("color", "white")
                .set("border-radius", "8px")
                .set("font-weight", "bold");

        // -------------------------------------------------
        // Layout de botones
        // -------------------------------------------------

        HorizontalLayout botonesHeader =
                new HorizontalLayout(
                        publicarButton,
                        configButton);

        botonesHeader.setWidthFull();

        /*
         * Colocamos los botones a la derecha del encabezado.
         */
        botonesHeader.setJustifyContentMode(
                JustifyContentMode.END);

        botonesHeader.setPadding(true);

        /*
         * topLayout pertenece a Perfil.
         *
         * Como es protected, las clases hijas pueden reutilizarlo
         * directamente.
         */
        topLayout.add(botonesHeader);
    }

    /*
     * Registramos los eventos propios de esta vista.
     *
     * También llamamos a super.bindEvents() para conservar los eventos
     * definidos en Perfil.
     */
    @Override
    protected void bindEvents() {

        super.bindEvents();

        publicarButton.addClickListener(
                e -> PublicarVideo());

        configButton.addClickListener(
                e -> Configuracion());
    }

    /*
     * Navega a la vista encargada de publicar vídeos.
     */
    public void PublicarVideo() {

        UI.getCurrent().navigate(
                PublicarVideo.class);
    }

    /*
     * Navega a la vista de configuración de la cuenta.
     */
    public void Configuracion() {

        UI.getCurrent().navigate(
                Configuracion.class);
    }
}