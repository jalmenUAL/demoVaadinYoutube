package com.example.demo.views.common;

import java.util.Set;
import java.util.Vector;

import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.patterns.BaseListParameterizedView;
import com.example.demo.services.iInicio;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@Route("Youtuberseguidos")
@RolesAllowed({ "ROLE_YOUTUBER", "ROLE_ADMINISTRADOR" })

public class Youtubersseguidos extends BaseListParameterizedView<String> {

    // Referencia a la vista Perfil.
    // Actualmente no se está utilizando directamente.
    public Perfil _perfil;

    // Vector donde se almacenan los elementos de la lista.
    public Vector<Youtubersseguidos_item> _item =
            new Vector<Youtubersseguidos_item>();

    // Conjunto de Youtubers que sigue el usuario.
    Set<com.example.demo.tables.Youtuber> youtubers;

    // Contenedor visual donde se mostrarán los Youtubers.
    FlexLayout gridContainer = new FlexLayout();

    // Servicio que proporciona las operaciones relacionadas con el inicio
    // y la búsqueda de Youtubers.
    iInicio _iInicio;

    // Proveedor de fábricas utilizado para crear las diferentes vistas.
    protected ViewFactoryProvider viewFactory;


    /**
     * Constructor de la vista.
     *
     * @param iInicio servicio utilizado para obtener información de los Youtubers
     * @param viewFactory proveedor de fábricas de vistas
     */
    public Youtubersseguidos(
            iInicio iInicio,
            ViewFactoryProvider viewFactory) {

        super();

        this._iInicio = iInicio;
        this.viewFactory = viewFactory;
    }


    /**
     * En esta vista no hay eventos propios que gestionar.
     */
    @Override
    protected void bindEvents() {

    }


    /**
     * Construye la lista de Youtubers seguidos.
     *
     * El parámetro recibido corresponde al identificador/login
     * del Youtuber cuyo listado de seguidos queremos mostrar.
     *
     * @param parameter identificador del Youtuber
     */
    @Override
    protected void buildList(String parameter) {

        // Buscamos el Youtuber utilizando el parámetro recibido
        // desde la navegación.
        com.example.demo.tables.Youtuber _youtuber =
                _iInicio.findYoutuberById(
                        String.valueOf(parameter));


        // Obtenemos el conjunto de Youtubers que sigue
        // el usuario seleccionado.
        Set<com.example.demo.tables.Youtuber> youtubers =
                _youtuber.getSeguidor_de();


        // Comprobamos si existe información de Youtubers seguidos.
        if (youtubers == null) {

            // No hay Youtubers que mostrar.
            // Actualmente no se añade ningún mensaje.
            
        } else {

            // Recorremos todos los Youtubers seguidos.
            for (com.example.demo.tables.Youtuber youtuber
                    : youtubers) {

                // Creamos el componente visual correspondiente
                // a cada Youtuber.
                //
                // Se utiliza ViewFactoryProvider para mantener
                // el patrón de factoría utilizado por la aplicación.
                Youtubersseguidos_item youtuberItem =
                        new Youtubersseguidos_item(
                                youtuber,
                                viewFactory);


                // Guardamos el elemento creado en el Vector.
                this._item.add(youtuberItem);


                // Añadimos el elemento al contenedor visual.
                gridContainer.add(youtuberItem);
            }
        }


        // Finalmente añadimos el contenedor a la vista.
        add(gridContainer);
    }
}