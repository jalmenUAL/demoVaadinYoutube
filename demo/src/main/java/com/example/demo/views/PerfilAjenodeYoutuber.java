package com.example.demo.views;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.factories.ViewFactory;
import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.services.iYoutuber;
import com.example.demo.tables.Youtuber;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.Route;

@Route("PerfilAjenodeYoutuber")
public class PerfilAjenodeYoutuber extends PerfilAjeno {

    private final iYoutuber iYoutuber;

    private Button btnSeguir;
    private Button btnDenunciar;

    public PerfilAjenodeYoutuber(iYoutuber iYoutuber, ViewFactoryProvider viewFactory) {
        super(iYoutuber, viewFactory);
        this.iYoutuber = iYoutuber;
    }

    @Override
    protected void build(String parameter) {

        super.build(parameter);
 
        btnSeguir = new Button();
        btnDenunciar = new Button();

        btnSeguir.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnDenunciar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        topLayout.add(btnSeguir, btnDenunciar);
    }

    @Override
    protected void bindEvents() {

        super.bindEvents();

        btnSeguir.addClickListener(e -> Seguir());
        btnDenunciar.addClickListener(e -> Denunciar());
    }

    @Override
    public void setParameter(BeforeEvent event, String parameter) {

        super.setParameter(event, parameter);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Youtuber youtuber = (Youtuber) auth.getPrincipal();

        if (_usuario.getSeguido_por().contains(youtuber)) {
            btnSeguir.setText("Dejar de seguir");
        } else {
            btnSeguir.setText("Seguir");
        }

        if (_usuario.getDenunciado_por().contains(youtuber)) {
            btnDenunciar.setText("Quitar denuncia");
        } else {
            btnDenunciar.setText("Denunciar");
        }

        if (_usuario.getLogin().equals(youtuber.getLogin())) {
            btnSeguir.setVisible(false);
            btnDenunciar.setVisible(false);
        }

    }

    public void Seguir() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Youtuber seguidor = (Youtuber) auth.getPrincipal();

        if (btnSeguir.getText().equals("Seguir")) {

            iYoutuber.seguirUsuario(
                    _usuario.getLogin(),
                    seguidor.getLogin());

            btnSeguir.setText("Dejar de seguir");

        } else {

            iYoutuber.dejardeseguirUsuario(
                    _usuario.getLogin(),
                    seguidor.getLogin());

            btnSeguir.setText("Seguir");
        }
    }

    public void Denunciar() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Youtuber seguidor = (Youtuber) auth.getPrincipal();

        if (btnDenunciar.getText().equals("Denunciar")) {

            iYoutuber.denunciarUsuario(
                    _usuario.getLogin(),
                    seguidor.getLogin());

            btnDenunciar.setText("Quitar denuncia");

        } else {

            iYoutuber.quitardenunciaUsuario(
                    _usuario.getLogin(),
                    seguidor.getLogin());

            btnDenunciar.setText("Denunciar");
        }
    }
}