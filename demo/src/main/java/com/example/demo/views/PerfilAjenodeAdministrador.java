package com.example.demo.views;

import com.example.demo.services.iAdministrador;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.Route;

@Route("PerfilAjenodeAdministrador")
public class PerfilAjenodeAdministrador extends PerfilAjeno {

    private final iAdministrador iAdministrador;

    private Button btnBloquear;

    public PerfilAjenodeAdministrador(iAdministrador iAdministrador) {
        super(iAdministrador);
        this.iAdministrador = iAdministrador;
    }

    @Override

    protected void build(String parameter) {

        super.build(parameter);

        btnBloquear = new Button();
        btnBloquear.addThemeVariants(ButtonVariant.LUMO_ERROR);

        topLayout.add(btnBloquear);
    }

    @Override
    protected void bindEvents() {

        super.bindEvents();

        btnBloquear.addClickListener(e -> Bloquear());
    }

    @Override
    public void setParameter(BeforeEvent event, String parameter) {

        super.setParameter(event, parameter);

        if (_usuario.getBloqueado()) {
            btnBloquear.setText("Quitar bloqueo");
        } else {
            btnBloquear.setText("Bloquear");
        }
    }

    public void Bloquear() {

        if (btnBloquear.getText().equals("Bloquear")) {

            btnBloquear.setText("Quitar bloqueo");

            iAdministrador.bloquearUsuario(_usuario.getLogin());

        } else {

            btnBloquear.setText("Bloquear");

            iAdministrador.desbloquearUsuario(_usuario.getLogin());

        }
    }
}