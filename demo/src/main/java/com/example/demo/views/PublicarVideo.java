package com.example.demo.views;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.patterns.BaseView;
import com.example.demo.services.iYoutuber;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

 
@Route("PublicarVideo")
@RolesAllowed("ROLE_YOUTUBER")
public class PublicarVideo extends BaseView {

    /*
     * Vista encargada de publicar un nuevo vídeo.
     *
     * Hereda de BaseView, por lo que seguimos el patrón:
     *
     *     initView()
     *        ├── build()
     *        └── bindEvents()
     *
     * Además, @RolesAllowed garantiza que solamente los usuarios
     * con ROLE_YOUTUBER puedan acceder a esta vista.
     */

    private final iYoutuber _iYoutuber;

    private TextField introduzcaLaUrl;
    private TextField introduzcaEltitulo;

    private Button publicarButton;

    public PublicarVideo(iYoutuber iYoutuber) {

        super();

        /*
         * La vista no accede directamente al repositorio ni a las
         * entidades para realizar operaciones de negocio.
         *
         * Utiliza la interfaz iYoutuber, que representa las operaciones
         * que un Youtuber puede realizar.
         */
        this._iYoutuber = iYoutuber;

        /*
         * Inicializamos la vista mediante el patrón BaseView.
         *
         * Esto ejecutará:
         *      build();
         *      bindEvents();
         */
        initView();
    }

    /*
     * Construcción de la interfaz.
     *
     * En build() solamente construimos y configuramos los componentes.
     * Los eventos se registran posteriormente en bindEvents().
     */
    @Override
    protected void build() {

        setWidthFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(
                JustifyContentMode.CENTER);

        setSpacing(true);
        setPadding(true);

        // -------------------------------------------------
        // Título
        // -------------------------------------------------

        H2 titulo =
                new H2("📹 Publicar nuevo video");

        titulo.getStyle()
                .set("color", "#2c3e50");

        // -------------------------------------------------
        // Campo título
        // -------------------------------------------------

        introduzcaEltitulo =
                new TextField("Título del Video");

        introduzcaEltitulo.setPlaceholder(
                "Ej. Cómo cocinar arroz");

        introduzcaEltitulo.setWidth("60%");

        // -------------------------------------------------
        // Campo URL
        // -------------------------------------------------

        introduzcaLaUrl =
                new TextField("URL del Video");

        introduzcaLaUrl.setPlaceholder(
                "Ej. https://youtube.com/...");

        introduzcaLaUrl.setWidth("60%");

        // -------------------------------------------------
        // Botón publicar
        // -------------------------------------------------

        publicarButton =
                new Button("Publicar Video");

        publicarButton.addThemeVariants(
                ButtonVariant.LUMO_PRIMARY);

        publicarButton.getStyle()
                .set("border-radius", "8px")
                .set("font-weight", "bold")
                .set("margin-top", "10px");

        // -------------------------------------------------
        // Añadir componentes
        // -------------------------------------------------

        add(
                titulo,
                introduzcaEltitulo,
                introduzcaLaUrl,
                publicarButton);
    }

    /*
     * Registro de los eventos de la vista.
     *
     * Separamos la construcción de la interfaz de la lógica de
     * los eventos gracias al patrón BaseView.
     */
    @Override
    protected void bindEvents() {

        publicarButton.addClickListener(
                e -> publicarVideo());
    }

    /*
     * Publica el vídeo perteneciente al usuario autenticado.
     */
    public void publicarVideo() {

        /*
         * Obtenemos el Authentication que Spring Security ha creado
         * después de realizar correctamente el login.
         */
        Authentication auth =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        /*
         * En nuestro CustomAuthProvider establecimos la entidad
         * Youtuber como principal:
         *
         * new UsernamePasswordAuthenticationToken(
         *      r,
         *      r.getPassword(),
         *      ...
         * )
         *
         * Por eso podemos recuperar aquí el Youtuber autenticado.
         */
        com.example.demo.tables.Youtuber usuario =
                (com.example.demo.tables.Youtuber)
                        auth.getPrincipal();

        /*
         * La vista conoce la interfaz iYoutuber, pero no necesita saber
         * cómo se guarda el vídeo en la base de datos.
         *
         * La operación se delega en la capa de servicio/fachada.
         */
        _iYoutuber.publicarVideo(
                usuario.getLogin(),
                introduzcaEltitulo.getValue(),
                introduzcaLaUrl.getValue());

        /*
         * Una vez publicado el vídeo, volvemos a la vista anterior.
         */
        UI.getCurrent()
                .getPage()
                .getHistory()
                .back();
    }
}