package com.example.demo.facade;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.components.BD_Administradores;
import com.example.demo.components.BD_Comentarios;
import com.example.demo.components.BD_Videos;
import com.example.demo.components.BD_Youtubers;
import com.example.demo.services.iAdministrador;
import com.example.demo.services.iInicio;
import com.example.demo.services.iNoLogueado;
import com.example.demo.services.iRegistrado;
import com.example.demo.services.iYoutuber;
import com.example.demo.tables.Comentario;
import com.example.demo.tables.Registrado;
import com.example.demo.tables.Video;
import com.example.demo.tables.Youtuber;

import jakarta.transaction.Transactional;

@Service
public class BDPrincipal implements iNoLogueado, iYoutuber, iAdministrador, iRegistrado, iInicio {
    public BD_Videos _videos;
    public BD_Comentarios _comentarios;
    public BD_Youtubers _youtubers;
    public BD_Administradores _administradores;

    public BDPrincipal(BD_Videos videos, BD_Comentarios comentarios, BD_Youtubers youtubers,
            BD_Administradores administradores) {
        this._youtubers = youtubers;
        this._administradores = administradores;
        this._videos = videos;
        this._comentarios = comentarios;
    }

    @Override
    public Registrado Login(String login, String password) {
        if (_administradores.autenticar(login, password) != null) {
            return _administradores.autenticar(login, password);
        } else if (_youtubers.autenticar(login, password) != null) {
            return _youtubers.autenticar(login, password);
        }
        return null;
    }

    @Override
    public List<Video> buscar(String texto) {
        return _videos.buscar(texto);
    }

    @Override
    public void publicarVideo(String login, String titulo, String url) {
        Youtuber usuario = _youtubers.findYoutuberById(login);
        _videos.publicarVideo(usuario, titulo, url);
    }

    @Override
    public void publicarComentario(String login, String idVideo, String contenido) {
        Youtuber usuario = _youtubers.findYoutuberById(login);
        Video video = _videos.findVideoById(Integer.valueOf(idVideo));
        _comentarios.publicarComentario(usuario, video, contenido);
    }

    @Override
    public void registrar(String login, String password, String avatarUrl, String fondoUrl) {
        _youtubers.registrar(login, password, avatarUrl, fondoUrl);
    }

    @Override
    public void actualizarConfiguracion(String login, String password, String avatar, String imagenFondo) {
        _youtubers.actualizarConfiguracion(login, password, avatar, imagenFondo);
    }

    @Override
    public Youtuber findYoutuberById(String login) {
        return _youtubers.findYoutuberById(login);
    }

    @Override
    public Video findVideoById(Integer idVideo) {
        return _videos.findVideoById(idVideo);
    }

    @Override
    public List<Video> getAllVideos() {
        return _videos.getAllVideos();
    }

    @Override
    public List<Video> getUltimosVideos() {
        return _videos.getUltimosVideos();
    }

    @Override
    public List<Video> getVideosRelacionados(Integer idVideo) {
        return _videos.getVideosRelacionados(idVideo);
    }

    @Override
    @Transactional
    public void borrarVideo(Integer idVideo) {
        Video video = _videos.findVideoById(idVideo);
        _videos.borrarMeGustaDeTodosLosUsuarios(idVideo);
        for (Object comentario : video.getTiene_comentarios()) {
            _comentarios.eliminarComentario(((Comentario) comentario).getId());
        }

        _videos.borrarVideo(idVideo);
    }

    @Override
    public void eliminarComentario(Integer idComentario) {
        _comentarios.eliminarComentario(idComentario);
    }

    @Override
    public List<Youtuber> buscarDenunciados() {
        return _youtubers.buscarDenunciados();
    }

    @Override
    public void denunciarUsuario(String loginDenunciante, String loginDenunciado) {
        _youtubers.denunciarUsuario(loginDenunciante, loginDenunciado);
    }

    @Override
    public void quitardenunciaUsuario(String loginDenunciante, String loginDenunciado) {
        _youtubers.quitardenunciaUsuario(loginDenunciante, loginDenunciado);
    }

    @Override
    public void seguirUsuario(String loginSeguidor, String loginSeguido) {
        _youtubers.seguirUsuario(loginSeguidor, loginSeguido);
    }

    @Override
    public void dejardeseguirUsuario(String loginSeguidor, String loginSeguido) {
        _youtubers.dejardeseguirUsuario(loginSeguidor, loginSeguido);
    }

    @Override
    public void likeVideo(String loginYoutuber, Integer idVideo) {
        Youtuber usuario = _youtubers.findYoutuberById(loginYoutuber);
        Video video = _videos.findVideoById(idVideo);
        usuario.getLe_gusta().add(video);
    }

    @Override
    public void dislikeVideo(String loginYoutuber, Integer idVideo) {
        Youtuber usuario = _youtubers.findYoutuberById(loginYoutuber);
        Video video = _videos.findVideoById(idVideo);
        usuario.getLe_gusta().remove(video);
    }

    @Override
    public void bloquearUsuario(String loginYoutuber) {
        _youtubers.bloquearUsuario(loginYoutuber);
    }

    @Override
    public void desbloquearUsuario(String loginYoutuber) {
        _youtubers.desbloquearUsuario(loginYoutuber);
    }

    @Override
    public Video findVideoById(int idVideo) {
        return _videos.findVideoById(idVideo);
    }

}