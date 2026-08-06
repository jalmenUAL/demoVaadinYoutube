package com.example.demo.factories;

import com.example.demo.facade.BDPrincipal;
import com.example.demo.services.iAdministrador;
import com.example.demo.tables.Comentario;
import com.example.demo.tables.Video;
import com.example.demo.views.Perfil;
import com.example.demo.views.PerfilAjeno;
import com.example.demo.views.PerfilAjenodeAdministrador;
import com.example.demo.views.UltimosVideos_item;
import com.example.demo.views.UltimosVideosdeAdministrador_item;
import com.example.demo.views.VerComentarios;
import com.example.demo.views.VerComentarios_item;
import com.example.demo.views.VerComentariosdeAdministrador;
import com.example.demo.views.VerComentariosdeAdministrador_item;
import com.example.demo.views.VerVideo;
import com.example.demo.views.VerVideodeAdministrador;

public class AdministradorViewFactory implements ViewFactory {

    private iAdministrador _iAdministrador;

    @Override
    public Class<? extends VerVideo> createVideo() {
         return VerVideodeAdministrador.class;
    }

    @Override
    public Class<? extends PerfilAjeno> createPerfilAjeno(String login) {
        return PerfilAjenodeAdministrador.class;
    }

    @Override
    public Class<? extends VerComentarios> createVerComentarios() {
        return VerComentariosdeAdministrador.class;
    }
    @Override
    public UltimosVideos_item createGaleriaItem(Video video) {
        return new UltimosVideosdeAdministrador_item(video);
    }
    @Override
    public VerComentarios_item createComentarioItem(Comentario comentario) {
        return new VerComentariosdeAdministrador_item(_iAdministrador, comentario);
    }

    @Override
    public Class<? extends Perfil> createPerfilView() {
        return PerfilAjenodeAdministrador.class;        
    }      

}
