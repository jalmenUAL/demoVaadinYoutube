package com.example.demo.views;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.services.iYoutuber;
import com.example.demo.tables.Youtuber;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.Route;

@Route("PerfilAjenodeYoutuber")
public class PerfilAjenodeYoutuber extends PerfilAjeno {

    private Button btnDenunciar;
    private Button btnSeguir;
    iYoutuber _iYoutuber;

    public PerfilAjenodeYoutuber(iYoutuber iYoutuber) {
        super(iYoutuber);
        this._iYoutuber = iYoutuber;

        btnDenunciar = new Button("Denunciar", e -> Denunciar());
        btnSeguir = new Button("Seguir", e -> Seguir());
        topLayout.add(btnSeguir, btnDenunciar);
    }

    @Override
    public void setParameter(BeforeEvent event, String parameter) {

        super.setParameter(event, parameter);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated()) {
            Youtuber youtuber = (Youtuber) auth.getPrincipal();

            btnSeguir.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            btnDenunciar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

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

    }

    public void Seguir() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            throw new RuntimeException("Usuario no autenticado");
        }

        com.example.demo.tables.Youtuber seguidor = (com.example.demo.tables.Youtuber) auth.getPrincipal();
        if (btnSeguir.getText().equals("Seguir")) {
            _iYoutuber.seguirUsuario(_usuario.getLogin(), seguidor.getLogin());
            btnSeguir.setText("Dejar de seguir"); // Cambiar el texto del botón

        } else {
            _iYoutuber.dejardeseguirUsuario(_usuario.getLogin(), seguidor.getLogin());
            btnSeguir.setText("Seguir"); // Cambiar el texto del botón

        }

    }

    public void Denunciar() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            throw new RuntimeException("Usuario no autenticado");
        }

        com.example.demo.tables.Youtuber seguidor = (com.example.demo.tables.Youtuber) auth.getPrincipal();
        if (btnDenunciar.getText().equals("Denunciar")) {
            _iYoutuber.denunciarUsuario(_usuario.getLogin(), seguidor.getLogin());
            btnDenunciar.setText("Quitar denuncia"); // Cambiar el texto del botón
        } else {
            _iYoutuber.quitardenunciaUsuario(_usuario.getLogin(), seguidor.getLogin());
            btnDenunciar.setText("Denunciar"); // Cambiar el texto del botón
        }

    }

}