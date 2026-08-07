package com.example.demo.views;

import com.example.demo.factories.ViewFactory;
import com.example.demo.services.iInicio;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("PerfilAjeno")
@AnonymousAllowed
public class PerfilAjeno extends Perfil {

	public PerfilAjeno(iInicio iInicio, ViewFactory viewFactory) {
		super(iInicio, viewFactory);
	}
	 
 
	 
}