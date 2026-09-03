package com.example.demo.views.common;

import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.patterns.BaseItemView;
import com.example.demo.tables.Video;
import com.example.demo.views.inicio.VerVideo;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;

@Route("GaleriadeVideos_item")
/**
 * Componente visual que representa un único vídeo dentro de una galería.
 *
 * <p>
 * Al heredar de BaseItemView<Video>, la clase recibe un objeto Video
 * como modelo y puede acceder a él mediante la variable protegida
 * "model".
 *
 * <p>
 * Este componente se encarga únicamente de mostrar la información
 * del vídeo y reaccionar cuando el usuario pulsa sobre su miniatura.
 */
public class GaleradeVideos_item extends BaseItemView<Video> {


    /**
     * Referencia a la vista de vídeo.
     *
     * <p>
     * Actualmente la navegación se realiza directamente mediante
     * UI.navigate(), por lo que este atributo no es necesario.
     */
    public VerVideo _verVideo;


    /**
     * Miniatura del vídeo.
     */
    private Image thumbnail;


    /**
     * Proveedor de factorías.
     *
     * <p>
     * Se utiliza para obtener la factoría correspondiente al usuario
     * que está utilizando actualmente la aplicación.
     */
    protected ViewFactoryProvider viewFactory;


    /**
     * Constructor.
     *
     * @param video vídeo que representará este componente
     * @param viewFactory proveedor de factorías de vistas
     */
    public GaleradeVideos_item(
            Video video,
            ViewFactoryProvider viewFactory) {

        /*
         * BaseItemView guarda el vídeo recibido en "model".
         */
        super(video);

        this.viewFactory = viewFactory;

        /*
         * Inicializar la vista:
         *
         *     build()
         *        ↓
         *     bindEvents()
         */
        initView();
    }


    /**
     * Construye la representación visual del vídeo.
     */
    @Override
    protected void build() {

        setWidthFull();
        setSpacing(true);


        // =========================
        // TÍTULO
        // =========================

        /*
         * "model" es el Video recibido en el constructor.
         */
        Span tituloSpan =
                new Span(model.getTitulo());

        tituloSpan.getStyle()
                .set("font-weight", "bold")
                .set("font-size", "1.2em");


        // =========================
        // ESTADÍSTICAS
        // =========================

        /*
         * Las relaciones del vídeo permiten conocer cuántos
         * usuarios han indicado que les gusta y cuántos comentarios
         * tiene.
         */
        int numMeGustas =
                model.getLe_gusta_a().size();

        int numComentarios =
                model.getTiene_comentarios().size();


        // =========================
        // PROPIETARIO
        // =========================

        /*
         * El propietario del vídeo se obtiene mediante la relación
         * es_de.
         */
        Avatar propietarioAvatar =
                new Avatar(
                        model.getEs_de().getLogin(),
                        model.getEs_de().getFotoPerfil());


        /*
         * Mostrar el avatar junto al título.
         */
        HorizontalLayout infoLayout =
                new HorizontalLayout(
                        propietarioAvatar,
                        tituloSpan);

        infoLayout.setAlignItems(
                Alignment.CENTER);

        infoLayout.setSpacing(true);


        // =========================
        // ESTADÍSTICAS VISUALES
        // =========================

        Span meGustasSpan =
                new Span("👍 " + numMeGustas);

        Span comentariosSpan =
                new Span("💬 " + numComentarios);


        HorizontalLayout statsLayout =
                new HorizontalLayout(
                        meGustasSpan,
                        comentariosSpan);

        statsLayout.setSpacing(true);


        // =========================
        // OBTENER ID DE YOUTUBE
        // =========================

        /*
         * La URL almacenada en el vídeo es una URL de YouTube.
         *
         * Ejemplo:
         *
         * https://www.youtube.com/watch?v=ABC123
         *
         * Necesitamos extraer el identificador del vídeo para poder
         * construir posteriormente la URL de la miniatura.
         */
        String videoId =
                model.getUrl()
                        .substring(
                                model.getUrl().lastIndexOf("/") + 1);


        /*
         * Eliminar posibles parámetros de la URL.
         *
         * Por ejemplo:
         *
         * ABC123?si=xxxx
         *
         * se convierte en:
         *
         * ABC123
         */
        if (videoId.contains("?")) {

            videoId =
                    videoId.substring(
                            0,
                            videoId.indexOf("?"));
        }


        /*
         * Eliminar también posibles fragmentos.
         */
        if (videoId.contains("#")) {

            videoId =
                    videoId.substring(
                            0,
                            videoId.indexOf("#"));
        }


        // =========================
        // MINIATURA
        // =========================

        /*
         * YouTube proporciona una URL conocida para obtener
         * la miniatura de un vídeo a partir de su ID.
         */
        String thumbnailUrl =
                "https://img.youtube.com/vi/"
                        + videoId
                        + "/hqdefault.jpg";


        thumbnail =
                new Image(
                        thumbnailUrl,
                        "Thumbnail del video");


        thumbnail.setWidth("100%");


        /*
         * Cambios visuales para indicar que la miniatura
         * es interactiva.
         */
        thumbnail.getStyle()
                .set("border-radius", "8px")
                .set("cursor", "pointer");


        // =========================
        // AÑADIR COMPONENTES
        // =========================

        add(
                infoLayout,
                statsLayout,
                thumbnail);
    }


    /**
     * Registra los eventos de la interfaz.
     */
    @Override
    protected void bindEvents() {

        /*
         * Al pulsar la miniatura se abre la vista del vídeo.
         */
        thumbnail.addClickListener(
                e -> VerVideo());
    }


    /**
     * Navega hasta la vista correspondiente al vídeo.
     *
     * <p>
     * La factoría determina qué implementación de VerVideo
     * debe utilizarse dependiendo del usuario actual.
     */
    public void VerVideo() {

        /*
         * getFactory() selecciona la factoría adecuada:
         *
         *     Administrador → AdministradorViewFactory
         *     Youtuber      → YoutuberViewFactory
         *     No logueado   → NoLogueadoViewFactory
         *
         * createVideo() devuelve la clase de vista correspondiente.
         *
         * El segundo parámetro es el ID del vídeo y será recibido
         * por la vista mediante HasUrlParameter.
         */
        UI.getCurrent().navigate(
                viewFactory
                        .getFactory()
                        .createVideo(),
                model.getId());
    }
}