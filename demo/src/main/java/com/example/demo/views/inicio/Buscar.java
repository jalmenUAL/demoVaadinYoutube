package com.example.demo.views.inicio;

import java.util.List;
import java.util.function.Consumer;

import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.patterns.BaseView;
import com.example.demo.services.interfaces.iInicio;
import com.example.demo.tables.Video;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Componente visual que permite buscar vídeos por su título.
 *
 * <p>
 * Esta clase no es una vista completa de navegación, sino un
 * componente reutilizable que puede colocarse dentro de otras vistas.
 *
 * <p>
 * Utiliza iInicio porque la búsqueda es una operación disponible
 * incluso para usuarios que no han iniciado sesión.
 */

public class Buscar extends BaseView {

    /**
     * Interfaz que proporciona las operaciones generales de inicio.
     *
     * <p>
     * Se utiliza para realizar la búsqueda sin acceder directamente
     * al repositorio.
     */
    private final iInicio _iInicio;
    ResultadodeBusqueda _resultadodeBusqueda;

    /**
     * Proveedor de factorías de vistas.
     *
     * <p>
     * Se recibe para mantener el mismo mecanismo de navegación
     * utilizado por el resto de componentes.
     */
    private final ViewFactoryProvider viewFactory;

    /**
     * Campo de texto donde el usuario introduce el texto de búsqueda.
     */
    private TextField textoBuscar;

    /**
     * Botón que inicia la búsqueda.
     */
    public Button botonBuscar;

    /**
     * Función que recibirá los resultados de la búsqueda.
     *
     * <p>
     * Consumer<List<Video>> significa:
     *
     * "una función que recibe una lista de vídeos y no devuelve
     * ningún resultado".
     *
     * <p>
     * Esto permite que Buscar no tenga que saber qué hace la vista
     * con los resultados.
     */
    private Consumer<List<Video>> onResultado;

    /**
     * Constructor del componente.
     *
     * @param iInicio     servicio utilizado para realizar las búsquedas
     * @param viewFactory proveedor de factorías de vistas
     */
    public Buscar(
            iInicio iInicio,
            ViewFactoryProvider viewFactory) {

        super();

        this._iInicio = iInicio;
        this.viewFactory = viewFactory;

        /*
         * Inicializa la vista:
         *
         * build()
         * ↓
         * bindEvents()
         */
        initView();
    }

    /**
     * Establece qué debe hacerse cuando termina una búsqueda.
     *
     * <p>
     * La clase Buscar solamente se encarga de obtener los resultados.
     * La clase que utiliza Buscar decide qué hacer con ellos.
     *
     * <p>
     * Por ejemplo, otra vista podría recibir los resultados y
     * actualizar una lista de vídeos.
     */
    public void setOnResultado(
            Consumer<List<Video>> onResultado) {

        this.onResultado = onResultado;
    }

    /**
     * Construye los componentes visuales de la búsqueda.
     */
    @Override
    protected void build() {

        setWidthFull();

        /*
         * Campo donde se introduce el texto que se quiere buscar.
         */
        textoBuscar = new TextField();

        textoBuscar.setPlaceholder(
                "Introduzca el nombre del vídeo que quiere buscar");

        textoBuscar.setWidthFull();

        /*
         * Botón que ejecutará la búsqueda.
         */
        botonBuscar = new Button("Buscar");

        botonBuscar.addThemeVariants(
                ButtonVariant.LUMO_PRIMARY);

        /*
         * Agrupamos el campo de búsqueda y el botón
         * horizontalmente.
         */
        HorizontalLayout buscarLayout = new HorizontalLayout(
                textoBuscar,
                botonBuscar);

        buscarLayout.setWidthFull();

        /*
         * El campo de texto ocupa el espacio disponible.
         *
         * El botón mantiene el espacio necesario para mostrar
         * su contenido.
         */
        buscarLayout.setFlexGrow(
                1,
                textoBuscar);

        add(buscarLayout);
    }

    /**
     * Registra los eventos de la interfaz.
     */
    @Override
    protected void bindEvents() {

        /*
         * Cuando se pulsa el botón se ejecuta Buscar().
         */
        botonBuscar.addClickListener(
                e -> {/*
                       * Obtener el texto introducido por el usuario.
                       */
                    List<Video> resultados = _iInicio.buscar(textoBuscar.getValue());

                    /*
                     * Comprobar si existe un consumidor de resultados.
                     *
                     * Es importante comprobarlo porque no es obligatorio que
                     * otra clase haya llamado previamente a setOnResultado().
                     */
                    if (onResultado != null) {

                        /*
                         * Ejecutar el Consumer pasando la lista de resultados.
                         */
                        onResultado.accept(resultados);
                    }
                });
    }

}