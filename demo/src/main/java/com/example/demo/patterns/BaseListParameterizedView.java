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
/**
 * Clase base para las vistas parametrizadas que muestran
 * una lista de elementos.
 *
 * <p>
 * El parámetro {@code T} permite que la vista reciba un dato
 * necesario para construir la lista.
 *
 * <p>
 * Por ejemplo:
 *
 * <pre>
 *     BaseListParameterizedView&lt;String&gt;
 * </pre>
 *
 * puede recibir un identificador de usuario para construir
 * la lista de elementos asociados a dicho usuario.
 *
 * @param <T> tipo del parámetro recibido por la vista
 */
public abstract class BaseListParameterizedView<T>
        extends BaseParameterizedView<T> {


    /**
     * Contenedor principal de la lista.
     *
     * <p>
     * Las clases hijas pueden utilizarlo para añadir
     * los elementos que correspondan.
     */
    protected VerticalLayout body;


    /**
     * Constructor de la vista.
     *
     * <p>
     * La construcción de la interfaz se realizará cuando
     * Vaadin proporcione el parámetro de la vista.
     */
    protected BaseListParameterizedView() {
        super();
    }


    /**
     * Construye la estructura común de la vista.
     *
     * <p>
     * Primero se crea el contenedor de la lista y después
     * se delega en la clase hija la construcción de sus elementos.
     *
     * @param parameter parámetro recibido por la vista
     */
    @Override
    protected void build(T parameter) {

        // ---------------------------------------------------------
        // CONTENEDOR DE LA LISTA
        // ---------------------------------------------------------

        body = new VerticalLayout();

        body.setWidthFull();


        // ---------------------------------------------------------
        // AÑADIR CONTENEDOR A LA VISTA
        // ---------------------------------------------------------

        add(body);


        // ---------------------------------------------------------
        // CONSTRUIR CONTENIDO ESPECÍFICO
        // ---------------------------------------------------------

        buildList(parameter);
    }


    /**
     * Construye el contenido específico de la lista.
     *
     * <p>
     * Cada clase hija debe implementar este método para decidir
     * qué elementos se muestran a partir del parámetro recibido.
     *
     * @param parameter parámetro recibido por la vista
     */
    protected abstract void buildList(T parameter);
}