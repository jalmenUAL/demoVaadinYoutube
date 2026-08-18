package com.example.demo.views;

import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import com.example.demo.factories.ViewFactory;
import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.services.iRegistrado;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.VaadinSession;

@Route("Registrado")

public abstract class Registrado extends Inicio {

    protected final iRegistrado iRegistrado;

    protected Button logoutButton;

    public Registrado(iRegistrado iRegistrado, ViewFactoryProvider viewFactory) {
        super(iRegistrado, viewFactory);
        this.iRegistrado = iRegistrado;
    }

     

    protected void Logout() {
        new SecurityContextLogoutHandler().logout(
                VaadinServletRequest.getCurrent()
                        .getHttpServletRequest(),
                null,
                null);

        VaadinSession.getCurrent().close();
        VaadinSession.getCurrent()
                .setAttribute("Registrado", null);

        getUI().ifPresent(
                ui -> ui.navigate("NoLogueado"));
    }

    @Override
    protected void build() {
        super.build();

        logoutButton = new Button(
                "Cerrar sesión",
                new Icon(VaadinIcon.SIGN_OUT));

        logoutButton.addThemeVariants(
                ButtonVariant.LUMO_ERROR);

        logoutButton.getStyle()
                .set("margin-left", "auto")
                .set("font-weight", "bold");

        header.add(logoutButton);
    }

    @Override
    protected void bindEvents() {
        

        logoutButton.addClickListener(e -> Logout());
    }
 

}