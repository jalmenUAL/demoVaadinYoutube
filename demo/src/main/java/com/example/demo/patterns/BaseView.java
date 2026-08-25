package com.example.demo.patterns;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * Clase base para las vistas que utilizan un VerticalLayout
 * como contenedor principal.
 *
 * <p>
 * Esta clase aplica el patrón Template Method.
 *
 * La idea es que todas las vistas sigan el mismo proceso
 * de inicialización:
 *
 *      1. Construir los componentes de la vista.
 *      2. Asociar los eventos.
 *
 * Las clases hijas solamente tienen que implementar
 * los detalles concretos de cada pantalla.
 */
public abstract class BaseView extends VerticalLayout {


    /**
     * Inicializa la vista siguiendo siempre el mismo orden.
     *
     * <p>
     * Primero se construye la interfaz mediante build()
     * y después se conectan los eventos mediante bindEvents().
     *
     * <p>
     * El método es final para impedir que una clase hija
     * cambie este orden.
     *
     * Por ejemplo, una clase hija no podría hacer:
     *
     *      bindEvents();
     *      build();
     *
     * porque los eventos podrían intentar utilizar
     * componentes que todavía no han sido creados.
     */
    protected final void initView() {

        // Primero creamos los componentes.
        build();

        // Después asociamos los eventos.
        bindEvents();
    }


    /**
     * Construye los componentes de la vista.
     *
     * <p>
     * Cada clase hija debe implementar este método
     * para crear su propia interfaz.
     */
    protected abstract void build();


    /**
     * Asocia los eventos de los componentes.
     *
     * <p>
     * Cada clase hija debe implementar este método
     * para indicar qué ocurre cuando el usuario
     * interactúa con botones, campos, etc.
     */
    protected abstract void bindEvents();
}