package com.example.demo.patterns;

import com.example.demo.factories.ViewFactoryProvider;
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
/**
 * Clase base para las vistas que reciben un parámetro en la URL.
 *
 * <p>
 * Define un ciclo de vida común para todas las vistas parametrizadas:
 *
 * <pre>
 *     setParameter()
 *          ↓
 *     initView(parameter)
 *          ↓
 *     build(parameter)
 *          ↓
 *     bindEvents()
 * </pre>
 *
 * <p>
 * Los métodos que controlan el ciclo de vida son {@code final},
 * evitando que las clases hijas puedan modificar el orden.
 *
 * @param <T> tipo del parámetro recibido desde la URL
 */
 

/**
 * Clase base abstracta para vistas que reciben un parámetro desde la URL.
 *
 * @param <T> Tipo del parámetro de la URL (ej. Long, String, etc.).
 * @param <S> Tipo de la interfaz del servicio asociado a la vista.
 */
public abstract class BaseParameterizedView<T, S> 
        extends VerticalLayout 
        implements HasUrlParameter<T>, iBaseView<S> {

    /**
     * Proveedor de factorías para la gestión de componentes dinámicos según el rol.
     */
    protected final ViewFactoryProvider viewFactory;

    /**
     * Servicio de negocio principal consumido por la vista.
     */
    protected final S servicio;

    /**
     * Constructor protegido obligatorio.
     *
     * <p>
     * Exige explícitamente el ViewFactoryProvider y el servicio de negocio.
     *
     * @param viewFactory Proveedor de factorías de la aplicación.
     * @param servicio    Interfaz del servicio de negocio.
     */
    protected BaseParameterizedView(ViewFactoryProvider viewFactory, S servicio) {
        if (viewFactory == null) {
            throw new IllegalArgumentException("El ViewFactoryProvider no puede ser nulo.");
        }
        if (servicio == null) {
            throw new IllegalArgumentException("El servicio de negocio no puede ser nulo.");
        }

        // Validación en tiempo de ejecución: garantiza que el servicio inyectado sea una interfaz
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

    /**
     * Método genérico de la interfaz iBaseView.
     * 
     * Nota: En vistas parametrizadas, la inicialización real se difiere 
     * hasta que Vaadin invoca setParameter(event, parameter).
     */
    @Override
    public final void initView() {
        // Implementación vacía o de respaldo exigida por iBaseView<S>
    }

    @Override
    public ViewFactoryProvider getViewFactory() {
        return viewFactory;
    }

    @Override
    public S getServicio() {
        return servicio;
    }

    /**
     * Recibe el parámetro proporcionado por Vaadin.
     *
     * <p>
     * Este método es llamado automáticamente por Vaadin
     * cuando se navega a una vista parametrizada.
     *
     * <p>
     * Es {@code final} para impedir que las clases hijas
     * modifiquen el ciclo de inicialización.
     */
    @Override
    public final void setParameter(BeforeEvent event, T parameter) {
        initView(parameter);
    }

    /**
     * Inicializa la vista utilizando el parámetro recibido.
     *
     * <p>
     * El orden de construcción queda garantizado:
     *
     * <pre>
     *     build(parameter)
     *          ↓
     *     bindEvents()
     * </pre>
     *
     * <p>
     * Es {@code final} para que ninguna clase hija pueda
     * alterar este orden.
     */
    protected final void initView(T parameter) {
        // Primero construimos los componentes.
        build(parameter);

        // Después conectamos los eventos.
        bindEvents();
    }

    /**
     * Construye los componentes específicos de la vista.
     *
     * <p>
     * Cada clase hija debe implementar este método.
     *
     * @param parameter parámetro recibido desde la URL
     */
    protected abstract void build(T parameter);

    /**
     * Registra los eventos de los componentes.
     *
     * <p>
     * Todas las clases hijas deben implementar este método.
     *
     * <p>
     * Si una vista no tiene eventos propios, deberá proporcionar
     * una implementación vacía.
     */
    protected abstract void bindEvents();
}