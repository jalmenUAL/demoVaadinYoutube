package com.example.demo.patterns;

import com.example.demo.factories.ViewFactoryProvider;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

 

/**
 * Clase base para las vistas parametrizadas que muestran una lista de elementos
 * cuyo contenido depende del parámetro recibido por la URL.
 *
 * <p>
 * Esta clase utiliza el patrón Template Method:
 * <pre>
 *     setParameter()
 *          ↓
 *     initView(parameter)
 *          ↓
 *     build(parameter)
 *          ↓
 *     buildList(parameter)
 * </pre>
 *
 * @param <T> tipo del parámetro recibido por la URL (ej. Long, String)
 * @param <S> tipo de la interfaz del servicio asociado a la vista
 */
public abstract class BaseListParameterizedView<T, S> 
        extends BaseParameterizedView<T, S> {

    /**
     * Contenedor principal de la lista.
     *
     * <p>
     * Las clases hijas pueden utilizarlo para añadir los elementos que correspondan.
     */
    protected VerticalLayout body;

    /**
     * Constructor obligatorio de la vista parametrizada con lista.
     *
     * <p>
     * Al no existir un constructor por defecto sin argumentos,
     * Java impedirá compilar a cualquier clase hija que no invoque explícitamente super(viewFactory, servicio).
     *
     * @param viewFactory proveedor de factorías de la aplicación
     * @param servicio    interfaz del servicio de negocio
     */
    protected BaseListParameterizedView(ViewFactoryProvider viewFactory, S servicio) {
        super(viewFactory, servicio);
    }

    /**
     * Construye la estructura común de la vista.
     *
     * <p>
     * Primero se crea el contenedor de la lista y después
     * se delega en la clase hija la construcción de sus elementos.
     *
     * @param parameter parámetro recibido por la vista desde la URL
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