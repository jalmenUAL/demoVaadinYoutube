package com.example.demo.views;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.services.iYoutuber;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.Route;

@Route("VerVideodeYoutuber")

public class VerVideodeYoutuber extends VerVideo {

    /*
     * Servicio específico del Youtuber.
     *
     * Permite realizar operaciones que no están disponibles
     * en iInicio, como dar o quitar "Me gusta".
     */
    private final iYoutuber iYoutuber;

    /*
     * Botón utilizado para indicar si el usuario ha dado
     * "Me gusta" al vídeo y permitir cambiar ese estado.
     */
    private Button likeButton;

    /*
     * Indica si el usuario actualmente autenticado
     * ha dado "Me gusta" al vídeo.
     */
    private Boolean legusta;


    /*
     * Constructor.
     *
     * Reutilizamos la construcción común de VerVideo
     * y añadimos la dependencia específica de Youtuber.
     */
    public VerVideodeYoutuber(
            iYoutuber iYoutuber,
            ViewFactoryProvider viewFactory) {

        super(iYoutuber, viewFactory);

        this.iYoutuber = iYoutuber;
    }


    /*
     * Da o quita el "Me gusta" del vídeo.
     *
     * El estado actual se comprueba antes de decidir
     * qué operación realizar.
     */
    public void like() {

        /*
         * Obtenemos el usuario autenticado.
         */
        Authentication auth =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();


        /*
         * El principal contiene la entidad Youtuber
         * utilizada durante la autenticación.
         */
        com.example.demo.tables.Youtuber usuario =
                (com.example.demo.tables.Youtuber)
                        auth.getPrincipal();


        /*
         * Si el usuario todavía no ha dado "Me gusta",
         * se registra el like.
         */
        if (!video.getLe_gusta_a().contains(usuario)) {

            iYoutuber.likeVideo(
                    usuario.getLogin(),
                    video.getId());

        } else {

            /*
             * Si ya había dado "Me gusta",
             * se elimina el like.
             */
            iYoutuber.dislikeVideo(
                    usuario.getLogin(),
                    video.getId());
        }


        /*
         * Recargamos la vista para reflejar
         * inmediatamente el nuevo estado.
         */
        UI.getCurrent()
                .getPage()
                .reload();
    }


    /*
     * Especialización de los comentarios para Youtuber.
     *
     * La implementación concreta puede proporcionar
     * funcionalidades adicionales respecto a la versión
     * de un usuario no autenticado.
     */
    @Override
    public void VerComentarios() {

        _verComentarios =
                new VerComentariosdeYoutuber(
                        video.getTiene_comentarios(),
                        video.getId(),
                        viewFactory);

        comentarios.add(
                _verComentarios);
    }


    /*
     * Se sobrescribe setParameter() para añadir
     * funcionalidad específica del Youtuber después
     * de construir la vista base.
     */
    @Override
    protected void build(Integer parameter) {

        super.build(parameter);


        


        /*
         * Creamos el botón de "Me gusta".
         */
        likeButton =
                new Button(
                        "",
                        event2 -> like());

        likeButton.setIcon(
                new Icon(
                        VaadinIcon.THUMBS_UP));


        /*
         * Obtenemos el usuario autenticado para comprobar
         * si ya ha dado "Me gusta" al vídeo.
         */
        Authentication auth =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        com.example.demo.tables.Youtuber usuario =
                (com.example.demo.tables.Youtuber)
                        auth.getPrincipal();


        /*
         * Comprobamos si el usuario está entre los usuarios
         * que han dado "Me gusta" al vídeo.
         */
        legusta =
                video.getLe_gusta_a()
                        .contains(usuario);


        /*
         * El texto del botón depende del estado actual.
         */
        if (!legusta) {

            likeButton.setText(
                    "Me Gusta");

        } else {

            likeButton.setText(
                    "Quitar Me Gusta");
        }


        /*
         * Estilo común del botón.
         */
        likeButton.getStyle()
                .set(
                        "background-color",
                        "#0d6efd")
                .set(
                        "color",
                        "white")
                .set(
                        "border-radius",
                        "8px")
                .set(
                        "padding",
                        "10px 20px")
                .set(
                        "font-weight",
                        "bold");


        /*
         * Añadimos el botón a la zona principal
         * de la vista.
         */
        frame_y_comentarios.add(
                likeButton);
    }
}