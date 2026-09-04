package com.example.demo.patterns;


import com.example.demo.factories.ViewFactoryProvider;

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
 *     BaseItemView&lt;Video, iVideoService&gt;
 * </pre>
 *
 * representa un elemento cuyo modelo es un {@code Video} y utiliza
 * el servicio {@code iVideoService}.
 *
 * @param <T> tipo de objeto del modelo de datos (perteneciente a 'tables')
 * @param <S> tipo de la interfaz del servicio asociado a la vista
 */
public abstract class BaseItemView<T, S> extends BaseView<S> {

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
     * <p>
     * Invocación delegada a {@code super(viewFactory, servicio)} para asegurar
     * la consistencia arquitectónica de dependencias.
     *
     * @param viewFactory proveedor de factorías de la aplicación
     * @param servicio    interfaz del servicio de negocio
     * @param model       objeto que representa los datos del elemento
     */
    protected BaseItemView(ViewFactoryProvider viewFactory, S servicio, T model) {
        super(viewFactory, servicio);

        if (model == null) {
            throw new IllegalArgumentException("El modelo de datos T no puede ser nulo en BaseItemView.");
        }

        this.model = model;
    }
}