package com.example.demo.services.interfaces;

import java.util.List;
import java.util.Set;

import com.example.demo.tables.Video;
import com.example.demo.tables.Youtuber;

/**
 * Interfaz que define las operaciones disponibles en la página
 * de inicio de la aplicación.
 *
 * <p>
 * Estas operaciones están relacionadas principalmente con la
 * consulta y visualización de contenidos.
 *
 * <p>
 * La interfaz no se preocupa de cómo se realizan estas operaciones.
 * Solamente define qué operaciones están disponibles.
 *
 * <p>
 * La implementación se encuentra en BDPrincipal.
 */
public interface iInicio {


    /**
     * Busca vídeos cuyo título coincida con el texto indicado.
     *
     * @param texto texto introducido por el usuario para realizar
     *              la búsqueda
     *
     * @return lista de vídeos que coinciden con la búsqueda
     */
    List<Video> buscar(String texto);


    /**
     * Busca un vídeo concreto utilizando su identificador.
     *
     * @param idVideo identificador del vídeo
     *
     * @return vídeo encontrado
     */
    Video findVideoById(Integer idVideo);


    /**
     * Busca un Youtuber mediante su login.
     *
     * <p>
     * Se utiliza, por ejemplo, para poder acceder al perfil
     * de un usuario desde la página de inicio.
     *
     * @param loginYoutuber login del Youtuber
     *
     * @return Youtuber encontrado
     */
    Youtuber findYoutuberById(String loginYoutuber);


    /**
     * Obtiene los últimos vídeos publicados.
     *
     * @return lista de los últimos vídeos
     */
    List<Video> getUltimosVideos();


    /**
     * Obtiene vídeos relacionados con un vídeo concreto.
     *
     * <p>
     * El criterio de relación se determina en la implementación.
     *
     * @param idVideo identificador del vídeo para el que se quieren
     *                buscar vídeos relacionados
     *
     * @return lista de vídeos relacionados
     */
    List<Video> getVideosRelacionados(Integer idVideo);

}