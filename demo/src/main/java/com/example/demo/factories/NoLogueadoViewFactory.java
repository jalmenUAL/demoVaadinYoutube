package com.example.demo.factories;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.example.demo.tables.Comentario;
import com.example.demo.views.UltimosVideos_item;
import com.example.demo.views.VerComentarios;
import com.example.demo.views.VerComentarios_item;
import com.example.demo.views.VerComentariosdeYoutuber;

 
@Component
/**
 * Factoría de vistas para usuarios que no han iniciado sesión.
 *
 * <p>
 * Implementa la interfaz ViewFactory, por lo que debe proporcionar
 * las vistas que puede utilizar un usuario no logueado.
 *
 * <p>
 * La ventaja de utilizar una factoría es que el código que navega
 * por la aplicación no necesita saber si el usuario es:
 *
 *     - No logueado
 *     - Youtuber
 *     - Administrador
 *
 * La factoría correspondiente se encarga de devolver la
 * implementación adecuada de cada vista.
 */
public class NoLogueadoViewFactory implements ViewFactory {


    /**
     * Devuelve la vista que se utilizará para visualizar un vídeo
     * cuando el usuario no ha iniciado sesión.
     *
     * <p>
     * Se devuelve la clase mediante .class.
     *
     * No se crea todavía una instancia de la vista.
     */
    @Override
    public Class<? extends com.example.demo.views.VerVideo> createVideo() {

        return com.example.demo.views.VerVideo.class;
    }


    /**
     * Devuelve la vista utilizada para consultar el perfil
     * de otro usuario.
     *
     * <p>
     * De nuevo, se devuelve la clase concreta que debe utilizar
     * Vaadin para realizar la navegación.
     */
    @Override
    public Class<? extends com.example.demo.views.PerfilAjeno> createPerfilAjeno() {

        return com.example.demo.views.PerfilAjeno.class;
    }


    /**
     * Crea la vista que muestra los comentarios de un vídeo.
     *
     * <p>
     * En este caso se crea directamente el objeto mediante new
     * porque la vista necesita recibir información en su constructor:
     *
     *     - Los comentarios que debe mostrar.
     *     - El ID del vídeo.
     *     - La factoría de vistas.
     *
     * <p>
     * Esto permite que VerComentarios utilice la misma factoría
     * para realizar nuevas navegaciones dentro de la aplicación.
     */
    @Override
    public com.example.demo.views.VerComentarios createVerComentarios(
            Set<Comentario> comentarios,
            int idvideo,
            ViewFactoryProvider viewFactory) {

        return new VerComentarios(
                comentarios,
                idvideo,
                viewFactory);
    }


    /**
     * Devuelve la clase que representa un elemento individual
     * de la galería de últimos vídeos.
     *
     * <p>
     * No se crea el objeto todavía; se proporciona la clase
     * que deberá utilizarse.
     */
    @Override
    public Class<? extends UltimosVideos_item> createGaleriaItem() {

        return com.example.demo.views.UltimosVideos_item.class;
    }


    /**
     * Crea un elemento individual para mostrar un comentario.
     *
     * <p>
     * Este método recibe el comentario concreto que se quiere
     * representar y la factoría que podrá utilizar el componente
     * para realizar otras navegaciones.
     */
    @Override
    public VerComentarios_item createVerComentariosItem(
            Comentario comentario,
            ViewFactoryProvider viewFactory) {

        return new com.example.demo.views.VerComentarios_item(
                comentario,
                viewFactory);
    }
}