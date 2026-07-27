package com.example.demo.services;

import java.util.List;
import java.util.Set;

import com.example.demo.tables.Video;
import com.example.demo.tables.Youtuber;

public interface iInicio {

    List<Video> buscar(String texto);

    Video findVideoById(Integer idVideo);

    Youtuber findYoutuberById(String loginYoutuber);

    List<Video> getUltimosVideos();

    List<Video> getVideosRelacionados(Integer idVideo);

}