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

    /*
     * Vista pública para crear una nueva cuenta.
     *
     * @AnonymousAllowed permite que un usuario NO autenticado
     * pueda acceder a esta vista.
     *
     * La vista hereda de BaseView, por lo que utiliza el patrón:
     *
     *      initView()
     *         ├── build()
     *         └── bindEvents()
     */

    public iNoLogueado _iNoLogueado;

    private EmailField login;
    private TextField password;

    private Image avatar;
    private Image imagenDeFondo;

    private Button registrarButton;

    public Registrar(iNoLogueado iNoLogueado) {

        super();

        /*
         * La vista utiliza la interfaz iNoLogueado para realizar
         * operaciones de negocio.
         *
         * No accedemos directamente al repositorio desde la vista.
         */
        this._iNoLogueado = iNoLogueado;

        /*
         * BaseView se encarga de ejecutar build() y bindEvents().
         */
        initView();
    }

    /*
     * Construcción de la interfaz.
     *
     * En este método solamente creamos y configuramos componentes.
     * Los eventos se registran en bindEvents().
     */
    @Override
    protected void build() {

        setSizeFull();

        setAlignItems(
                Alignment.CENTER);

        setJustifyContentMode(
                JustifyContentMode.CENTER);

        setSpacing(true);

        // -------------------------------------------------
        // Cabecera
        // -------------------------------------------------

        H1 cabecera =
                new H1("Crear nueva cuenta");

        cabecera.getStyle()
                .set("color", "#2c3e50");

        add(cabecera);

        // -------------------------------------------------
        // Datos de usuario
        // -------------------------------------------------

        VerticalLayout datosLayout =
                new VerticalLayout();

        datosLayout.setAlignItems(
                Alignment.CENTER);

        login =
                new EmailField("Login");

        password =
                new TextField("Password");

        /*
         * Estos campos pertenecen a la interfaz gráfica.
         * Todavía no estamos creando el Youtuber ni guardándolo
         * en la base de datos.
         */
        datosLayout.add(
                login,
                password);

        // -------------------------------------------------
        // Avatar
        // -------------------------------------------------

        VerticalLayout avatarLayout =
                new VerticalLayout();

        avatarLayout.setAlignItems(
                Alignment.CENTER);

        Label avatarLabel =
                new Label("Avatar");

        avatar =
                new Image();

        avatar.setMaxWidth("300px");

        /*
         * MemoryBuffer almacena temporalmente el archivo que
         * el usuario selecciona mediante Upload.
         *
         * En este caso el buffer es local a la construcción
         * de la vista.
         */
        MemoryBuffer buffer =
                new MemoryBuffer();

        Upload upload =
                new Upload(buffer);

        /*
         * Limitamos los tipos de archivo que puede seleccionar
         * el usuario.
         */
        upload.setAcceptedFileTypes(
                "image/jpeg",
                "image/png",
                "image/gif");

        /*
         * Este evento se ejecuta cuando la subida del archivo
         * termina correctamente.
         *
         * Lo utilizamos únicamente para mostrar una
         * previsualización del avatar.
         */
        upload.addSucceededListener(
                event -> {

                    InputStream inputStream =
                            buffer.getInputStream();

                    StreamResource resource =
                            new StreamResource(
                                    event.getFileName(),
                                    () -> inputStream);

                    avatar.setSrc(resource);
                });

        avatarLayout.add(
                avatarLabel,
                upload,
                avatar);

        // -------------------------------------------------
        // Imagen de fondo
        // -------------------------------------------------

        VerticalLayout fondoLayout =
                new VerticalLayout();

        fondoLayout.setAlignItems(
                Alignment.CENTER);

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
                "image/gif");

        /*
         * Igual que con el avatar, aquí solamente mostramos
         * una previsualización del archivo seleccionado.
         */
        upload2.addSucceededListener(
                event -> {

                    InputStream inputStream =
                            buffer2.getInputStream();

                    StreamResource resource =
                            new StreamResource(
                                    event.getFileName(),
                                    () -> inputStream);

                    imagenDeFondo.setSrc(resource);
                });

        fondoLayout.add(
                fondoLabel,
                upload2,
                imagenDeFondo);

        // -------------------------------------------------
        // Layout de imágenes
        // -------------------------------------------------

        HorizontalLayout imagenesLayout =
                new HorizontalLayout(
                        avatarLayout,
                        fondoLayout);

        imagenesLayout.setAlignItems(
                Alignment.START);

        imagenesLayout.setSpacing(true);

        // -------------------------------------------------
        // Botón registrar
        // -------------------------------------------------

        registrarButton =
                new Button(
                        "Registrar",
                        new Icon(VaadinIcon.USER_CHECK));

        registrarButton.addThemeVariants(
                ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_SUCCESS);

        // -------------------------------------------------
        // Contenido
        // -------------------------------------------------

        VerticalLayout contenido =
                new VerticalLayout(
                        datosLayout,
                        imagenesLayout,
                        registrarButton);

        contenido.setAlignItems(
                Alignment.CENTER);

        contenido.setSpacing(true);
        contenido.setPadding(true);

        add(contenido);
    }

    /*
     * Registro de eventos.
     *
     * Mantenemos separado el código que construye la interfaz
     * del código que responde a las acciones del usuario.
     */
    @Override
    protected void bindEvents() {

        registrarButton.addClickListener(
                event -> Registrar());
    }

    /*
     * Solicita al sistema la creación de la cuenta.
     */
    public void Registrar() {

        /*
         * La vista recoge los datos introducidos por el usuario
         * y delega la operación en iNoLogueado.
         *
         * La vista no crea directamente el objeto Youtuber
         * ni realiza el repository.save().
         */
        _iNoLogueado.registrar(
                login.getValue(),
                password.getValue(),
                avatar.getSrc(),
                imagenDeFondo.getSrc());

        /*
         * Una vez terminado el registro, volvemos a la vista
         * pública.
         */
        UI.getCurrent()
                .navigate(
                        NoLogueado.class);
    }

    /*
     * Método relacionado con el envío de correo.
     *
     * Actualmente no forma parte del flujo de registro y solamente
     * muestra un mensaje informativo.
     */
    public void EnviarCorreo() {

        Notification.show(
                "El envío de correo está deshabilitado");
    }
}