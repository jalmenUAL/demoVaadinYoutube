package com.example.demo.views;

import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.services.iRegistrado;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.VaadinSession;

@Route("Registrado")
public abstract class Registrado extends Inicio {

    /*
     * Clase base para las vistas a las que solamente pueden acceder
     * usuarios que han iniciado sesión.
     *
     * Registrado hereda de Inicio:
     *
     *      BaseActorView
     *           ↓
     *         Inicio
     *           ↓
     *       Registrado
     *           ↓
     *    Youtuber / Administrador
     *
     * De esta forma, los usuarios registrados reutilizan toda la
     * interfaz común de Inicio y además incorporan el botón
     * "Cerrar sesión".
     */

    protected final iRegistrado iRegistrado;

    protected Button logoutButton;

    public Registrado(
            iRegistrado iRegistrado,
            ViewFactoryProvider viewFactory) {

        /*
         * El constructor de Inicio se encarga de recibir la interfaz
         * de servicios y la factoría que utilizarán las vistas.
         */
        super(iRegistrado, viewFactory);

        this.iRegistrado = iRegistrado;
    }

    /*
     * Cierra la sesión del usuario actual.
     *
     * Es importante distinguir entre:
     *
     *  - La sesión de Vaadin.
     *  - El SecurityContext de Spring Security.
     *
     * Al cerrar sesión debemos limpiar correctamente ambos.
     */
    protected void Logout() {

        /*
         * SecurityContextLogoutHandler se encarga de realizar el
         * logout de Spring Security.
         *
         * Obtenemos la HttpServletRequest a partir de la petición
         * actual de Vaadin.
         */
        new SecurityContextLogoutHandler().logout(
                VaadinServletRequest.getCurrent()
                        .getHttpServletRequest(),
                null,
                null);

        /*
         * Cerramos también la sesión de Vaadin.
         */
        VaadinSession.getCurrent().close();

        /*
         * Después de cerrar sesión enviamos al usuario a la vista
         * pública de inicio.
         */
        getUI().ifPresent(
                ui -> ui.navigate("NoLogueado"));
    }

    /*
     * Construcción de la parte común de las vistas de usuarios
     * registrados.
     */
    @Override
    protected void build() {

        /*
         * Primero construimos todo lo que proporciona Inicio:
         *
         *  - Logo
         *  - Buscador
         *  - etc.
         */
        super.build();

        /*
         * Añadimos a continuación el botón específico de los usuarios
         * registrados.
         */
        logoutButton = new Button(
                "Cerrar sesión",
                new Icon(VaadinIcon.SIGN_OUT));

        logoutButton.addThemeVariants(
                ButtonVariant.LUMO_ERROR);

        /*
         * margin-left: auto hace que el botón se coloque a la derecha
         * del header.
         */
        logoutButton.getStyle()
                .set("margin-left", "auto")
                .set("font-weight", "bold");

        header.add(logoutButton);
    }

    /*
     * Asociamos los eventos de los componentes.
     */
    @Override
    protected void bindEvents() {

        /*
         * Muy importante llamar a super.bindEvents().
         *
         * Inicio también tiene eventos propios, concretamente el
         * buscador.
         *
         * Si no llamáramos a super.bindEvents(), esos eventos dejarían
         * de estar registrados en las clases hijas.
         */
        super.bindEvents();

        logoutButton.addClickListener(
                e -> Logout());
    }
}