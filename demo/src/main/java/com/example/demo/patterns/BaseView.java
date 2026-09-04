package com.example.demo.patterns;

import com.example.demo.factories.ViewFactoryProvider;
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
 

/**
 * Clase base abstracta para todas las vistas genéricas de la aplicación.
 *
 * @param <S> Tipo de la interfaz del servicio asociado a la vista.
 */
 

public abstract class BaseView<S> extends VerticalLayout implements iBaseView<S> {

    protected final ViewFactoryProvider viewFactory;
    protected final S servicio;

    protected BaseView(ViewFactoryProvider viewFactory, S servicio) {
        if (viewFactory == null) {
            throw new IllegalArgumentException("El ViewFactoryProvider no puede ser nulo.");
        }
        if (servicio == null) {
            throw new IllegalArgumentException("El servicio no puede ser nulo.");
        }

        boolean esInterfaz = servicio.getClass().getInterfaces().length > 0;
        if (!esInterfaz) {
            throw new IllegalArgumentException(
                "La vista " + getClass().getSimpleName() + 
                " debe recibir una INTERFAZ de servicio en su constructor, no una clase concreta (" + 
                servicio.getClass().getName() + ")."
            );
        }

        this.viewFactory = viewFactory;
        this.servicio = servicio;
    }

    @Override
    public final void initView() {
        build();
        bindEvents();
    }

    @Override
    public ViewFactoryProvider getViewFactory() {
        return viewFactory;
    }

    @Override
    public S getServicio() {
        return servicio;
    }

    protected abstract void build();
    protected abstract void bindEvents();
}