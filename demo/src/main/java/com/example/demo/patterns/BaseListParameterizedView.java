package com.example.demo.patterns;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * Clase base para las vistas que:
 *
 * 1. Reciben un parámetro desde la URL.
 * 2. Muestran una lista cuyo contenido depende de ese parámetro.
 *
 * <p>
 * Hereda de BaseParameterizedView<T>, por lo que mantiene
 * el mecanismo de navegación parametrizada de Vaadin.
 *
 * <p>
 * El parámetro T puede representar, por ejemplo:
 *
 *     Integer -> ID de un vídeo
 *     String  -> login de un usuario
 *
 * <p>
 * Esta clase utiliza el patrón Template Method:
 *
 *     setParameter()
 *          ↓
 *     initView(parameter)
 *          ↓
 *     build(parameter)
 *          ↓
 *     buildList(parameter)
 *
 * La clase base controla la estructura general y la clase hija
 * decide cómo construir concretamente la lista.
 */
public abstract class BaseListParameterizedView<T>
        extends BaseParameterizedView<T> {


    /**
     * Contenedor donde se mostrarán los elementos de la lista.
     *
     * <p>
     * Las clases hijas pueden utilizar este layout para añadir
     * los elementos que correspondan.
     */
    protected VerticalLayout body;


    /**
     * Constructor.
     *
     * <p>
     * No necesitamos hacer nada especial porque la inicialización
     * se realizará cuando Vaadin entregue el parámetro mediante
     * setParameter().
     */
    public BaseListParameterizedView() {
        super();
    }


    /**
     * Construye la estructura general de la vista.
     *
     * <p>
     * Este método sobrescribe el build(T parameter) de
     * BaseParameterizedView.
     *
     * <p>
     * El proceso es:
     *
     *     1. Crear el contenedor.
     *     2. Añadirlo a la vista.
     *     3. Dejar que la clase hija construya la lista.
     */
    @Override
    protected void build(T parameter) {

        // ---------------------------------------------------------
        // Crear el contenedor de la lista
        // ---------------------------------------------------------

        body = new VerticalLayout();

        body.setWidthFull();


        // ---------------------------------------------------------
        // Añadir el contenedor a la vista
        // ---------------------------------------------------------

        add(body);


        // ---------------------------------------------------------
        // Construir el contenido específico
        // ---------------------------------------------------------

        /*
         * La clase hija decide qué elementos debe mostrar
         * utilizando el parámetro recibido.
         */
        buildList(parameter);
    }


    /**
     * Construye la lista concreta de la vista.
     *
     * <p>
     * Cada clase hija debe implementar este método.
     *
     * Por ejemplo, si el parámetro es un ID de vídeo,
     * podría utilizarlo para obtener los comentarios
     * relacionados con ese vídeo y mostrarlos dentro de body.
     */
    protected abstract void buildList(T parameter);
}