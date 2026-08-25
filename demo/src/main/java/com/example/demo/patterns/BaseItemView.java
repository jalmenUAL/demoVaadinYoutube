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
/**
 * Clase base para las vistas que representan un elemento de un modelo.
 *
 * <p>
 * Permite crear vistas genéricas para distintos tipos de objetos.
 *
 * <p>
 * Por ejemplo:
 *
 * <pre>
 *     BaseItemView&lt;Video&gt;
 * </pre>
 *
 * representa un elemento cuyo modelo es un {@code Video}.
 *
 * <p>
 * Del mismo modo:
 *
 * <pre>
 *     BaseItemView&lt;Youtuber&gt;
 * </pre>
 *
 * representa un elemento cuyo modelo es un {@code Youtuber}.
 *
 * @param <T> tipo de objeto que representa el elemento
 */
public abstract class BaseItemView<T> extends BaseView {


    /**
     * Modelo representado por esta vista.
     *
     * <p>
     * Las clases hijas pueden utilizarlo durante las fases
     * {@link #build()} y {@link #bindEvents()}.
     *
     * <p>
     * Es {@code final} porque la vista representa al mismo
     * elemento durante todo su ciclo de vida.
     */
    protected final T model;


    /**
     * Crea una vista para el modelo indicado.
     *
     * @param model objeto que representa los datos del elemento
     */
    protected BaseItemView(T model) {

        this.model = model;
    }
}