package com.example.demo.factories;

import java.util.Set;

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
    public VerComentarios createVerComentarios(Set<Comentario> comentarios, int idvideo, ViewFactoryProvider viewFactory) {
        return new VerComentariosdeAdministrador(_iAdministrador, comentarios, idvideo, viewFactory);
    }
    @Override
    public Class<? extends UltimosVideos_item> createGaleriaItem() {
        return UltimosVideosdeAdministrador_item.class;
    }
    @Override
    public VerComentarios_item createVerComentariosItem(Comentario comentario, ViewFactoryProvider viewFactory) {
        return new VerComentariosdeAdministrador_item(_iAdministrador, comentario, viewFactory);
    }

       

}
