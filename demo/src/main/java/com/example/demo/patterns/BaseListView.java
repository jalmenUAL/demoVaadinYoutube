package com.example.demo.patterns;

import java.util.Collection;

import com.example.demo.factories.ViewFactoryProvider;

 

/**
 * Clase base para las vistas que muestran una colección de elementos.
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
 * @param <T> tipo de elemento del modelo que contiene la lista (perteneciente a 'tables')
 * @param <S> tipo de la interfaz del servicio asociado a la vista
 */
public abstract class BaseListView<T, S> extends BaseView<S> {

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
     * Crea una vista de lista con sus dependencias y colección de datos.
     *
     * @param viewFactory proveedor de factorías de la aplicación
     * @param servicio    interfaz del servicio de negocio
     * @param elements    colección de elementos que debe mostrar la vista
     */
    protected BaseListView(ViewFactoryProvider viewFactory, S servicio, Collection<T> elements) {
        super(viewFactory, servicio);

        if (elements == null) {
            throw new IllegalArgumentException("La colección de elementos no puede ser nula en BaseListView.");
        }

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