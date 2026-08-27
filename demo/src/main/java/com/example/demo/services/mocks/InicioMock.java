package com.example.demo.services.mocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.example.demo.services.interfaces.iInicio;
import com.example.demo.tables.Video;
import com.example.demo.tables.Youtuber;

@Service

@Primary
public class InicioMock implements iInicio {

    protected Set<Video> videos;
    protected Set<Youtuber> youtubers;

    public InicioMock(DatosMock datos) {

         
        this.videos = datos.videos;
        this.youtubers = datos.youtubers;

    }

    @Override
    public List<Video> buscar(String texto) {

        if (texto == null || texto.isBlank()) {
            return new ArrayList<>();
        }

        String textoBusqueda = texto.toLowerCase();

        return videos.stream()
                .filter(video -> video.getTitulo() != null &&
                        video.getTitulo()
                                .toLowerCase()
                                .contains(textoBusqueda))
                .toList();
    }

    @Override
    public Video findVideoById(Integer idVideo) {

        if (idVideo == null) {
            return null;
        }

        return videos.stream()
                .filter(video -> video.getId() == idVideo)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Youtuber findYoutuberById(
            String loginYoutuber) {

        if (loginYoutuber == null) {
            return null;
        }

        return youtubers.stream()
                .filter(youtuber -> loginYoutuber.equals(
                        youtuber.getLogin()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Video> getUltimosVideos() {

        /*
         * Para el prototipo simplemente devolvemos
         * los vídeos preparados como escenario.
         */
        return  new ArrayList<>(videos);
    }

    @Override
    public List<Video> getVideosRelacionados(
            Integer idVideo) {

        if (idVideo == null) {
            return new ArrayList<>();
        }

        Video video = findVideoById(idVideo);

        if (video == null) {
            return new ArrayList<>();
        }

        /*
         * Simulamos que los vídeos relacionados son
         * otros vídeos publicados por el mismo Youtuber.
         */
        Youtuber youtuber = video.getEs_de();

        if (youtuber == null) {
            return new ArrayList<>();
        }

        return videos.stream()
                .filter(v -> v.getEs_de() == youtuber &&
                        v.getId() != video.getId())
                .toList();
    }
}