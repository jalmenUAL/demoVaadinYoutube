package com.example.demo.views.common;

import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.services.iInicio;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("PerfilAjeno")
@AnonymousAllowed
public class PerfilAjeno extends Perfil {

    /*
     * PerfilAjeno reutiliza toda la funcionalidad de Perfil.
     *
     * No necesitamos volver a implementar:
     *
     * - build()
     * - bindEvents()
     * - mostrar los vídeos publicados
     * - mostrar los vídeos gustados
     * - mostrar los Youtubers seguidos
     *
     * La clase padre ya proporciona toda esa funcionalidad.
     *
     * Esta clase existe como punto de entrada/ruta independiente
     * para poder mostrar el perfil de otro usuario.
     */

    public PerfilAjeno(
            iInicio iInicio,
            ViewFactoryProvider viewFactory) {

        /*
         * Pasamos las dependencias al constructor de Perfil.
         *
         * Perfil se encargará de almacenarlas y de construir
         * la vista cuando reciba el parámetro de la URL.
         */
        super(iInicio, viewFactory);
    }
}