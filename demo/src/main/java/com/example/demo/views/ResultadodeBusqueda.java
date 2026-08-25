package com.example.demo.views;

import java.util.List;

import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.tables.Video;
import com.vaadin.flow.router.Route;

@Route("ResultadodeBusqueda")
public class ResultadodeBusqueda
        extends GaleradeVideos {

    /*
     * Esta vista reutiliza la estructura de GaleradeVideos.
     *
     * Heredamos:
     *  - el contenedor de la galería
     *  - el título
     *  - el carrusel
     *  - la colección de elementos
     *  - el ViewFactoryProvider
     *
     * Y solamente modificamos lo necesario para representar
     * los resultados de una búsqueda.
     */

    

    public ResultadodeBusqueda(
            List<Video> resultados,
            ViewFactoryProvider viewFactory) {

        /*
         * Delegamos en el constructor de la clase padre.
         */
        super(resultados, viewFactory);
    }

    /*
     * Personalizamos la construcción de la vista.
     *
     * Primero ejecutamos el build() de GaleradeVideos para que
     * se cree toda la estructura general.
     *
     * Después modificamos solamente el título.
     */
    @Override
    protected void build() {

        super.build();

        tituloGaleria.setText(
                "Resultados de la búsqueda");
    }

    /*
     * Aquí aparece la verdadera especialización.
     *
     * GaleradeVideos utiliza GaleradeVideos_item.
     *
     * ResultadodeBusqueda utiliza ResultadodeBusqueda_item.
     *
     * Como ResultadodeBusqueda_item hereda de
     * GaleradeVideos_item, seguimos reutilizando toda
     * la funcionalidad común.
     */
    @Override
    protected void buildItems() {

        for (Video video : elements) {

            ResultadodeBusqueda_item item =
                    new ResultadodeBusqueda_item(
                            video,
                            viewFactory);

            carrusel.add(item);
        }
    }
}