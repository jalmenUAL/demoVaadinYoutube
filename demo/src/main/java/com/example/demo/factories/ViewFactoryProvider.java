package com.example.demo.factories;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
/**
 * Proveedor de factorías de vistas.
 *
 * <p>
 * Esta clase se encarga de seleccionar la factoría que corresponde
 * al usuario que está utilizando actualmente la aplicación.
 *
 * <p>
 * Existen tres factorías:
 *
 *     - AdministradorViewFactory
 *     - YoutuberViewFactory
 *     - NoLogueadoViewFactory
 *
 * <p>
 * La clase consulta la información de autenticación de Spring
 * Security para decidir cuál debe devolver.
 *
 * <p>
 * De esta forma, el resto de la aplicación no necesita comprobar
 * continuamente qué tipo de usuario está conectado.
 */
public class ViewFactoryProvider {

    /**
     * Factoría utilizada por los administradores.
     */
    private final AdministradorViewFactory administradorFactory;


    /**
     * Factoría utilizada por los youtubers.
     */
    private final YoutuberViewFactory youtuberFactory;


    /**
     * Factoría utilizada cuando no hay un usuario autenticado.
     */
    private final NoLogueadoViewFactory noLogueadoFactory;


    /**
     * Constructor.
     *
     * <p>
     * Las tres factorías se reciben mediante inyección de
     * dependencias. El proveedor no necesita crearlas directamente.
     */
    public ViewFactoryProvider(
            AdministradorViewFactory administradorFactory,
            YoutuberViewFactory youtuberFactory,
            NoLogueadoViewFactory noLogueadoFactory) {

        this.administradorFactory = administradorFactory;
        this.youtuberFactory = youtuberFactory;
        this.noLogueadoFactory = noLogueadoFactory;
    }


    /**
     * Devuelve la factoría que corresponde al usuario actual.
     *
     * <p>
     * Primero se consulta la autenticación de Spring Security.
     * Después se comprueba el rol del usuario.
     *
     * @return la factoría correspondiente al usuario actual
     */
    public ViewFactory getFactory() {

        /*
         * Obtener la información de autenticación de Spring Security.
         *
         * SecurityContextHolder contiene la información del usuario
         * que está realizando actualmente la petición.
         */
        Authentication auth =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();


        /*
         * Comprobar si existe un usuario autenticado.
         *
         * También se descarta el usuario "anonymousUser", que
         * representa a un usuario que no ha iniciado sesión.
         */
        if (auth != null
                && auth.isAuthenticated()
                && !auth.getPrincipal().equals("anonymousUser")) {


            /*
             * Comprobar si el usuario tiene el rol
             * ROLE_ADMINISTRADOR.
             *
             * getAuthorities() contiene los roles/permisos
             * asignados al usuario.
             */
            boolean esAdmin =
                    auth.getAuthorities().stream()
                            .anyMatch(a ->
                                    a.getAuthority()
                                            .equals("ROLE_ADMINISTRADOR"));


            /*
             * Comprobar si el usuario tiene el rol
             * ROLE_YOUTUBER.
             */
            boolean esYoutuber =
                    auth.getAuthorities().stream()
                            .anyMatch(a ->
                                    a.getAuthority()
                                            .equals("ROLE_YOUTUBER"));


            /*
             * Si es administrador, utilizar la factoría
             * específica para administradores.
             */
            if (esAdmin) {
                return administradorFactory;
            }


            /*
             * Si es youtuber, utilizar la factoría
             * específica para youtubers.
             */
            if (esYoutuber) {
                return youtuberFactory;
            }
        }


        /*
         * Si no hay usuario autenticado o no pertenece a ninguno
         * de los roles anteriores, se utiliza la factoría para
         * usuarios no logueados.
         */
        return noLogueadoFactory;
    }
}