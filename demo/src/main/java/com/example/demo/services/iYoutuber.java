package com.example.demo.services;

import java.io.InputStream;

import com.example.demo.tables.Video;

/**
 * Interfaz que define las operaciones disponibles para un Youtuber
 * autenticado.
 *
 * <p>
 * Extiende iRegistrado porque un Youtuber es un usuario registrado
 * y, por tanto, hereda todas las operaciones comunes de un usuario
 * autenticado.
 *
 * <p>
 * Además, añade las operaciones específicas de un Youtuber:
 *
 *     - Publicar vídeos.
 *     - Publicar comentarios.
 *     - Seguir y dejar de seguir usuarios.
 *     - Dar y quitar "me gusta".
 *     - Denunciar y retirar denuncias.
 *     - Modificar su configuración.
 */
public interface iYoutuber extends iRegistrado {


    /**
     * Busca un vídeo mediante su identificador.
     *
     * @param idVideo identificador del vídeo
     * @return vídeo encontrado
     */
    Video findVideoById(int idVideo);


    /**
     * Publica un nuevo vídeo.
     *
     * @param loginYoutuber login del Youtuber que publica el vídeo
     * @param titulo título del vídeo
     * @param url URL del vídeo
     */
    void publicarVideo(
            String loginYoutuber,
            String titulo,
            String url);


    /**
     * Publica un comentario sobre un vídeo.
     *
     * @param loginYoutuber login del usuario que escribe el comentario
     * @param idVideo identificador del vídeo comentado
     * @param contenido texto del comentario
     */
    void publicarComentario(
            String loginYoutuber,
            String idVideo,
            String contenido);


    /**
     * Actualiza la configuración del perfil del Youtuber.
     *
     * <p>
     * Permite modificar la contraseña, el avatar y la imagen
     * de fondo.
     *
     * <p>
     * InputStream se utiliza para recibir los archivos subidos
     * por el usuario sin tener que cargar directamente un archivo
     * completo en memoria.
     */
    void actualizarConfiguracion(
            String login,
            String password,
            InputStream avatar,
            String avatarNombre,
            InputStream fondo,
            String fondoNombre);


    /**
     * Comienza a seguir a otro Youtuber.
     *
     * @param loginSeguido usuario que queremos seguir
     * @param loginSeguidor usuario que realiza el seguimiento
     */
    void seguirUsuario(
            String loginSeguido,
            String loginSeguidor);


    /**
     * Deja de seguir a otro Youtuber.
     *
     * @param loginSeguido usuario al que dejamos de seguir
     * @param loginSeguidor usuario que deja de seguirlo
     */
    void dejardeseguirUsuario(
            String loginSeguido,
            String loginSeguidor);


    /**
     * Añade un vídeo a la lista de vídeos que le gustan
     * al Youtuber.
     *
     * @param loginYoutuber usuario que da "me gusta"
     * @param idVideo vídeo al que se da "me gusta"
     */
    void likeVideo(
            String loginYoutuber,
            Integer idVideo);


    /**
     * Elimina un vídeo de la lista de vídeos que le gustan
     * al Youtuber.
     *
     * @param loginYoutuber usuario que quita el "me gusta"
     * @param idVideo vídeo al que se quita el "me gusta"
     */
    void dislikeVideo(
            String loginYoutuber,
            Integer idVideo);


    /**
     * Denuncia a otro usuario.
     *
     * @param loginDenunciante usuario que realiza la denuncia
     * @param loginDenunciado usuario que recibe la denuncia
     */
    void denunciarUsuario(
            String loginDenunciante,
            String loginDenunciado);


    /**
     * Retira una denuncia realizada anteriormente.
     *
     * @param loginDenunciante usuario que realizó la denuncia
     * @param loginDenunciado usuario cuya denuncia se quiere retirar
     */
    void quitardenunciaUsuario(
            String loginDenunciante,
            String loginDenunciado);

}