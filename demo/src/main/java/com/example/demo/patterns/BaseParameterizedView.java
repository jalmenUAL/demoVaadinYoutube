package com.example.demo.patterns;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;

/**
 * Clase base para las vistas que necesitan recibir un parámetro
 * desde la URL.
 *
 * <p>
 * Utiliza un tipo genérico T para indicar el tipo del parámetro.
 *
 * Por ejemplo:
 *
 *     BaseParameterizedView<Integer>
 *
 * significa que la vista recibe un Integer como parámetro.
 *
 * Esto es útil, por ejemplo, para mostrar un vídeo concreto:
 *
 *     /video/25
 *
 * donde 25 sería el parámetro recibido por la vista.
 *
 * <p>
 * Implementa HasUrlParameter<T>, una interfaz de Vaadin que permite
 * recibir un parámetro asociado a la navegación.
 */
public abstract class BaseParameterizedView<T>
        extends VerticalLayout
        implements HasUrlParameter<T> {


    /**
     * Método de Vaadin que se ejecuta cuando se navega hasta
     * esta vista proporcionando un parámetro en la URL.
     *
     * <p>
     * No construimos directamente la vista aquí.
     * Delegamos la construcción en initView(parameter).
     */
    @Override
    public void setParameter(
            BeforeEvent event,
            T parameter) {

        initView(parameter);
    }


    /**
     * Inicializa la vista utilizando el parámetro recibido.
     *
     * <p>
     * El orden de construcción es:
     *
     *     1. Construir la vista utilizando el parámetro.
     *     2. Asociar los eventos.
     *
     * <p>
     * Este funcionamiento sigue la misma idea del patrón
     * Template Method utilizado en BaseView.
     */
    public void initView(T parameter) {

        // Construir la interfaz utilizando el parámetro.
        build(parameter);

        // Asociar los eventos.
        bindEvents();
    }


    /**
     * Construye la interfaz utilizando el parámetro recibido.
     *
     * <p>
     * Cada clase hija decide qué hacer con dicho parámetro.
     */
    protected abstract void build(T parameter);


    /**
     * Asocia los eventos de la vista.
     *
     * <p>
     * Cada clase hija debe implementar este método.
     */
    protected abstract void bindEvents();
}