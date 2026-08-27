package com.example.demo.views.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.patterns.BaseListView;
import com.example.demo.tables.Video;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;

@Route("ListadeVideos")
/**
 * Vista que muestra una colección de vídeos organizada en filas.
 *
 * <p>
 * Hereda de BaseListView<Video>, por lo que recibe una colección
 * de vídeos y se encarga de crear un ListadeVideos_item por cada
 * elemento.
 *
 * <p>
 * A diferencia de GaleradeVideos, que utiliza un único
 * HorizontalLayout como carrusel, esta vista distribuye los vídeos
 * en filas con dos elementos por fila.
 */
public class ListadeVideos
        extends BaseListView<Video> {


    /**
     * Guarda los componentes visuales creados para cada vídeo.
     *
     * <p>
     * No es necesario para mostrar los elementos, pero permite
     * conservar referencias a ellos por si posteriormente queremos
     * modificarlos o acceder a ellos.
     */
    public Vector<ListadeVideos_item> _item =
            new Vector<>();


    /**
     * Proveedor de factorías.
     *
     * <p>
     * Se pasa a cada ListadeVideos_item para que pueda utilizar
     * la factoría correspondiente al usuario actual.
     */
    protected ViewFactoryProvider viewFactory;


    /**
     * Constructor.
     *
     * @param videos colección de vídeos que se mostrarán
     * @param factory proveedor de factorías
     */
    public ListadeVideos(
            Set<Video> videos,
            ViewFactoryProvider factory) {

        /*
         * La clase padre guarda la colección recibida
         * en la variable "elements".
         */
        super(videos);

        this.viewFactory = factory;


        /*
         * Inicializa la vista:
         *
         *     build()
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
     * Configura el contenedor principal de la lista.
     *
     * <p>
     * En este caso no necesitamos crear un contenedor adicional:
     * la propia vista actúa como contenedor.
     */
    @Override
    protected void buildContainer() {

        setWidthFull();
        setSpacing(true);
    }


    /**
     * Esta lista no tiene eventos propios.
     *
     * <p>
     * Los eventos pertenecen a cada ListadeVideos_item.
     */
    @Override
    protected void bindEvents() {

        // La lista no tiene eventos propios.
    }


    /**
     * Construye los elementos de la lista.
     *
     * <p>
     * Los vídeos se distribuyen en filas de dos elementos.
     *
     * <p>
     * Ejemplo con 5 vídeos:
     *
     *     ┌────────────┬────────────┐
     *     │  Vídeo 1   │  Vídeo 2   │
     *     ├────────────┼────────────┤
     *     │  Vídeo 3   │  Vídeo 4   │
     *     ├────────────┼────────────┤
     *     │  Vídeo 5   │            │
     *     └────────────┴────────────┘
     */
    @Override
    protected void buildItems() {


        // Número máximo de vídeos por fila.
        int columnas = 2;


        /*
         * Índice del siguiente vídeo que debemos procesar.
         */
        int index = 0;


        /*
         * "elements" es una Collection<T> heredada de BaseListView.
         *
         * Como aquí necesitamos acceder a los elementos mediante
         * índices (get(index)), convertimos la colección en una lista.
         */
        List<Video> listaVideos =
                new ArrayList<>(elements);


        /*
         * Continuamos creando filas mientras queden vídeos.
         */
        while (index < listaVideos.size()) {


            // =========================
            // CREAR FILA
            // =========================

            HorizontalLayout fila =
                    new HorizontalLayout();

            fila.setWidthFull();
            fila.setSpacing(true);


            /*
             * Separar los elementos horizontalmente para aprovechar
             * el espacio disponible.
             */
            fila.getStyle()
                    .set(
                            "justify-content",
                            "space-between");


            // =========================
            // CREAR ELEMENTOS
            // =========================

            /*
             * Como queremos dos columnas, el bucle permite añadir
             * como máximo dos vídeos a cada fila.
             *
             * La segunda condición evita acceder a una posición
             * que no exista cuando la última fila tenga un solo vídeo.
             */
            for (
                    int c = 0;
                    c < columnas
                            && index < listaVideos.size();
                    c++) {


                Video video =
                        listaVideos.get(index);


                /*
                 * Crear el componente visual correspondiente
                 * al vídeo.
                 */
                ListadeVideos_item item =
                        new ListadeVideos_item(
                                video,
                                viewFactory);


                /*
                 * Cada elemento ocupa aproximadamente el 48 %
                 * de la anchura de la fila.
                 *
                 * El espacio restante permite mantener separación
                 * entre las dos columnas.
                 */
                item.setWidth("48%");


                /*
                 * Guardar una referencia al componente.
                 */
                _item.add(item);


                /*
                 * Añadir el componente a la fila actual.
                 */
                fila.add(item);


                /*
                 * Pasar al siguiente vídeo.
                 */
                index++;
            }


            /*
             * Una vez completada la fila, la añadimos a la vista.
             */
            add(fila);
        }
    }
}