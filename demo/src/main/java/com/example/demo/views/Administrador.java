package com.example.demo.views;

import java.util.List;

import com.example.demo.domain.Video;
import com.example.demo.service.iAdministrador;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@Route("Administrador")
@RolesAllowed("ROLE_ADMINISTRADOR")

public class Administrador extends Registrado {

    private final iAdministrador iAdministrador;

    private Usuariosdenunciados usuariosDenunciados;

    public Administrador(iAdministrador iAdministrador) {
        super(iAdministrador);
        this.iAdministrador = iAdministrador;
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        UsuariosDenunciados();
    }

    private void UsuariosDenunciados() {
       List<com.example.demo.domain.Youtuber> denunciados =
                iAdministrador.buscarDenunciados();

        usuariosDenunciados =
                new Usuariosdenunciados(denunciados);

        body.add(usuariosDenunciados);
    }
 

	@Override
	protected void UltimosVideos() {
		 List<Video> ultimosVideos =
                iAdministrador.getAllVideos();

        this._ultimosVideos =
                new UltimosVideos(ultimosVideos);

        body.add(this._ultimosVideos);
	}

}