package com.example.demo.views.nologueado;

import java.util.List;
import java.util.Set;

import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.services.interfaces.iNoLogueado;
import com.example.demo.tables.Video;
import com.example.demo.views.inicio.Inicio;
import com.example.demo.views.inicio.UltimosVideos;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("NoLogueado")
@AnonymousAllowed

public class NoLogueado extends Inicio {

    /*
     * Servicio que contiene las operaciones disponibles para un
     * usuario que todavía no ha iniciado sesión.
     *
     * Al heredar de Inicio, esta clase también dispone de las
     * operaciones generales de iInicio.
     */
    private final iNoLogueado iNoLogueado;

    /*
     * Botones específicos de esta vista.
     *
     * Un usuario no autenticado puede:
     * - iniciar sesión
     * - registrarse
     */
    private Button loginButton;
    private Button registrarButton;
    Registrar _registrar;
    Login _login;

    public NoLogueado(
            iNoLogueado iNoLogueado,
            ViewFactoryProvider viewFactory) {

        /*
         * El constructor de Inicio recibe el servicio iInicio y
         * la factoría de vistas.
         *
         * Como iNoLogueado hereda de iInicio, podemos pasarlo
         * directamente al constructor de la clase padre.
         */
        super(iNoLogueado, viewFactory);

        this.iNoLogueado = iNoLogueado;

        /*
         * Inicializamos la vista una vez que todos sus atributos
         * necesarios han sido inicializados.
         */
        initView();
    }

    /*
     * ============================================================
     * CONSTRUCCIÓN DE LA VISTA
     * ============================================================
     */
    @Override
    protected void build() {

        /*
         * MUY IMPORTANTE:
         *
         * Inicio ya construye:
         * - el logo
         * - el buscador
         * - el header
         *
         * Por eso primero llamamos a super.build().
         *
         * Después añadimos únicamente los componentes propios
         * de NoLogueado.
         */
        super.build();

        /*
         * Botón para acceder a la pantalla de Login.
         */
        loginButton = new Button(
                "Login",
                new Icon(VaadinIcon.SIGN_IN));

        loginButton.addThemeVariants(
                ButtonVariant.LUMO_PRIMARY);

        /*
         * Botón para acceder al registro.
         */
        registrarButton = new Button(
                "Registrar",
                new Icon(VaadinIcon.USER_CARD));

        registrarButton.addThemeVariants(
                ButtonVariant.LUMO_SUCCESS);

        /*
         * Agrupamos los dos botones horizontalmente.
         */
        HorizontalLayout botones =
                new HorizontalLayout(
                        loginButton,
                        registrarButton);

        /*
         * El header ya fue creado por BaseActorView
         * y configurado por Inicio.
         *
         * Aquí simplemente añadimos nuestros botones.
         */
        header.add(botones);
    }

    /*
     * ============================================================
     * EVENTOS
     * ============================================================
     */
    @Override
    protected void bindEvents() {

        /*
         * Inicio también tiene eventos propios:
         *
         * - evento del botón Buscar
         *
         * Por eso debemos llamar a super.bindEvents().
         *
         * Si no lo hiciéramos, el buscador que hemos heredado
         * dejaría de funcionar.
         */
        super.bindEvents();

        /*
         * Al pulsar Login navegamos a la vista de autenticación.
         */
        loginButton.addClickListener(
                e -> Login());

        /*
         * Al pulsar Registrar navegamos a la vista de registro.
         */
        registrarButton.addClickListener(
                e -> Registrar());
    }

    /*
     * ============================================================
     * CONTENIDO ESPECÍFICO DEL INICIO
     * ============================================================
     *
     * Inicio declara UltimosVideos() como abstracto porque cada
     * tipo de usuario puede decidir cómo obtener o mostrar los
     * vídeos iniciales.
     */
    @Override
    protected void UltimosVideos() {

        /*
         * iNoLogueado hereda de iInicio, por lo que tiene acceso
         * a getUltimosVideos().
         */
       List<Video> videos =
                iNoLogueado.getUltimosVideos();

        /*
         * Creamos el componente que representa la lista de
         * últimos vídeos.
         *
         * La factoría se pasa para que los elementos sepan qué
         * tipo de vista deben abrir al pulsar sobre ellos.
         */
        _ultimosVideos =
                new UltimosVideos(
                        videos,
                        viewFactory);

        body.add(_ultimosVideos);
    }

    /*
     * ============================================================
     * NAVEGACIÓN
     * ============================================================
     */

    /*
     * Navegar al Login.
     */
    private void Login() {

        UI.getCurrent()
                .navigate(Login.class);
    }

    /*
     * Navegar al Registro.
     */
    private void Registrar() {

        UI.getCurrent()
                .navigate(Registrar.class);
    }

    @Override
    public com.example.demo.services.interfaces.iInicio getServicio() {
        return iNoLogueado;
    }

    @Override
    public ViewFactoryProvider getViewFactory() {
       return viewFactory;
    }
}