package com.example.demo.views;

import java.io.InputStream;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.patterns.BaseView;
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
public class Configuracion extends BaseView {

        private final iYoutuber _iYoutuber;

        private Image imagenDeFondo;
        private Image avatar;
        private TextField password;
        private Button actualizar;

        private MemoryBuffer avatarBuffer;
        private MemoryBuffer fondoBuffer;

        public Configuracion(iYoutuber iYoutuber) {
                super();
                this._iYoutuber = iYoutuber;
                initView();
        }

        @Override
        protected void build() {
                setSizeFull();
                setAlignItems(Alignment.CENTER);
                setJustifyContentMode(JustifyContentMode.CENTER);
                setSpacing(true);
                H1 heading = new H1("Configuración");
                heading.getStyle().set("color", "#2c3e50");

                VerticalLayout datosLayout = new VerticalLayout();
                datosLayout.setAlignItems(Alignment.CENTER);

                password = new TextField("New Password");
                datosLayout.add(password);

                VerticalLayout avatarLayout = new VerticalLayout();
                avatarLayout.setAlignItems(Alignment.CENTER);

                Span avatarLabel = new Span("Avatar");
                avatar = new Image();
                avatar.setMaxWidth("300px");

                avatarBuffer = new MemoryBuffer();
                Upload uploadAvatar = new Upload(avatarBuffer);
                uploadAvatar.setAcceptedFileTypes(
                                "image/jpeg",
                                "image/png",
                                "image/gif");

                avatarLayout.add(avatarLabel, uploadAvatar, avatar);

                VerticalLayout fondoLayout = new VerticalLayout();
                fondoLayout.setAlignItems(Alignment.CENTER);

                Span fondoLabel = new Span("Imagen de fondo");
                imagenDeFondo = new Image();
                imagenDeFondo.setMaxWidth("300px");

                fondoBuffer = new MemoryBuffer();
                Upload uploadFondo = new Upload(fondoBuffer);
                uploadFondo.setAcceptedFileTypes(
                                "image/jpeg",
                                "image/png",
                                "image/gif");

                fondoLayout.add(fondoLabel, uploadFondo, imagenDeFondo);

                HorizontalLayout imagenesLayout = new HorizontalLayout(avatarLayout, fondoLayout);
                imagenesLayout.setAlignItems(Alignment.START);
                imagenesLayout.setSpacing(true);

                actualizar = new Button(
                                "Actualizar",
                                new Icon(VaadinIcon.REFRESH));

                actualizar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

                VerticalLayout contenido = new VerticalLayout(
                                datosLayout,
                                imagenesLayout,
                                actualizar);

                contenido.setAlignItems(Alignment.CENTER);
                contenido.setSpacing(true);
                contenido.setPadding(true);

                add(heading, contenido);

                uploadAvatar.setId("uploadAvatar");
                uploadFondo.setId("uploadFondo");
        }

        @Override
        protected void bindEvents() {

                Upload uploadAvatar = (Upload) getChildren()
                                .flatMap(component -> component.getChildren())
                                .filter(component -> component instanceof Upload
                                                && "uploadAvatar".equals(component.getId().orElse("")))
                                .findFirst()
                                .orElse(null);

                Upload uploadFondo = (Upload) getChildren()
                                .flatMap(component -> component.getChildren())
                                .filter(component -> component instanceof Upload
                                                && "uploadFondo".equals(component.getId().orElse("")))
                                .findFirst()
                                .orElse(null);

                if (uploadAvatar != null) {
                        uploadAvatar.addSucceededListener(event -> {

                                InputStream inputStream = avatarBuffer.getInputStream();

                                StreamResource resource = new StreamResource(
                                                event.getFileName(),
                                                () -> inputStream);

                                avatar.setSrc(resource);

                        });
                }

                if (uploadFondo != null) {
                        uploadFondo.addSucceededListener(event -> {

                                InputStream inputStream = fondoBuffer.getInputStream();

                                StreamResource resource = new StreamResource(
                                                event.getFileName(),
                                                () -> inputStream);

                                imagenDeFondo.setSrc(resource);

                        });
                }

                actualizar.addClickListener(event -> {
                        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

                        com.example.demo.tables.Youtuber usuario = (com.example.demo.tables.Youtuber) auth
                                        .getPrincipal();

                        _iYoutuber.actualizarConfiguracion(
                                        usuario.getLogin(),
                                        password.getValue(),
                                        avatar.getSrc(),
                                        imagenDeFondo.getSrc());

                        UI.getCurrent().getPage().getHistory().back();
                });
        }

}