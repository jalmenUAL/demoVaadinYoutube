package com.example.demo.facade;

import com.example.demo.repositories.RepositorioYoutuber;
import com.example.demo.services.interfaces.iAdministrador;
import com.example.demo.services.interfaces.iInicio;
import com.example.demo.services.interfaces.iNoLogueado;
import com.example.demo.services.interfaces.iRegistrado;
import com.example.demo.services.interfaces.iYoutuber;

import java.io.InputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.components.BD_Administradores;
import com.example.demo.components.BD_Comentarios;
import com.example.demo.components.BD_Videos;
import com.example.demo.components.BD_Youtubers;
import com.example.demo.tables.Comentario;
import com.example.demo.tables.Registrado;
import com.example.demo.tables.Video;
import com.example.demo.tables.Youtuber;

import jakarta.transaction.Transactional;

@Service
/**
 * Clase principal de acceso a la lógica de negocio de la aplicación.
 *
 * <p>
 * Esta clase implementa las diferentes interfaces de la aplicación
 * (iNoLogueado, iYoutuber, iAdministrador, iRegistrado e iInicio).
 *
 * <p>
 * Su función principal es actuar como una FACHADA:
 *
 *     Vista
 *       ↓
 *  BDPrincipal
 *       ↓
 * ┌─────┼──────────┬──────────────┐
 * ↓     ↓          ↓              ↓
 * BD_  BD_        BD_            BD_
 *Videos Comentarios Youtubers Administradores
 *
 * De esta forma, las vistas no necesitan conocer directamente
 * cómo se almacenan o modifican los datos.
 */
public class BDPrincipal
        implements iNoLogueado,
                   iYoutuber,
                   iAdministrador,
                   iRegistrado,
                   iInicio {

 
    /*
     * Clases encargadas de cada grupo de operaciones.
     *
     * BD_Videos       → operaciones relacionadas con vídeos.
     * BD_Comentarios  → operaciones relacionadas con comentarios.
     * BD_Youtubers    → operaciones relacionadas con usuarios.
     * BD_Administradores → operaciones relacionadas con administradores.
     */
    public BD_Videos _videos;
    public BD_Comentarios _comentarios;
    public BD_Youtubers _youtubers;
    public BD_Administradores _administradores;


    /**
     * Constructor.
     *
     * Spring proporciona estas dependencias automáticamente
     * mediante inyección de dependencias.
     */
    public BDPrincipal(
            BD_Videos videos,
            BD_Comentarios comentarios,
            BD_Youtubers youtubers,
            BD_Administradores administradores) {

        this._youtubers = youtubers;
        this._administradores = administradores;
        this._videos = videos;
        this._comentarios = comentarios;
     }


    // ============================================================
    // AUTENTICACIÓN
    // ============================================================

    /**
     * Comprueba si las credenciales corresponden a un administrador
     * o a un Youtuber.
     *
     * @return el usuario autenticado o null si las credenciales
     *         no son correctas.
     */
    @Override
    public Registrado Login(String login, String password) {

        /*
         * Primero comprobamos si es administrador.
         */
        Registrado administrador =
                _administradores.autenticar(login, password);

        if (administrador != null) {
            return administrador;
        }

        /*
         * Si no es administrador, comprobamos si es Youtuber.
         */
        return _youtubers.autenticar(login, password);
    }


    // ============================================================
    // VÍDEOS - CONSULTAS
    // ============================================================

    /**
     * Busca vídeos cuyo título coincida con el texto indicado.
     */
    @Override
    public List<Video> buscar(String texto) {
        return _videos.buscar(texto);
    }


    /**
     * Busca un vídeo por su identificador.
     */
    @Override
    public Video findVideoById(Integer idVideo) {
        return _videos.findVideoById(idVideo);
    }


    /**
     * Misma operación que la anterior, pero utilizando int.
     *
     * Se mantiene porque alguna de las interfaces generadas
     * puede exigir esta firma.
     */
    @Override
    public Video findVideoById(int idVideo) {
        return _videos.findVideoById(idVideo);
    }


    /**
     * Obtiene todos los vídeos.
     */
    @Override
    public List<Video> getAllVideos() {
        return _videos.getAllVideos();
    }


    /**
     * Obtiene los últimos vídeos.
     */
    @Override
    public List<Video> getUltimosVideos() {
        return _videos.getUltimosVideos();
    }


    /**
     * Busca vídeos relacionados con otro vídeo.
     */
    @Override
    public List<Video> getVideosRelacionados(Integer idVideo) {
        return _videos.getVideosRelacionados(idVideo);
    }


    // ============================================================
    // VÍDEOS - OPERACIONES
    // ============================================================

    /**
     * Publica un vídeo perteneciente a un Youtuber.
     *
     * La vista proporciona el login del usuario, pero BD_Videos
     * necesita el objeto Youtuber.
     *
     * Por eso primero buscamos al usuario y después delegamos
     * la operación.
     */
    @Override
    public void publicarVideo(
            String login,
            String titulo,
            String url) {

        Youtuber usuario =
                _youtubers.findYoutuberById(login);

        _videos.publicarVideo(
                usuario,
                titulo,
                url);
    }


    /**
     * Elimina un vídeo y las relaciones que dependen de él.
     *
     * @Transactional hace que todas las operaciones formen parte
     * de una única transacción de base de datos.
     *
     * Si alguna operación falla, Hibernate puede hacer rollback
     * de la transacción.
     */
    @Override
    @Transactional
    public void borrarVideo(Integer idVideo) {

        Video video =
                _videos.findVideoById(idVideo);

        /*
         * Antes de borrar el vídeo eliminamos las relaciones
         * de "Me gusta".
         *
         * Esto es necesario porque Youtuber y Video están
         * relacionados mediante una relación ManyToMany.
         */
        for (Object usuario : video.getLe_gusta_a()) {

            _youtubers.eliminarMeGusta(
                    (Youtuber) usuario,
                    video);
        }


        /*
         * También eliminamos los comentarios asociados al vídeo.
         *
         * En este caso recorremos los objetos Comentario y
         * eliminamos cada uno mediante BD_Comentarios.
         */
        for (Object comentario : video.getTiene_comentarios()) {

            _comentarios.eliminarComentario(
                    ((Comentario) comentario).getId());
        }


        /*
         * Una vez eliminadas las relaciones y los comentarios,
         * podemos eliminar el vídeo.
         */
        _videos.borrarVideo(idVideo);
    }


    // ============================================================
    // COMENTARIOS
    // ============================================================

    /**
     * Publica un comentario sobre un vídeo.
     *
     * Los parámetros llegan como String desde la vista,
     * por lo que el ID del vídeo debe convertirse a Integer.
     */
    @Override
    public void publicarComentario(
            String login,
            String idVideo,
            String contenido) {

        Youtuber usuario =
                _youtubers.findYoutuberById(login);

        Video video =
                _videos.findVideoById(
                        Integer.valueOf(idVideo));

        _comentarios.publicarComentario(
                usuario,
                video,
                contenido);
    }


    /**
     * Elimina un comentario.
     */
    @Override
    public void eliminarComentario(
            Integer idComentario) {

        _comentarios.eliminarComentario(
                idComentario);
    }


    // ============================================================
    // YOUTUBERS - CONSULTAS
    // ============================================================

    /**
     * Busca un Youtuber por su login.
     */
    @Override
    public Youtuber findYoutuberById(String login) {
        return _youtubers.findYoutuberById(login);
    }


    /**
     * Obtiene los Youtubers que han sido denunciados.
     */
    @Override
    public List<Youtuber> buscarDenunciados() {
        return _youtubers.buscarDenunciados();
    }


    // ============================================================
    // REGISTRO Y CONFIGURACIÓN
    // ============================================================

    /**
     * Registra un nuevo Youtuber.
     */
    @Override
    public void registrar(
            String login,
            String password,
            String avatarUrl,
            String fondoUrl) {

        _youtubers.registrar(
                login,
                password,
                avatarUrl,
                fondoUrl);
    }


    /**
     * Actualiza la configuración del usuario.
     *
     * Los InputStream corresponden a los archivos subidos
     * mediante Upload de Vaadin.
     */
    @Override
    public void actualizarConfiguracion(
            String login,
            String password,
            InputStream avatar,
            String avatarNombre,
            InputStream fondo,
            String fondoNombre) {

        _youtubers.actualizarConfiguracion(
                login,
                password,
                avatar,
                avatarNombre,
                fondo,
                fondoNombre);
    }


    // ============================================================
    // SEGUIR / DEJAR DE SEGUIR
    // ============================================================

    /**
     * Hace que un Youtuber siga a otro.
     */
    @Override
    public void seguirUsuario(
            String loginSeguido,
            String loginSeguidor) {

        _youtubers.seguirUsuario(
                loginSeguido,
                loginSeguidor);
    }


    /**
     * Elimina la relación de seguimiento.
     */
    @Override
    public void dejardeseguirUsuario(
            String loginSeguido,
            String loginSeguidor) {

        _youtubers.dejardeseguirUsuario(
                loginSeguido,
                loginSeguidor);
    }


    // ============================================================
    // DENUNCIAS
    // ============================================================

    /**
     * Denuncia a un Youtuber.
     */
    @Override
    public void denunciarUsuario(
            String loginDenunciante,
            String loginDenunciado) {

        _youtubers.denunciarUsuario(
                loginDenunciante,
                loginDenunciado);
    }


    /**
     * Elimina una denuncia.
     */
    @Override
    public void quitardenunciaUsuario(
            String loginDenunciante,
            String loginDenunciado) {

        _youtubers.quitardenunciaUsuario(
                loginDenunciante,
                loginDenunciado);
    }


    // ============================================================
    // ME GUSTA / NO ME GUSTA
    // ============================================================

    /**
     * Añade un "Me gusta" de un usuario a un vídeo.
     */
    @Override
    public void likeVideo(
            String loginYoutuber,
            Integer idVideo) {

        Youtuber usuario =
                _youtubers.findYoutuberById(
                        loginYoutuber);

        Video video =
                _videos.findVideoById(idVideo);

        _youtubers.likeVideo(
                usuario,
                video);
    }


    /**
     * Elimina el "Me gusta" de un usuario a un vídeo.
     */
    @Override
    public void dislikeVideo(
            String loginYoutuber,
            Integer idVideo) {

        Youtuber usuario =
                _youtubers.findYoutuberById(
                        loginYoutuber);

        Video video =
                _videos.findVideoById(idVideo);

        _youtubers.dislikeVideo(
                usuario,
                video);
    }


    // ============================================================
    // BLOQUEAR / DESBLOQUEAR
    // ============================================================

    /**
     * Bloquea a un Youtuber.
     */
    @Override
    public void bloquearUsuario(
            String loginYoutuber) {

        _youtubers.bloquearUsuario(
                loginYoutuber);
    }


    /**
     * Desbloquea a un Youtuber.
     */
    @Override
    public void desbloquearUsuario(
            String loginYoutuber) {

        _youtubers.desbloquearUsuario(
                loginYoutuber);
    }
}