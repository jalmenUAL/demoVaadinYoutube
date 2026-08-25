package com.example.demo.patterns;

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
public abstract class BaseActorView extends AppLayout {

    /**
     * Cabecera común de todas las vistas.
     *
     * Las clases hijas pueden utilizarla para añadir
     * botones, buscadores, títulos, etc.
     */
    protected HorizontalLayout header;

    /**
     * Contenido principal de la vista.
     */
    protected VerticalLayout body;


    /**
     * Constructor de la vista.
     *
     * La inicialización se realiza mediante initView().
     */
    protected BaseActorView() {
    }


    /**
     * Inicializa completamente la vista.
     *
     * El orden de inicialización es siempre:
     *
     *     buildLayout()
     *          ↓
     *     build()
     *          ↓
     *     bindEvents()
     *
     * Este método es final para impedir que una clase hija
     * pueda alterar el ciclo de construcción de la vista.
     */
    public final void initView() {

        buildLayout();

        build();

        bindEvents();
    }


    /**
     * Construye la estructura común de la vista.
     *
     * Esta parte pertenece a la clase base y no debe
     * repetirse en las clases hijas.
     */
    private void buildLayout() {

        // ---------------------------------------------------------
        // HEADER
        // ---------------------------------------------------------

        header = new HorizontalLayout();

        header.setWidthFull();

        header.setAlignItems(
                Alignment.CENTER);

        /*
         * AppLayout permite colocar el header
         * en la barra superior.
         */
        addToNavbar(header);


        // ---------------------------------------------------------
        // BODY
        // ---------------------------------------------------------

        body = new VerticalLayout();

        body.setSizeFull();

        /*
         * El body será el contenido principal
         * de la vista.
         */
        setContent(body);
    }


    /**
     * Construye los componentes específicos de la vista.
     *
     * Es obligatorio que cada clase hija implemente este método.
     */
    protected abstract void build();


    /**
     * Registra los eventos de los componentes.
     *
     * Es obligatorio implementarlo, aunque una vista no tenga
     * eventos. En ese caso simplemente se deja vacío.
     */
    protected abstract void bindEvents();
}