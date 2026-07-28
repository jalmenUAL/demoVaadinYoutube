package com.example.demo.views;

import java.io.InputStream;

import com.example.demo.patterns.BaseView;
import com.example.demo.services.iNoLogueado;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("Registrar")
@AnonymousAllowed
public class Registrar extends BaseView {

    public NoLogueado _noLogueado;
    public ServidordeCorreo _servidordeCorreo;

    public iNoLogueado _iNoLogueado;


    private EmailField login;
    private TextField password;
    private Image avatar;
    private Image imagenDeFondo;

    private Button registrarButton;


    public Registrar(iNoLogueado iNoLogueado) {

        this._iNoLogueado = iNoLogueado;

    }


    @Override
    protected void build() {

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(
                JustifyContentMode.CENTER
        );
        setSpacing(true);



        H1 cabecera =
                new H1("Crear nueva cuenta");

        cabecera.getStyle()
                .set("color", "#2c3e50");


        add(cabecera);



        VerticalLayout datosLayout =
                new VerticalLayout();

        datosLayout.setAlignItems(
                Alignment.CENTER
        );


        login =
                new EmailField("Login");


        password =
                new TextField("Password");


        datosLayout.add(
                login,
                password
        );



        VerticalLayout avatarLayout =
                new VerticalLayout();

        avatarLayout.setAlignItems(
                Alignment.CENTER
        );


        Label avatarLabel =
                new Label("Avatar");


        avatar =
                new Image();

        avatar.setMaxWidth("300px");


        MemoryBuffer buffer =
                new MemoryBuffer();


        Upload upload =
                new Upload(buffer);


        upload.setAcceptedFileTypes(
                "image/jpeg",
                "image/png",
                "image/gif"
        );


        upload.addSucceededListener(
                event -> {

                    InputStream inputStream =
                            buffer.getInputStream();


                    StreamResource resource =
                            new StreamResource(
                                    event.getFileName(),
                                    () -> inputStream
                            );


                    avatar.setSrc(resource);

                }
        );


        avatarLayout.add(
                avatarLabel,
                upload,
                avatar
        );



        VerticalLayout fondoLayout =
                new VerticalLayout();


        fondoLayout.setAlignItems(
                Alignment.CENTER
        );


        Label fondoLabel =
                new Label("Imagen de fondo");


        imagenDeFondo =
                new Image();


        imagenDeFondo.setMaxWidth("300px");


        MemoryBuffer buffer2 =
                new MemoryBuffer();


        Upload upload2 =
                new Upload(buffer2);


        upload2.setAcceptedFileTypes(
                "image/jpeg",
                "image/png",
                "image/gif"
        );


        upload2.addSucceededListener(
                event -> {

                    InputStream inputStream =
                            buffer2.getInputStream();


                    StreamResource resource =
                            new StreamResource(
                                    event.getFileName(),
                                    () -> inputStream
                            );


                    imagenDeFondo.setSrc(resource);

                }
        );


        fondoLayout.add(
                fondoLabel,
                upload2,
                imagenDeFondo
        );



        HorizontalLayout imagenesLayout =
                new HorizontalLayout(
                        avatarLayout,
                        fondoLayout
                );


        imagenesLayout.setAlignItems(
                Alignment.START
        );

        imagenesLayout.setSpacing(true);



        registrarButton =
                new Button(
                        "Registrar",
                        new Icon(VaadinIcon.USER_CHECK)
                );


        registrarButton.addThemeVariants(
                ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_SUCCESS
        );



        VerticalLayout contenido =
                new VerticalLayout(
                        datosLayout,
                        imagenesLayout,
                        registrarButton
                );


        contenido.setAlignItems(
                Alignment.CENTER
        );

        contenido.setSpacing(true);
        contenido.setPadding(true);



        add(contenido);

    }



    @Override
    protected void bindEvents() {

        registrarButton.addClickListener(
                event -> Registrar()
        );

    }



    @Override
    protected void configureNavigation() {

        // No necesita navegación inicial

    }



    public void Registrar() {

        _iNoLogueado.registrar(
                login.getValue(),
                password.getValue(),
                avatar.getSrc(),
                imagenDeFondo.getSrc()
        );


        UI.getCurrent()
                .navigate(
                        NoLogueado.class
                );

    }



    public void EnviarCorreo() {

        Notification.show(
                "El envío de correo está deshabilitado"
        );

    }

}