package com.example.demo.patterns;

import com.example.demo.factories.ViewFactoryProvider;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * Clase base para las vistas principales de los actores de la aplicación.
 *
 * <p>
 * Esta clase utiliza principalmente dos ideas:
 *
 * 1. HERENCIA:
 *    Las vistas concretas heredan de BaseActorView.
 *
 * 2. TEMPLATE METHOD:
 *    initView() establece el orden general de construcción de una vista:
 *
 *        1. Construir la estructura común
 *        2. Construir el contenido específico
 *        3. Asociar los eventos
 *
 *    Las clases hijas solamente tienen que implementar build()
 *    y, si lo necesitan, sobrescribir bindEvents().
 *
 * <p>
 * Por ejemplo:
 *
 *     public class Inicio extends BaseActorView {
 *
 *         @Override
 *         protected void build() {
 *             // Contenido específico de Inicio
 *         }
 *
 *         @Override
 *         protected void bindEvents() {
 *             // Eventos específicos de Inicio
 *         }
 *     }
 *
 * De esta forma evitamos repetir en todas las vistas la creación
 * del header, el body y la configuración básica del AppLayout.
 */
 

/**
 * Clase base abstracta para las vistas asociadas a un actor.
 *
 * @param <S> Tipo de la interfaz del servicio asociado a la vista.
 */
 

public abstract class BaseActorView<S> extends AppLayout implements iBaseView<S> {

    protected final ViewFactoryProvider viewFactory;
    protected final S servicio;

    protected HorizontalLayout header;
    protected VerticalLayout body;

    protected BaseActorView(ViewFactoryProvider viewFactory, S servicio) {
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
        buildLayout();
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

    private void buildLayout() {
        header = new HorizontalLayout();
        header.setWidthFull();
        header.setAlignItems(Alignment.CENTER);
        addToNavbar(header);

        body = new VerticalLayout();
        body.setSizeFull();
        setContent(body);
    }

    protected abstract void build();
    protected abstract void bindEvents();
}