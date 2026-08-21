package com.example.demo.factories;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.example.demo.tables.Comentario;
import com.example.demo.views.UltimosVideos_item;
import com.example.demo.views.VerComentarios_item;
import com.example.demo.views.VerComentariosdeYoutuber;

 
@Component
public class YoutuberViewFactory implements ViewFactory {

     

    @Override
    public Class<? extends com.example.demo.views.VerVideo> createVideo() {
        return com.example.demo.views.VerVideodeYoutuber.class;
    }

    @Override
    public Class<? extends com.example.demo.views.PerfilAjeno> createPerfilAjeno() {
        return com.example.demo.views.PerfilAjenodeYoutuber.class;
    }

    @Override
    public com.example.demo.views.VerComentarios createVerComentarios(Set<Comentario> comentarios, int idvideo) {
        return new VerComentariosdeYoutuber(comentarios, idvideo, this);
    }

    @Override
    public Class<? extends UltimosVideos_item> createGaleriaItem() {
        return com.example.demo.views.UltimosVideosdeYoutuber_item.class;
    }
    @Override
    public VerComentarios_item createVerComentariosItem(Comentario comentario) {
        return new com.example.demo.views.VerComentariosdeYoutuber_item(comentario, this);
    }

   
}
