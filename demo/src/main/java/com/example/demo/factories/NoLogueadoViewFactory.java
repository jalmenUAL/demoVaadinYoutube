package com.example.demo.factories;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.example.demo.tables.Comentario;
import com.example.demo.views.UltimosVideos_item;
import com.example.demo.views.VerComentarios;
import com.example.demo.views.VerComentarios_item;
import com.example.demo.views.VerComentariosdeYoutuber;

 
@Component
public class NoLogueadoViewFactory implements ViewFactory {

     

    @Override
    public Class<? extends com.example.demo.views.VerVideo> createVideo() {
        return com.example.demo.views.VerVideo.class;
    }

    @Override
    public Class<? extends com.example.demo.views.PerfilAjeno> createPerfilAjeno() {
        return com.example.demo.views.PerfilAjeno.class;
    }

    @Override
    public com.example.demo.views.VerComentarios createVerComentarios(Set<Comentario> comentarios, int idvideo, ViewFactoryProvider viewFactory) {
        return new VerComentarios(comentarios, idvideo, viewFactory);
    }

    @Override
    public Class<? extends UltimosVideos_item> createGaleriaItem() {
        return  com.example.demo.views.UltimosVideos_item.class;
    }
    public VerComentarios_item createVerComentariosItem(Comentario comentario, ViewFactoryProvider viewFactory) {
        return new com.example.demo.views.VerComentarios_item(comentario, viewFactory);
    }

 

    
}
