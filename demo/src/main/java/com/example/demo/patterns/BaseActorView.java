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
     * Cabecera común de las vistas.
     *
     * protected permite que las clases hijas puedan utilizarla.
     */
    protected HorizontalLayout header =
            new HorizontalLayout();


    /**
     * Contenido principal de la vista.
     *
     * Todas las vistas que hereden de esta clase tendrán
     * automáticamente este contenedor.
     */
    protected VerticalLayout body;


    /**
     * Constructor de la clase base.
     *
     * No necesitamos realizar aquí la inicialización porque
     * se hace mediante initView().
     */
    public BaseActorView() {

    }


    /**
     * Método que inicializa completamente la vista.
     *
     * Este método establece el orden en el que se construye
     * cualquier vista hija.
     *
     * Es importante que el orden sea:
     *
     *     buildLayout()
     *          ↓
     *     build()
     *          ↓
     *     bindEvents()
     *
     * Primero creamos los componentes comunes,
     * después construimos el contenido y finalmente
     * conectamos los eventos.
     */
    public void initView() {

        buildLayout();

        build();

        bindEvents();
    }


    /**
     * Construye la estructura común de la vista.
     *
     * Esta parte es igual para todas las clases hijas,
     * por lo que no tiene sentido repetirla en cada una.
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
         * AppLayout permite colocar componentes en la barra
         * superior mediante addToNavbar().
         */
        addToNavbar(header);


        // ---------------------------------------------------------
        // BODY
        // ---------------------------------------------------------

        body = new VerticalLayout();

        body.setSizeFull();


        /*
         * El body será el contenido principal del AppLayout.
         */
        setContent(body);
    }


    /**
     * Método abstracto que deben implementar las clases hijas.
     *
     * Aquí se construyen los componentes específicos de cada vista.
     *
     * Al ser abstracto, BaseActorView no sabe qué contenido tendrá
     * cada pantalla.
     *
     * Por ejemplo:
     *
     *     @Override
     *     protected void build() {
     *         Button boton = new Button("Aceptar");
     *         body.add(boton);
     *     }
     */
    protected abstract void build();


    /**
     * Método para registrar los eventos de los componentes.
     *
     * No todas las vistas necesitan eventos, por eso no es abstracto.
     *
     * Las clases que necesiten eventos pueden sobrescribirlo:
     *
     *     @Override
     *     protected void bindEvents() {
     *         boton.addClickListener(...);
     *     }
     *
     * Si una vista no tiene eventos, simplemente no hace nada.
     */
    protected void bindEvents() {
    }
}