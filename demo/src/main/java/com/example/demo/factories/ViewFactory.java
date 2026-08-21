package com.example.demo.factories;

import java.util.Set;

import com.example.demo.tables.Comentario;
import com.example.demo.views.GaleradeVideos_item;
import com.example.demo.views.PerfilAjeno;
import com.example.demo.views.VerComentarios;
import com.example.demo.views.VerComentarios_item;
import com.example.demo.views.VerVideo;

public interface ViewFactory {

    Class<? extends VerVideo> createVideo();

    Class<? extends PerfilAjeno> createPerfilAjeno();

    VerComentarios createVerComentarios(Set<Comentario> comentarios);

    Class<? extends GaleradeVideos_item> createGaleriaItem();

    VerComentarios_item createComentarioItem(Comentario comentario);

    VerComentarios createVerComentariosItem(Set<Comentario> comentarios);

 

    
}
