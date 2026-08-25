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
/**
 * Clase base para las vistas que utilizan un VerticalLayout.
 *
 * <p>
 * Define el ciclo de vida común de todas las vistas:
 *
 * <pre>
 *     initView()
 *        ↓
 *      build()
 *        ↓
 *   bindEvents()
 * </pre>
 *
 * <p>
 * Las clases hijas son responsables de construir su interfaz
 * y registrar sus eventos, mientras que BaseView garantiza
 * que ambas fases se ejecuten siempre en el orden correcto.
 */
public abstract class BaseView extends VerticalLayout {


    /**
     * Constructor protegido.
     *
     * <p>
     * La creación de la vista corresponde a las clases hijas.
     */
    protected BaseView() {
    }


    /**
     * Inicializa completamente la vista.
     *
     * <p>
     * Primero se construyen todos los componentes mediante
     * {@link #build()} y posteriormente se registran sus
     * eventos mediante {@link #bindEvents()}.
     *
     * <p>
     * El método es {@code final} para impedir que una clase hija
     * modifique el ciclo de inicialización.
     */
    protected final void initView() {

        // ---------------------------------------------------------
        // CONSTRUCCIÓN DE LA INTERFAZ
        // ---------------------------------------------------------

        build();


        // ---------------------------------------------------------
        // REGISTRO DE EVENTOS
        // ---------------------------------------------------------

        bindEvents();
    }


    /**
     * Construye los componentes específicos de la vista.
     *
     * <p>
     * Cada clase hija debe implementar este método.
     */
    protected abstract void build();


    /**
     * Registra los eventos de los componentes.
     *
     * <p>
     * Cada clase hija debe implementar este método.
     *
     * <p>
     * Si la vista no tiene eventos propios, el método puede
     * implementarse vacío.
     */
    protected abstract void bindEvents();
}