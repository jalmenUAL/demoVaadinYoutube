package com.example.demo.views.administrador;

import java.util.List;

import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.services.iAdministrador;
import com.example.demo.tables.Video;
import com.example.demo.views.inicio.UltimosVideos;
import com.example.demo.views.registrado.Registrado;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;


/**
 * Vista principal utilizada por los administradores.
 *
 * <p>
 * Esta vista hereda de Registrado porque un administrador también
 * comparte parte de la estructura y comportamiento de un usuario
 * registrado.
 *
 * <p>
 * El acceso a esta vista está protegido mediante dos mecanismos:
 *
 *     1. @Route indica la URL de la vista.
 *     2. @RolesAllowed limita el acceso a usuarios con rol
 *        ROLE_ADMINISTRADOR.
 *
 * <p>
 * Por tanto, un Youtuber o un usuario no autenticado no debería
 * poder acceder a esta vista.
 */
@Route("Administrador")
@RolesAllowed("ROLE_ADMINISTRADOR")
public class Administrador extends Registrado {


    /**
     * Interfaz de servicios que proporciona las operaciones
     * específicas de un administrador.
     *
     * <p>
     * La vista trabaja contra la interfaz y no directamente
     * contra BDPrincipal o los repositorios.
     */
    protected final iAdministrador iAdministrador;


    /**
     * Componente que muestra la lista de usuarios denunciados.
     */
    protected Usuariosdenunciados _usuariosdenunciados;


    /**
     * Constructor.
     *
     * <p>
     * Spring inyecta:
     *
     *     - iAdministrador: acceso a las operaciones del administrador.
     *     - ViewFactoryProvider: proveedor de la factoría de vistas.
     */
    public Administrador(
            iAdministrador iAdministrador,
            ViewFactoryProvider viewFactory) {

        /*
         * El constructor de la clase padre se encarga de inicializar
         * la parte común de las vistas de usuarios registrados.
         */
        super(iAdministrador, viewFactory);


        /*
         * Guardamos la interfaz porque esta vista necesita utilizar
         * operaciones específicas del administrador.
         */
        this.iAdministrador = iAdministrador;


        /*
         * Inicializar la vista siguiendo el patrón de las clases base:
         *
         *     build()
         *        ↓
         *     bindEvents()
         */
        initView();
    }


    /**
     * Construye el contenido específico de la vista.
     *
     * <p>
     * Primero se ejecuta super.build() para construir todos los
     * elementos comunes heredados de Registrado.
     *
     * <p>
     * Después se añade la lista de usuarios denunciados, que es
     * exclusiva de esta vista.
     */
    @Override
    protected void build() {


        /*
         * Añadir la lista de usuarios denunciados.
         */
        Usuariosdenunciados();

        /*
         * Construir primero la estructura común de Registrado.
         */
        super.build();


    }


    /**
     * Construye la sección que muestra los últimos vídeos.
     *
     * <p>
     * En el caso del administrador se obtienen todos los vídeos,
     * ya que el administrador puede consultar todos ellos para
     * realizar tareas de administración o moderación.
     */
    @Override
    protected void UltimosVideos() {

        /*
         * Obtener todos los vídeos mediante la interfaz
         * de administrador.
         */
        List<Video> ultimosVideos =
                iAdministrador.getAllVideos();


        /*
         * Crear el componente encargado de mostrar los vídeos.
         *
         * Se le pasa viewFactory para que los elementos de la lista
         * puedan realizar navegaciones utilizando la factoría
         * correspondiente al usuario actual.
         */
        _ultimosVideos =
                new UltimosVideos(
                        ultimosVideos,
                        viewFactory);


        /*
         * Añadir el componente al cuerpo de la vista.
         */
        body.add(_ultimosVideos);
    }


    /**
     * Obtiene y muestra los usuarios que han sido denunciados.
     *
     * <p>
     * Esta funcionalidad es específica del administrador.
     */
    private void Usuariosdenunciados() {

        /*
         * Obtener los usuarios denunciados a través de la
         * interfaz de servicios.
         */
        List<com.example.demo.tables.Youtuber> denunciados =
                iAdministrador.buscarDenunciados();


        /*
         * Crear el componente visual que mostrará la lista.
         */
        _usuariosdenunciados =
                new Usuariosdenunciados(denunciados);


        /*
         * Añadirlo al cuerpo principal de la vista.
         */
        body.add(_usuariosdenunciados);
    }


    /**
     * Registra los eventos de la vista.
     *
     * <p>
     * En esta vista no se añaden eventos específicos, pero se llama
     * a super.bindEvents() para conservar los eventos definidos
     * por las clases padre.
     */
    @Override
    protected void bindEvents() {

        super.bindEvents();
    }
}