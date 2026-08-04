package com.example.demo.views;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.patterns.BaseView;
import com.example.demo.services.iYoutuber;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@Route("PublicarVideo")
@RolesAllowed("ROLE_YOUTUBER")
public class PublicarVideo extends BaseView {

    public PerfilPropio _perfilPropio;

    private final iYoutuber _iYoutuber;

    private TextField introduzcaLaUrl;
    private TextField introduzcaEltitulo;

    private Button publicarButton;


    public PublicarVideo(iYoutuber iYoutuber) {
        super();
        this._iYoutuber = iYoutuber;
        	 


    }


    @Override
    protected void build() {

        setWidthFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSpacing(true);
        setPadding(true);


        H2 titulo =
                new H2("📹 Publicar nuevo video");

        titulo.getStyle()
                .set("color", "#2c3e50");


        introduzcaEltitulo =
                new TextField("Título del Video");

        introduzcaLaUrl =
                new TextField("URL del Video");


        introduzcaEltitulo.setPlaceholder(
                "Ej. Cómo cocinar arroz"
        );

        introduzcaLaUrl.setPlaceholder(
                "Ej. https://youtube.com/..."
        );


        introduzcaEltitulo.setWidth("60%");
        introduzcaLaUrl.setWidth("60%");


        publicarButton =
                new Button("Publicar Video");


        publicarButton.addThemeVariants(
                ButtonVariant.LUMO_PRIMARY
        );


        publicarButton.getStyle()
                .set("border-radius", "8px")
                .set("font-weight", "bold")
                .set("margin-top", "10px");


        add(
                titulo,
                introduzcaEltitulo,
                introduzcaLaUrl,
                publicarButton
        );

    }


    @Override
    protected void bindEvents() {

        publicarButton.addClickListener(
                e -> PublicarVideo()
        );

    }

    public void PublicarVideo() {

        Authentication auth =
                SecurityContextHolder.getContext()
                        .getAuthentication();


        if (auth == null
                || !auth.isAuthenticated()
                || auth.getPrincipal().equals("anonymousUser")) {

            throw new RuntimeException(
                    "Usuario no autenticado"
            );

        }


        com.example.demo.tables.Youtuber usuario =
                (com.example.demo.tables.Youtuber) auth.getPrincipal();


        _iYoutuber.publicarVideo(
                usuario.getLogin(),
                introduzcaEltitulo.getValue(),
                introduzcaLaUrl.getValue()
        );


        UI.getCurrent()
                .getPage()
                .getHistory()
                .back();

    }

}
