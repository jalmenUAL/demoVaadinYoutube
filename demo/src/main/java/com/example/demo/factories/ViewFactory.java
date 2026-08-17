package com.example.demo.factories;

import com.example.demo.tables.Comentario;
import com.example.demo.views.GaleradeVideos_item;
import com.example.demo.views.PerfilAjeno;
import com.example.demo.views.VerComentarios;
import com.example.demo.views.VerComentarios_item;
import com.example.demo.views.VerVideo;

public interface ViewFactory {

    Class<? extends VerVideo> createVideo();

    Class<? extends PerfilAjeno> createPerfilAjeno();

    Class<? extends VerComentarios> createVerComentarios();

    Class<? extends GaleradeVideos_item> createGaleriaItem();

    VerComentarios_item createComentarioItem(Comentario comentario);

 

    
}
