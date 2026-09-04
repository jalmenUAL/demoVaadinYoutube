package com.example.demo.patterns;

import java.util.Collection;

/**
 * Contenedor único de contratos de capacidad para la arquitectura del proyecto.
 * Permite agrupar todos los tipos sin dispersión de archivos.
 */
public final class Contracts {

    // Constructor privado para evitar instanciación de la clase contenedora
    private Contracts() {}

    // =========================================================================
    // 1. SERVICIO / BASE DE DATOS
    // =========================================================================
    /**
     * Contrato para vistas que interactúan con la capa de servicios / base de datos.
     *
     * @param <S> Tipo de la interfaz de servicio (ej. iAdministrador, iVideoService)
     */
    public interface HasService<S> {

        /**
         * Devuelve la instancia del servicio inyectado en la vista.
         * Usado por JUnit para auditar la inyección y por el framework.
         */
        S getServicio();
    }

    // =========================================================================
    // 2. ÍTEM / MODELO ÚNICO
    // =========================================================================
    /**
     * Contrato para vistas que presentan o manipulan una única entidad modelo.
     *
     * @param <T> Tipo de la entidad (ej. Video, Usuario, Comentario)
     */
    public interface HasModel<T> {

        /**
         * Devuelve el objeto modelo asociado a la vista.
         */
        T getModel();

        /**
         * Comprueba si el modelo ha sido asignado e inicializado.
         */
        default boolean hasValidModel() {
            return getModel() != null;
        }
    }

    // =========================================================================
    // 3. LISTAS / COLECCIONES DE ELEMENTOS
    // =========================================================================
    /**
     * Contrato para vistas que presentan una colección de elementos.
     *
     * @param <T> Tipo de los elementos de la colección
     */
    public interface HasElements<T> {

        /**
         * Devuelve la colección de elementos a mostrar.
         */
        Collection<T> getElements();

        /**
         * Construye el componente contenedor (Grid, VerticalLayout, FlexLayout, etc.).
         */
        void buildContainer();

        /**
         * Instancia y añade las vistas/componentes correspondientes a cada elemento.
         */
        void buildItems();

        /**
         * Secuencia predeterminada de construcción de listas.
         * Invocable directamente desde el método build() de la vista.
         */
        default void buildList() {
            buildContainer();
            buildItems();
        }

        /**
         * Comprueba si la colección contiene elementos.
         */
        default boolean hasElements() {
            Collection<T> elems = getElements();
            return elems != null && !elems.isEmpty();
        }
    }

    // =========================================================================
    // 4. POLIMORFISMO / ROLES
    // =========================================================================
    /**
     * Contrato para vistas cuya funcionalidad o UI se especializa según el actor.
     */
    public interface HasRole {

        /**
         * Enum o identificador del rol que opera la vista.
         */
        RoleType getRole();

        /**
         * Comprueba si el rol actual coincide con un rol esperado.
         */
        default boolean isRole(RoleType expectedRole) {
            return getRole() == expectedRole;
        }

        /**
         * Tipos de actores reconocidos en la arquitectura.
         */
        enum RoleType {
            NO_LOGUEADO,
            REGISTRADO,
            YOUTUBER,
            ADMINISTRADOR
        }
    }

    // =========================================================================
    // 5. FACTORÍAS
    // =========================================================================
    /**
     * Contrato para vistas o componentes que requieren crear otras vistas al vuelo.
     *
     * @param <F> Tipo del proveedor de factorías de vistas
     */
    public interface HasFactory<F> {

        /**
         * Devuelve el proveedor de factorías inyectado.
         */
        F getViewFactory();
    }
}