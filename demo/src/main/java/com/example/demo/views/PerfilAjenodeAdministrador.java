package com.example.demo.views;

import com.example.demo.services.iAdministrador;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.Route;

@Route("PerfilAjenodeAdministrador")
public class PerfilAjenodeAdministrador extends PerfilAjeno {
    private Button btnBloquear;

    iAdministrador iAdministrador;

    public PerfilAjenodeAdministrador(iAdministrador iAdministrador) {
        super(iAdministrador);
        this.iAdministrador = iAdministrador;

        btnBloquear = new Button("", e -> Bloquear());
        btnBloquear.addThemeVariants(ButtonVariant.LUMO_ERROR);
        topLayout.add(btnBloquear);

    }

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        // 👇 ejecuta la lógica de la clase padre
        super.setParameter(event, parameter);
        if (_usuario.getBloqueado()) {
            btnBloquear.setText("Quitar bloqueo");
        } else {
            btnBloquear.setText("Bloquear");
        }
    }

    public void Bloquear() {
        if (btnBloquear.getText().equals("Bloquear")) {
            btnBloquear.setText("Quitar Bloqueo");
            iAdministrador.bloquearUsuario(_usuario.getLogin());
        } else {
            btnBloquear.setText("Bloquear");
            iAdministrador.desbloquearUsuario(_usuario.getLogin());
        }

    }
}