package com.example.demo.services;

import java.util.List;

import com.example.demo.tables.Video;

public interface iAdministrador extends iRegistrado {

    void eliminarComentario(Integer idComentario);

    List<com.example.demo.tables.Youtuber> buscarDenunciados();

    List<Video> getAllVideos();

    void borrarVideo(Integer idVideo);

    void bloquearUsuario(String idYoutuber);

    void desbloquearUsuario(String idYoutuber);

}