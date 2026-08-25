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
/**
 * Clase base para las vistas que muestran una colección
 * de elementos.
 *
 * <p>
 * La clase define el ciclo de construcción de una lista:
 *
 * <pre>
 *     build()
 *        ↓
 *     buildContainer()
 *        ↓
 *     buildItems()
 * </pre>
 *
 * <p>
 * La clase hija solamente debe definir cómo se construye
 * el contenedor y cómo se representan los elementos.
 *
 * @param <T> tipo de elemento que contiene la lista
 */
public abstract class BaseListView<T>
        extends BaseView {


    /**
     * Elementos que mostrará la lista.
     *
     * <p>
     * Se utiliza {@link Collection} porque la vista solamente
     * necesita recorrer los elementos y no depende de una
     * implementación concreta como {@link java.util.List}
     * o {@link java.util.Set}.
     */
    protected final Collection<T> elements;


    /**
     * Crea una vista de lista.
     *
     * @param elements colección de elementos que debe mostrar
     *                 la vista
     */
    protected BaseListView(Collection<T> elements) {

        this.elements = elements;
    }


    /**
     * Construye la lista siguiendo siempre el mismo orden.
     *
     * <p>
     * El método es {@code final} para impedir que una clase hija
     * altere el orden de construcción.
     *
     * <pre>
     *     buildContainer()
     *          ↓
     *     buildItems()
     * </pre>
     */
    @Override
    protected final void build() {

        // ---------------------------------------------------------
        // CONSTRUIR CONTENEDOR
        // ---------------------------------------------------------

        /*
         * La clase hija decide qué componente utilizar
         * como contenedor de la lista.
         */
        buildContainer();


        // ---------------------------------------------------------
        // CONSTRUIR ELEMENTOS
        // ---------------------------------------------------------

        /*
         * Una vez creado el contenedor, la clase hija
         * construye y añade los elementos.
         */
        buildItems();
    }


    /**
     * Construye el contenedor donde se mostrarán los elementos.
     *
     * <p>
     * Puede ser, por ejemplo, un VerticalLayout,
     * HorizontalLayout, FlexLayout, Grid, etc.
     */
    protected abstract void buildContainer();


    /**
     * Construye y añade los elementos de la colección.
     *
     * <p>
     * La clase hija decide cómo representar cada elemento.
     */
    protected abstract void buildItems();
}