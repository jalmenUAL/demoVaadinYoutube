package com.example.demo.views;

import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.patterns.BaseActorView;
import com.example.demo.services.iInicio;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("Inicio")
@AnonymousAllowed

/**
 * Vista base de la página de inicio de la aplicación.
 *
 * <p>
 * Es abstracta porque la forma concreta de mostrar los últimos
 * vídeos puede depender del tipo de usuario.
 *
 * <p>
 * Hereda de BaseActorView, por lo que dispone de:
 *
 *     - header → barra superior.
 *     - body   → contenido principal.
 *
 * <p>
 * Las clases hijas son las responsables de implementar
 * UltimosVideos().
 */
public abstract class Inicio extends BaseActorView {


    /**
     * Interfaz de servicios utilizada para realizar operaciones
     * relacionadas con la página de inicio.
     *
     * <p>
     * Por ejemplo, realizar búsquedas.
     */
    protected final iInicio iInicio;


    /**
     * Componente de búsqueda que aparece en la cabecera.
     */
    protected Buscar _buscar;


    /**
     * Componente que muestra los últimos vídeos.
     *
     * <p>
     * La instancia concreta será creada por la implementación
     * de UltimosVideos() de la clase hija.
     */
    protected UltimosVideos _ultimosVideos;


    /**
     * Proveedor de factorías utilizado por las vistas que necesitan
     * crear o navegar hacia otras vistas.
     */
    protected ViewFactoryProvider viewFactory;


    /**
     * Constructor.
     */
    public Inicio(
            iInicio iInicio,
            ViewFactoryProvider viewFactory) {

        super();

        this.iInicio = iInicio;
        this.viewFactory = viewFactory;
    }


    /**
     * Construye los elementos comunes de la página de inicio.
     *
     * <p>
     * Las clases hijas pueden reutilizar esta construcción mediante
     * super.build() y añadir posteriormente sus propios componentes.
     */
    @Override
    protected void build() {


        // =========================
        // LOGOTIPO
        // =========================

        /*
         * Se crea un logotipo sencillo mediante un Div.
         *
         * No se utiliza una imagen externa: el texto y los estilos
         * se generan directamente con Vaadin.
         */
        Div youtubeLogo =
                new Div();

        youtubeLogo.setText("YouTube");

        youtubeLogo.getStyle()
                .set("background-color", "#FF0000")
                .set("color", "white")
                .set("font-weight", "bold")
                .set("font-size", "1.8em")
                .set("padding", "10px 22px")
                .set("border-radius", "8px");


        // =========================
        // CABECERA
        // =========================

        header.setAlignItems(
                Alignment.CENTER);

        header.setSpacing(true);

        header.add(youtubeLogo);


        // =========================
        // BÚSQUEDA
        // =========================

        /*
         * La vista Buscar recibe el servicio iInicio y el proveedor
         * de factorías mediante inyección de dependencias.
         *
         * Buscar no realiza directamente la navegación: comunica
         * los resultados mediante un Consumer.
         */
        _buscar =
                new Buscar(
                        iInicio,
                        viewFactory);

        header.add(_buscar);

        
    }


    /**
     * Registra los eventos de la vista.
     */
    @Override
    protected void bindEvents() {


        /*
         * Buscar permite registrar una función que se ejecutará
         * cuando haya resultados de búsqueda.
         *
         * Aquí utilizamos una lambda como Consumer<List<Video>>.
         */
        _buscar.setOnResultado(resultados -> {


            // =========================
            // LIMPIAR CONTENIDO
            // =========================

            /*
             * Antes de mostrar los nuevos resultados eliminamos
             * el contenido que hubiera anteriormente en body.
             */
            body.removeAll();


            // =========================
            // SIN RESULTADOS
            // =========================

            if (resultados.isEmpty()) {

                Div noResultsDiv =
                        new Div();

                noResultsDiv.getStyle()
                        .set("font-size", "1.7em")
                        .set("color", "#555")
                        .set("padding", "20px")
                        .set("text-align", "center");

                noResultsDiv.setText(
                        "No se encontraron resultados.");

                body.add(noResultsDiv);

                /*
                 * No continuamos creando la vista de resultados.
                 */
                return;
            }


            // =========================
            // MOSTRAR RESULTADOS
            // =========================

            /*
             * Crear una vista específica para representar
             * los resultados encontrados.
             */
            ResultadodeBusqueda vista =
                    new ResultadodeBusqueda(
                            resultados,
                            viewFactory);


            body.add(vista);
        });
         
    }


     
    /**
     * Carga y muestra los últimos vídeos.
     *
     * <p>
     * Cada tipo de usuario puede implementar este método de forma
     * diferente.
     */
    protected abstract void UltimosVideos();
}