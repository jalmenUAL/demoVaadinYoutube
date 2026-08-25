package com.example.demo.factories;

import java.util.Set;

import com.example.demo.tables.Comentario;
import com.example.demo.views.GaleradeVideos_item;
import com.example.demo.views.PerfilAjeno;
import com.example.demo.views.VerComentarios;
import com.example.demo.views.VerComentarios_item;
import com.example.demo.views.VerVideo;

/**
 * Interfaz común para las factorías de vistas.
 *
 * <p>
 * Define las vistas que puede proporcionar una factoría,
 * independientemente del tipo de usuario.
 *
 * <p>
 * Por ejemplo, puede existir una factoría para:
 *
 *     - Usuarios no logueados.
 *     - Youtubers.
 *     - Administradores.
 *
 * Todas ellas implementan esta misma interfaz, pero cada una
 * devuelve sus propias implementaciones concretas de las vistas.
 *
 * <p>
 * Esto permite desacoplar las vistas del tipo concreto de usuario.
 * La vista únicamente conoce ViewFactory y no necesita saber
 * qué clase concreta va a recibir.
 */
public interface ViewFactory {


    /**
     * Obtiene la clase de la vista utilizada para visualizar
     * un vídeo.
     *
     * <p>
     * Se devuelve Class porque la navegación de Vaadin necesita
     * conocer la clase de destino.
     */
    Class<? extends VerVideo> createVideo();


    /**
     * Obtiene la clase de la vista utilizada para consultar
     * el perfil de otro usuario.
     */
    Class<? extends PerfilAjeno> createPerfilAjeno();


    /**
     * Crea una vista para mostrar los comentarios de un vídeo.
     *
     * @param comentarios conjunto de comentarios que se mostrarán
     * @param idvideo identificador del vídeo
     * @param viewFactory proveedor de factorías que podrá utilizar
     *                    la vista para realizar otras navegaciones
     *
     * @return vista de comentarios correspondiente al tipo de usuario
     */
    VerComentarios createVerComentarios(
            Set<Comentario> comentarios,
            int idvideo,
            ViewFactoryProvider viewFactory);


    /**
     * Obtiene la clase que representa un elemento individual
     * dentro de la galería de vídeos.
     *
     * <p>
     * Cada tipo de usuario puede tener una implementación
     * diferente de este elemento.
     */
    Class<? extends GaleradeVideos_item> createGaleriaItem();


    /**
     * Crea el componente que representa un comentario individual.
     *
     * @param comentario comentario que se debe mostrar
     * @param viewFactory proveedor de factorías que puede utilizar
     *                    el componente para realizar navegaciones
     *
     * @return componente utilizado para representar el comentario
     */
    VerComentarios_item createVerComentariosItem(
            Comentario comentario,
            ViewFactoryProvider viewFactory);
}
