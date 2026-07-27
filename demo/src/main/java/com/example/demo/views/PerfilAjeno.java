package com.example.demo.views;

import com.example.demo.services.iInicio;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("PerfilAjeno")
@AnonymousAllowed
public class PerfilAjeno extends Perfil {

	public PerfilAjeno(iInicio iInicio) {
		super(iInicio);
	}
	public VerVideo _verVideo;
	public VerComentarios_item _verComentarios;
	public Youtubersseguidos_item _youtubersseguidos;
 
	 
}