package com.example.demo.views;

import java.io.InputStream;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.services.iYoutuber;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

import jakarta.annotation.security.RolesAllowed;

@Route("Configuracion")
@RolesAllowed("ROLE_YOUTUBER")
public class Configuracion extends VerticalLayout {
    public PerfilPropio _perfilPropio;
    private iYoutuber _iYoutuber;

    private Image imagenDeFondo;
    private Image avatar;
    private TextField password;

    /* Accede a la base de datos con iYoutuber */

    public Configuracion(iYoutuber iYoutuber) {
        _iYoutuber = iYoutuber;
       
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSpacing(true);

         
        H1 heading = new H1("Configuración");
        heading.getStyle().set("color", "#2c3e50");
        add(heading);

         
        VerticalLayout datosLayout = new VerticalLayout();
        datosLayout.setAlignItems(Alignment.CENTER);

        password = new TextField("New Password");
        datosLayout.add(password);

         
        VerticalLayout avatarLayout = new VerticalLayout();
        avatarLayout.setAlignItems(Alignment.CENTER);
        Span avatarLabel = new Span("Avatar");
        avatar = new Image();
        avatar.setMaxWidth("300px");
        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes("image/jpeg", "image/png", "image/gif");
        upload.addSucceededListener(event -> {
            InputStream inputStream = buffer.getInputStream();
            StreamResource resource = new StreamResource(event.getFileName(), () -> inputStream);
            avatar.setSrc(resource);
        });
        avatarLayout.add(avatarLabel, upload, avatar);

        // Layout para imagen de fondo
        VerticalLayout fondoLayout = new VerticalLayout();
        fondoLayout.setAlignItems(Alignment.CENTER);
        Span fondoLabel = new Span("Imagen de fondo");
        imagenDeFondo = new Image();
        imagenDeFondo.setMaxWidth("300px");
        MemoryBuffer buffer2 = new MemoryBuffer();
        Upload upload2 = new Upload(buffer2);
        upload2.setAcceptedFileTypes("image/jpeg", "image/png", "image/gif");
        upload2.addSucceededListener(event -> {
            InputStream inputStream = buffer2.getInputStream();
            StreamResource resource = new StreamResource(event.getFileName(), () -> inputStream);
            imagenDeFondo.setSrc(resource);
        });
        fondoLayout.add(fondoLabel, upload2, imagenDeFondo);

        
        HorizontalLayout imagenesLayout = new HorizontalLayout(avatarLayout, fondoLayout);
        imagenesLayout.setAlignItems(Alignment.START);
        imagenesLayout.setSpacing(true);

        
        Button actualizar = new Button("Actualizar", new Icon(VaadinIcon.REFRESH));
        actualizar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        actualizar.addClickListener(event -> {

            actualizar();
        });

       
        VerticalLayout contenido = new VerticalLayout(datosLayout, imagenesLayout, actualizar);
        contenido.setAlignItems(Alignment.CENTER);
        contenido.setSpacing(true);
        contenido.setPadding(true);

        add(contenido);
    }

    private void actualizar() {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            throw new RuntimeException("Usuario no autenticado");
        }

        com.example.demo.tables.Youtuber usuario = (com.example.demo.tables.Youtuber) auth.getPrincipal();

        _iYoutuber.actualizarConfiguracion(usuario.getLogin(), password.getValue(), avatar.getSrc(), imagenDeFondo.getSrc());
        
        UI.getCurrent().getPage().getHistory().back();

    }
}