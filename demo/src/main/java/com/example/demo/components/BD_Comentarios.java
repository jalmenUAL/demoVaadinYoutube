package com.example.demo.components;

import java.util.Vector;

import org.springframework.stereotype.Service;

import com.example.demo.facade.BDPrincipal;
import com.example.demo.repositories.RepositorioComentario;
import com.example.demo.tables.Comentario;
import com.example.demo.tables.Video;
import com.example.demo.tables.Youtuber;

@Service

public class BD_Comentarios {

    public BDPrincipal _en;
    public Vector<Comentario> _comentarios = new Vector<Comentario>();
    private RepositorioComentario repository;

    public BD_Comentarios(RepositorioComentario comentariosRepository) {
        repository = comentariosRepository;

    }

    public void publicarComentario(Youtuber usuario, Video video, String contenido) {
        Comentario c = new Comentario();
        c.setEscrito_por(usuario);
        c.setTexto(contenido);
        c.setSobre(video);
        repository.save(c);

    }

    public void eliminarComentario(Integer idComentario) {
        repository.deleteById(idComentario);

    }

}