package com.example.demo.views;

import java.util.List;

import com.example.demo.services.iAdministrador;
import com.example.demo.tables.Video;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@Route("Administrador")
@RolesAllowed("ROLE_ADMINISTRADOR")

public class Administrador extends Registrado {

    protected final iAdministrador iAdministrador;

    protected Usuariosdenunciados _usuariosdenunciados;

    public Administrador(iAdministrador iAdministrador) {
        super(iAdministrador);
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

        this._ultimosVideos = new UltimosVideos(ultimosVideos);

        body.add(this._ultimosVideos);
    }

    private void Usuariosdenunciados() {
        List<com.example.demo.tables.Youtuber> denunciados = iAdministrador.buscarDenunciados();

        _usuariosdenunciados = new Usuariosdenunciados(denunciados);

        body.add(_usuariosdenunciados);
    }

}