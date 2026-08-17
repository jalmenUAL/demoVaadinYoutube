package com.example.demo.views;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.factories.AdministradorViewFactory;
import com.example.demo.factories.ViewFactory;
import com.example.demo.services.iAdministrador;
import com.example.demo.tables.Video;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@Route("Administrador")
@RolesAllowed("ROLE_ADMINISTRADOR")
@Component
public class Administrador extends Registrado {

    protected final iAdministrador iAdministrador;

    protected Usuariosdenunciados _usuariosdenunciados;

    public Administrador(iAdministrador iAdministrador, AdministradorViewFactory viewFactory) {
        super(iAdministrador, viewFactory);
        this.iAdministrador = iAdministrador;
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        Usuariosdenunciados();
    }

    @Override
    protected void UltimosVideos() {
        List<Video> ultimosVideos = iAdministrador.getAllVideos();
        _ultimosVideos = new UltimosVideos(ultimosVideos, viewFactory);
        body.add(_ultimosVideos);
    }

    private void Usuariosdenunciados() {
        List<com.example.demo.tables.Youtuber> denunciados = iAdministrador.buscarDenunciados();
        _usuariosdenunciados = new Usuariosdenunciados(denunciados);
        body.add(_usuariosdenunciados);
    }

    

}