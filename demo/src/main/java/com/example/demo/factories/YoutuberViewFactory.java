package com.example.demo.factories;

import com.example.demo.tables.Comentario;
import com.example.demo.tables.Video;
import com.example.demo.views.Perfil;
import com.example.demo.views.UltimosVideos_item;

public class YoutuberViewFactory implements ViewFactory {

     

    @Override
    public Class<? extends com.example.demo.views.VerVideo> createVideo() {
        return com.example.demo.views.VerVideo.class;
    }

    @Override
    public Class<? extends com.example.demo.views.PerfilAjeno> createPerfilAjeno(String login) {
        return com.example.demo.views.PerfilAjeno.class;
    }

    @Override
    public Class<? extends com.example.demo.views.VerComentarios> createVerComentarios() {
        return com.example.demo.views.VerComentarios.class;
    }

    @Override
    public UltimosVideos_item createGaleriaItem(Video video) {
        return new UltimosVideos_item(video);
    }
    @Override
    public com.example.demo.views.VerComentarios_item createComentarioItem(Comentario comentario) {
        return new com.example.demo.views.VerComentariosdeYoutuber_item(comentario);
    }

    @Override
    public Class<? extends Perfil> createPerfilView(String idYoutuber) {
        return com.example.demo.views.PerfilAjenodeYoutuber.class;
    }
}
