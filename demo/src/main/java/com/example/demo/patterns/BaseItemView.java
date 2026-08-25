package com.example.demo.patterns;

/**
 * Clase base para las vistas que representan un único elemento
 * de un modelo de datos.
 *
 * <p>
 * Utiliza un tipo genérico T para poder trabajar con diferentes
 * tipos de objetos sin tener que crear una clase base diferente
 * para cada uno.
 *
 * <p>
 * Por ejemplo:
 *
 *     BaseItemView<Video>
 *
 * representa una vista cuyo modelo es un Video.
 *
 *     BaseItemView<Youtuber>
 *
 * representa una vista cuyo modelo es un Youtuber.
 *
 * <p>
 * Esta clase también hereda de BaseView, por lo que las vistas
 * que hereden de BaseItemView dispondrán automáticamente del
 * ciclo:
 *
 *     build()
 *        ↓
 *     bindEvents()
 */
public abstract class BaseItemView<T>
        extends BaseView {


    /**
     * Objeto que representa los datos que muestra esta vista.
     *
     * <p>
     * T es el tipo genérico de la clase.
     *
     * Si tenemos:
     *
     *     BaseItemView<Video>
     *
     * entonces model será un Video.
     *
     * Si tenemos:
     *
     *     BaseItemView<Youtuber>
     *
     * entonces model será un Youtuber.
     *
     * <p>
     * Es final porque una vez creada la vista no queremos
     * sustituir el objeto que representa.
     */
    protected final T model;


    /**
     * Constructor.
     *
     * @param model objeto que representa los datos del elemento
     *              que mostrará la vista.
     */
    public BaseItemView(T model) {

        /*
         * Guardamos el objeto recibido para que las clases hijas
         * puedan utilizarlo en build() y bindEvents().
         */
        this.model = model;
    }
}