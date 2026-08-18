package com.example.demo.factories;

import org.springframework.stereotype.Component;

import com.example.demo.tables.Comentario;
import com.example.demo.views.UltimosVideos_item;
import com.example.demo.views.VerComentarios_item;

 
@Component
public class YoutuberViewFactory implements ViewFactory {

     

    @Override
    public Class<? extends com.example.demo.views.VerVideo> createVideo() {
        return com.example.demo.views.VerVideo.class;
    }

    @Override
    public Class<? extends com.example.demo.views.PerfilAjeno> createPerfilAjeno() {
        return com.example.demo.views.PerfilAjeno.class;
    }

    @Override
    public Class<? extends com.example.demo.views.VerComentarios> createVerComentarios() {
        return com.example.demo.views.VerComentarios.class;
    }

    @Override
    public Class<? extends UltimosVideos_item> createGaleriaItem() {
        return com.example.demo.views.UltimosVideos_item.class;
    }
    @Override
    public VerComentarios_item createComentarioItem(Comentario comentario) {
        return new com.example.demo.views.VerComentariosdeYoutuber_item(comentario, this);
    }

   
}
