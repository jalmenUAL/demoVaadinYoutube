package com.example.demo.factories;

import org.springframework.stereotype.Component;

import com.example.demo.services.iAdministrador;
import com.example.demo.tables.Comentario;
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

 @Component
public class AdministradorViewFactory implements ViewFactory {

    private iAdministrador _iAdministrador;

    public AdministradorViewFactory(iAdministrador iAdministrador) {
        this._iAdministrador = iAdministrador;
    }

    @Override
    public Class<? extends VerVideo> createVideo() {
         return VerVideodeAdministrador.class;
    }

    @Override
    public Class<? extends PerfilAjeno> createPerfilAjeno() {
        return PerfilAjenodeAdministrador.class;
    }

    @Override
    public Class<? extends VerComentarios> createVerComentarios() {
        return VerComentariosdeAdministrador.class;
    }
    @Override
    public Class<? extends UltimosVideos_item> createGaleriaItem() {
        return UltimosVideosdeAdministrador_item.class;
    }
    @Override
    public VerComentarios_item createComentarioItem(Comentario comentario) {
        return new VerComentariosdeAdministrador_item(_iAdministrador, comentario, this);
    }

       

}
