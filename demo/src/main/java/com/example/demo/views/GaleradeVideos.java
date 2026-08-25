package com.example.demo.views;

import java.util.List;
import java.util.Vector;

import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.patterns.BaseListView;
import com.example.demo.tables.Video;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;

@Route("GaleriadeVideos")
/**
 * Vista que muestra una colección de vídeos en forma de galería.
 *
 * <p>
 * Hereda de BaseListView<Video>, por lo que recibe una colección
 * de objetos Video y se encarga de crear un componente visual
 * GaleradeVideos_item para cada uno de ellos.
 *
 * <p>
 * La clase padre separa la construcción de la lista en dos partes:
 *
 *     1. buildContainer() → crea la estructura de la galería.
 *     2. buildItems()     → crea los elementos de la colección.
 */
public class GaleradeVideos
        extends BaseListView<Video> {


    /**
     * Colección de componentes visuales correspondientes
     * a los vídeos mostrados.
     *
     * <p>
     * No es estrictamente necesaria para mostrar los componentes,
     * pero puede resultar útil si posteriormente queremos acceder
     * individualmente a los elementos creados.
     */
    public Vector<GaleradeVideos_item> _item =
            new Vector<>();


    /**
     * Contenedor horizontal donde se colocan los vídeos.
     */
    protected HorizontalLayout carrusel;


    /**
     * Título de la galería.
     */
    protected H2 tituloGaleria;


    /**
     * Proveedor de factorías.
     *
     * <p>
     * Se pasa a cada GaleradeVideos_item para que estos puedan
     * navegar a la implementación de VerVideo correspondiente
     * al usuario actual.
     */
    protected ViewFactoryProvider viewFactory;


    /**
     * Constructor.
     *
     * @param videos colección de vídeos que se mostrarán
     * @param factory proveedor de factorías de vistas
     */
    public GaleradeVideos(
            List<Video> videos,
            ViewFactoryProvider factory) {

        /*
         * BaseListView guarda la colección recibida en "elements".
         */
        super(videos);

        this.viewFactory = factory;


        /*
         * Inicializa la vista:
         *
         *     build()
         *        ↓
         *     BaseListView.build()
         *        ↓
         *     buildContainer()
         *        ↓
         *     buildItems()
         *        ↓
         *     bindEvents()
         */
        initView();
    }


    /**
     * Construye la vista completa.
     */
    @Override
    protected void build() {

        /*
         * Llamar a super.build() es importante.
         *
         * BaseListView.build() se encarga de ejecutar:
         *
         *     buildContainer()
         *     buildItems()
         */
        super.build();


        /*
         * El título también podría establecerse directamente
         * en buildContainer(). Aquí simplemente se modifica
         * después de construir el contenedor.
         */
        tituloGaleria.setText(
                "Galería de Videos");
    }


    /**
     * Esta vista no necesita eventos propios.
     *
     * <p>
     * Los eventos pertenecen a cada GaleradeVideos_item,
     * concretamente al click sobre su miniatura.
     */
    @Override
    protected void bindEvents() {
        // No hay eventos propios de la galería.
    }


    /**
     * Construye la estructura visual de la galería.
     *
     * <p>
     * Aquí no se crean todavía los vídeos. Únicamente se crean
     * los componentes que actuarán como contenedores.
     */
    @Override
    protected void buildContainer() {

        setSizeFull();

        setAlignItems(
                Alignment.CENTER);

        setJustifyContentMode(
                JustifyContentMode.START);


        // =========================
        // TÍTULO
        // =========================

        tituloGaleria =
                new H2("Galería de Videos");

        tituloGaleria.getStyle()
                .set("color", "#2c3e50")
                .set("margin-top", "20px")
                .set("margin-bottom", "10px");


        // =========================
        // CARRUSEL
        // =========================

        /*
         * HorizontalLayout permite colocar los elementos
         * uno al lado del otro.
         */
        carrusel =
                new HorizontalLayout();

        carrusel.setSpacing(true);
        carrusel.setPadding(true);
        carrusel.setWidthFull();

        carrusel.setJustifyContentMode(
                FlexComponent
                        .JustifyContentMode.CENTER);


        /*
         * Añadir los componentes principales de la galería.
         */
        add(
                tituloGaleria,
                carrusel);
    }


    /**
     * Crea los elementos visuales correspondientes a cada vídeo.
     */
    @Override
    protected void buildItems() {

        /*
         * "elements" pertenece a BaseListView y contiene
         * los vídeos recibidos en el constructor.
         */
        for (Video video : elements) {

            /*
             * Crear el componente visual que representa
             * este vídeo.
             */
            GaleradeVideos_item item =
                    new GaleradeVideos_item(
                            video,
                            viewFactory);


            /*
             * Guardar una referencia al componente.
             *
             * Esto solo es necesario si posteriormente queremos
             * acceder a los elementos desde esta clase.
             */
            _item.add(item);


            /*
             * Añadir el mismo componente al carrusel.
             */
            carrusel.add(item);
        }
    }
}