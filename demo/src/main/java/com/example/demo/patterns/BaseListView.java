package com.example.demo.patterns;

import java.util.Collection;

 /**
 * Clase base para las vistas que muestran una colección de elementos.
 *
 * <p>
 * Utiliza un tipo genérico T para indicar qué tipo de objetos
 * contiene la lista.
 *
 * <p>
 * Por ejemplo:
 *
 *     BaseListView<Video>
 *
 * representa una vista que muestra una colección de vídeos.
 *
 *     BaseListView<Youtuber>
 *
 * representa una vista que muestra una colección de Youtubers.
 *
 * <p>
 * Esta clase hereda de BaseView, por lo que también utiliza
 * el ciclo de inicialización:
 *
 *     initView()
 *        ↓
 *     build()
 *        ↓
 *     bindEvents()
 *
 * <p>
 * Además, build() establece un orden común para construir
 * cualquier lista:
 *
 *     1. Construir el contenedor.
 *     2. Construir los elementos.
 */
public abstract class BaseListView<T>
        extends BaseView {


    /**
     * Colección de elementos que mostrará la lista.
     *
     * <p>
     * T representa el tipo de elemento.
     *
     * Por ejemplo:
     *
     *     Collection<Video>
     *
     * o:
     *
     *     Collection<Youtuber>
     *
     * <p>
     * Se utiliza Collection en lugar de una implementación concreta
     * como List porque la vista solamente necesita recorrer
     * los elementos y no depende de cómo se almacenen.
     */
    protected final Collection<T> elements;


    /**
     * Constructor.
     *
     * @param elements colección de elementos que debe mostrar
     *                 la vista.
     */
    public BaseListView(Collection<T> elements) {

        /*
         * Guardamos la colección para que las clases hijas
         * puedan utilizarla al construir los elementos.
         */
        this.elements = elements;
    }


    /**
     * Construye la vista.
     *
     * <p>
     * Este método sobrescribe el build() de BaseView.
     *
     * <p>
     * La clase base establece el orden:
     *
     *     buildContainer()
     *          ↓
     *     buildItems()
     *
     * Las clases hijas solamente tienen que indicar
     * cómo realizar cada una de esas operaciones.
     */
    @Override
    protected void build() {

        /*
         * Primero se crea el contenedor de la lista.
         *
         * Por ejemplo, un VerticalLayout, HorizontalLayout,
         * Grid, etc.
         */
        buildContainer();


        /*
         * Una vez creado el contenedor, se añaden
         * los elementos de la colección.
         */
        buildItems();
    }


    /**
     * Construye el contenedor donde se mostrarán los elementos.
     *
     * <p>
     * Cada tipo de lista puede necesitar un contenedor diferente.
     *
     * Por eso este método es abstracto.
     */
    protected abstract void buildContainer();


    /**
     * Construye y añade los elementos de la lista.
     *
     * <p>
     * La clase hija decide cómo representar cada objeto de T.
     *
     * Por ejemplo, una lista de vídeos podría crear
     * un Videos_item para cada Video.
     */
    protected abstract void buildItems();
}