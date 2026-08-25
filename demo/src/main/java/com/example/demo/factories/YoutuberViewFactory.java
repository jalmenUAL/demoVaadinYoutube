package com.example.demo.factories;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.example.demo.tables.Comentario;
import com.example.demo.views.UltimosVideos_item;
import com.example.demo.views.VerComentarios_item;
import com.example.demo.views.VerComentariosdeYoutuber;
import com.example.demo.views.VerComentariosdeYoutuber_item;

 
@Component
/**
 * Factoría de vistas para usuarios con rol YOUTUBER.
 *
 * <p>
 * Implementa la interfaz ViewFactory, por lo que proporciona
 * todas las vistas que puede necesitar un Youtuber.
 *
 * <p>
 * La estructura es la misma que en las demás factorías, pero
 * aquí se seleccionan las implementaciones específicas para
 * Youtubers.
 *
 * <p>
 * Por ejemplo:
 *
 *     VerVideo
 *          ↓
 *     VerVideodeYoutuber
 *
 * Mientras que un Administrador utilizaría:
 *
 *     VerVideo
 *          ↓
 *     VerVideodeAdministrador
 *
 * De esta forma, el código que utiliza la factoría puede trabajar
 * con las clases base y no necesita conocer las implementaciones
 * concretas.
 */
public class YoutuberViewFactory implements ViewFactory {


    /**
     * Devuelve la clase que se utilizará para visualizar un vídeo
     * cuando el usuario es un Youtuber.
     *
     * <p>
     * Se devuelve la clase mediante .class porque la navegación
     * de Vaadin necesita conocer la clase de destino.
     */
    @Override
    public Class<? extends com.example.demo.views.VerVideo> createVideo() {

        return com.example.demo.views.VerVideodeYoutuber.class;
    }


    /**
     * Devuelve la clase utilizada para visualizar el perfil
     * de otro usuario desde la perspectiva de un Youtuber.
     */
    @Override
    public Class<? extends com.example.demo.views.PerfilAjeno> createPerfilAjeno() {

        return com.example.demo.views.PerfilAjenodeYoutuber.class;
    }


    /**
     * Crea la vista que muestra los comentarios de un vídeo.
     *
     * <p>
     * En este caso se crea directamente el objeto mediante new
     * porque la vista necesita recibir información en el constructor.
     *
     * @param comentarios comentarios que debe mostrar la vista
     * @param idvideo identificador del vídeo
     * @param viewFactory proveedor de factorías utilizado para
     *                    realizar otras navegaciones
     *
     * @return vista de comentarios para un Youtuber
     */
    @Override
    public com.example.demo.views.VerComentarios createVerComentarios(
            Set<Comentario> comentarios,
            int idvideo,
            ViewFactoryProvider viewFactory) {

        return new VerComentariosdeYoutuber(
                comentarios,
                idvideo,
                viewFactory);
    }


    /**
     * Devuelve la clase que representa un elemento individual
     * de la lista de últimos vídeos para un Youtuber.
     *
     * <p>
     * Se devuelve la clase concreta, pero todavía no se crea
     * ninguna instancia.
     */
    @Override
    public Class<? extends UltimosVideos_item> createGaleriaItem() {

        return com.example.demo.views.UltimosVideosdeYoutuber_item.class;
    }


    /**
     * Crea el componente utilizado para mostrar un comentario
     * individual.
     *
     * <p>
     * Se utiliza new porque el comentario concreto se recibe
     * como parámetro del constructor.
     */
    @Override
    public VerComentarios_item createVerComentariosItem(
            Comentario comentario,
            ViewFactoryProvider viewFactory) {

        return new VerComentariosdeYoutuber_item(
                comentario,
                viewFactory);
    }
}