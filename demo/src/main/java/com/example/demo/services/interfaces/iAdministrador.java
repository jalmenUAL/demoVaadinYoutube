package com.example.demo.services.interfaces;

import java.util.List;

import com.example.demo.tables.Video;

/**
 * Interfaz que define las operaciones disponibles para un Administrador.
 *
 * <p>
 * Extiende iRegistrado porque un Administrador también es un
 * usuario registrado y, por tanto, puede utilizar las operaciones
 * comunes definidas en dicha interfaz.
 *
 * <p>
 * Aquí solamente se declaran las operaciones específicas del
 * Administrador. La implementación real se encuentra en
 * BDPrincipal.
 */
public interface iAdministrador extends iRegistrado {


    /**
     * Elimina un comentario de la aplicación.
     *
     * @param idComentario identificador del comentario que se quiere eliminar
     */
    void eliminarComentario(Integer idComentario);


    /**
     * Obtiene los Youtubers que han sido denunciados.
     *
     * @return lista de usuarios denunciados
     */
    List<com.example.demo.tables.Youtuber> buscarDenunciados();


    /**
     * Obtiene todos los vídeos almacenados en la aplicación.
     *
     * <p>
     * Esta operación permite al administrador consultar todos
     * los vídeos, por ejemplo, para poder moderarlos o eliminarlos.
     *
     * @return lista de todos los vídeos
     */
    List<Video> getAllVideos();


    /**
     * Elimina un vídeo de la aplicación.
     *
     * @param idVideo identificador del vídeo que se quiere eliminar
     */
    void borrarVideo(Integer idVideo);


    /**
     * Bloquea a un Youtuber.
     *
     * <p>
     * Al bloquearlo, el usuario no podrá iniciar sesión.
     *
     * @param idYoutuber login del Youtuber que se quiere bloquear
     */
    void bloquearUsuario(String idYoutuber);


    /**
     * Desbloquea a un Youtuber previamente bloqueado.
     *
     * @param idYoutuber login del Youtuber que se quiere desbloquear
     */
    void desbloquearUsuario(String idYoutuber);

}