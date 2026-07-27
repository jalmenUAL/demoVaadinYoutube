package com.example.demo.services;

import com.example.demo.tables.Video;

public interface iYoutuber extends iRegistrado {

    Video findVideoById(int idVideo);

    void publicarVideo(String loginYoutuber, String titulo, String url);

    void publicarComentario(String loginYoutuber, String idVideo, String contenido);

    void actualizarConfiguracion(String loginYoutuber, String password, String avatar, String imagenFondo);

    void seguirUsuario(String loginSeguidor, String loginSeguido);

    void dejardeseguirUsuario(String loginSeguidor, String loginSeguido);

    void likeVideo(String loginYoutuber, Integer idVideo);

    void dislikeVideo(String loginYoutuber, Integer idVideo);

    void denunciarUsuario(String loginDenunciante, String loginDenunciado);

    void quitardenunciaUsuario(String loginDenunciante, String loginDenunciado);

}