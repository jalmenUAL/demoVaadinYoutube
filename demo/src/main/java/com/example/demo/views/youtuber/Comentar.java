package com.example.demo.views.youtuber;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.patterns.BaseParameterizedView;
import com.example.demo.services.interfaces.iYoutuber;
import com.example.demo.tables.Video;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@Route("Comentar")
@RolesAllowed("ROLE_YOUTUBER")
/**
 * Componente utilizado para escribir y publicar un comentario
 * sobre un vídeo concreto.
 *
 * <p>
 * Esta clase hereda de BaseParameterizedView<String> porque necesita
 * recibir un parámetro para saber sobre qué vídeo se va a comentar.
 *
 * <p>
 * El parámetro recibido es el ID del vídeo, que llega como String
 * y posteriormente se convierte a int.
 */
public class Comentar extends BaseParameterizedView<String> {


    /**
     * Servicio que proporciona las operaciones disponibles para
     * un Youtuber.
     *
     * <p>
     * La vista no accede directamente al repositorio ni a la BD.
     * Utiliza la interfaz de servicios.
     */
    private final iYoutuber _iYoutuber;


    /**
     * Campo de texto donde el usuario escribe el comentario.
     */
    private TextField campoComentario;


    /**
     * Botón utilizado para publicar el comentario.
     */
    private Button btnPublicar;


    /**
     * ID del vídeo sobre el que se está escribiendo el comentario.
     */
    private int id;


    /**
     * Constructor.
     *
     * <p>
     * El servicio iYoutuber es inyectado por Spring.
     */
    public Comentar(iYoutuber iYoutuber) {

        this._iYoutuber = iYoutuber;
    }


    /**
     * Construye la interfaz utilizando el parámetro recibido
     * en la URL.
     *
     * @param parameter ID del vídeo recibido como String
     */
    @Override
    protected void build(String parameter) {

        /*
         * Los parámetros de URL llegan como String.
         *
         * Por eso debemos convertir el ID a entero antes de
         * utilizarlo como identificador del vídeo.
         */
        id = Integer.parseInt(parameter);


        /*
         * Configuración general del layout.
         */
        setWidthFull();
        setPadding(true);
        setSpacing(true);
        setAlignItems(Alignment.STRETCH);


        /*
         * Campo de texto para escribir el comentario.
         */
        campoComentario =
                new TextField("Escribe un comentario");

        campoComentario.setWidthFull();


        /*
         * Botón para publicar el comentario.
         */
        btnPublicar =
                new Button("Publicar comentario");

        btnPublicar.addThemeVariants(
                ButtonVariant.LUMO_PRIMARY);

        btnPublicar.setWidthFull();


        /*
         * Añadir los componentes a la vista.
         */
        add(campoComentario, btnPublicar);
    }


    /**
     * Registra los eventos de la interfaz.
     */
    @Override
    protected void bindEvents() {

        /*
         * Cuando se pulsa el botón:
         *
         *     1. Se publica el comentario.
         *     2. Se limpia el campo de texto.
         */
        btnPublicar.addClickListener(e -> {

            publicarComentario();

            campoComentario.clear();
        });
    }


    /**
     * Publica el comentario escrito por el usuario.
     */
    public void publicarComentario() {


        /*
         * Recuperamos el vídeo utilizando el ID que recibimos
         * como parámetro de la URL.
         */
        Video video =
                _iYoutuber.findVideoById(id);


        /*
         * Obtener la información del usuario actualmente autenticado.
         *
         * SecurityContextHolder permite acceder al contexto de
         * seguridad de Spring Security.
         */
        Authentication auth =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();


        /*
         * En CustomAuthProvider establecimos la entidad Youtuber
         * como principal:
         *
         *     new UsernamePasswordAuthenticationToken(
         *          r,
         *          r.getPassword(),
         *          ...
         *     )
         *
         * Por eso aquí podemos recuperar el Youtuber desde
         * auth.getPrincipal().
         */
        com.example.demo.tables.Youtuber usuario =
                (com.example.demo.tables.Youtuber)
                        auth.getPrincipal();


        /*
         * Publicar el comentario a través de la interfaz de servicios.
         *
         * No modificamos directamente el vídeo ni el repositorio
         * desde la vista.
         */
        _iYoutuber.publicarComentario(
                usuario.getLogin(),
                String.valueOf(video.getId()),
                campoComentario.getValue());


        /*
         * Volver a la página anterior una vez publicado
         * correctamente el comentario.
         */
        UI.getCurrent()
                .getPage()
                .getHistory()
                .back();
    }
}