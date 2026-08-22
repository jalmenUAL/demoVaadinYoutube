package com.example.demo.components;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import org.springframework.stereotype.Service;

import com.example.demo.facade.BDPrincipal;
import com.example.demo.repositories.RepositorioVideo;
import com.example.demo.tables.Video;
import com.example.demo.tables.Youtuber;

@Service

public class BD_Videos {
    public BDPrincipal _en;
    public Vector<Video> _videos = new Vector<Video>();

    private RepositorioVideo videorepository;

    public BD_Videos(RepositorioVideo videorepository) {
        this.videorepository = videorepository;

    }

    public Video findVideoById(Integer idVideo) {
        return videorepository.findById(idVideo)
                .orElseThrow(() -> new RuntimeException("Video no encontrado"));
    }

    public java.util.List<Video> buscar(String texto) {
        List<Video> busqueda = videorepository.findAll();
        return busqueda.stream()
                .filter(video -> video.getTitulo().contains(texto))
                .toList();
    }

    public void publicarVideo(Youtuber usuario, String titulo, String url) {
        Video video = new Video();
        video.setTitulo(titulo);
        video.setUrl(url);
        video.setEs_de(usuario);
        videorepository.save(video);

    }

    public List<Video> getAllVideos() {
        return videorepository.findAll();
    }

    public java.util.List<Video> getUltimosVideos() {
        java.util.List<Video> UltimosVideos = videorepository.findAll();
        if (UltimosVideos.size() > 10) {
            UltimosVideos = UltimosVideos.subList(0, 10);
        }
        return UltimosVideos;
    }

    public List<Video> getVideosRelacionados(Integer idVideo) {
        Video videob = findVideoById(idVideo);
        List<String> palabras = Arrays.stream(videob.getTitulo().split("\s+"))
                .map(String::toLowerCase)
                .toList();
        List<Video> busqueda = videorepository.findAll();
        return busqueda.stream()
                // Excluir el propio video
                .filter(video -> !(video.getId() == videob.getId()))
                // Comparar títulos ignorando mayúsculas
                .filter(video -> {
                    String titulo = video.getTitulo().toLowerCase();
                    return palabras.stream().anyMatch(titulo::contains);
                })
                .toList();
    }

    public void borrarVideo(Integer idVideo) {
        videorepository.deleteById(idVideo);
    }

    public void borrarMeGustaDeTodosLosUsuarios(Integer idVideo) {
        Video video = findVideoById(idVideo);
        videorepository.findAll().forEach(usuario -> {
            if (usuario.getLe_gusta_a().contains(video)) {
                usuario.getLe_gusta_a().remove(video);
            }
        });
    }
 public void likeVideo(Youtuber usuario, Video video) {
         if (!video.getLe_gusta_a().contains(usuario)) {
       video.getLe_gusta_a().add(usuario);   
       videorepository.save(video);
        
    }
}

    public void dislikeVideo(Youtuber usuario, Video video) {
         if (video.getLe_gusta_a().contains(video)) {
        video.getLe_gusta_a().remove(usuario);
        videorepository.save(video);}
         }
}