package com.example.demo.views.common;

import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.patterns.BaseItemView;
import com.example.demo.tables.Video;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;

@Route("ListadeVideos_item")
/**
 * Elemento visual que representa un único vídeo dentro de una lista.
 *
 * <p>
 * Hereda de BaseItemView<Video>, por lo que recibe un objeto Video
 * como modelo y lo almacena en la variable "model".
 *
 * <p>
 * La responsabilidad de esta clase es mostrar la información básica
 * del vídeo y permitir al usuario acceder a él pulsando sobre
 * su miniatura.
 */
public class ListadeVideos_item
        extends BaseItemView<Video> {


    /**
     * Referencia a la vista de vídeo.
     *
     * <p>
     * Actualmente la navegación se realiza mediante UI.navigate(),
     * por lo que este atributo no es necesario.
     */
    public VerVideo _verVideo;


    /**
     * Imagen utilizada como miniatura del vídeo.
     */
    private Image thumbnail;


    /**
     * Proveedor de factorías.
     *
     * <p>
     * Permite obtener la factoría correspondiente al usuario actual
     * y, a través de ella, la vista concreta de VerVideo.
     */
    protected ViewFactoryProvider viewFactory;


    /**
     * Constructor.
     *
     * @param video vídeo que se mostrará
     * @param viewFactory proveedor de factorías
     */
    public ListadeVideos_item(
            Video video,
            ViewFactoryProvider viewFactory) {

        /*
         * La clase padre guarda el vídeo en la variable "model".
         */
        super(video);

        this.viewFactory = viewFactory;


        /*
         * Inicializa la vista:
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
        // DATOS DEL VÍDEO
        // =========================

        /*
         * Como model es un Video, podemos acceder directamente
         * a sus propiedades y relaciones.
         */
        String tituloVideo =
                model.getTitulo();

        String propietarioNombre =
                model.getEs_de().getLogin();

        String propietarioFotoUrl =
                model.getEs_de().getFotoPerfil();


        /*
         * Obtener el número de "Me gusta" y comentarios.
         *
         * Estas colecciones representan las relaciones entre
         * los usuarios y el vídeo.
         */
        int numMeGustas =
                model.getLe_gusta_a().size();

        int numComentarios =
                model.getTiene_comentarios().size();


        // =========================
        // TÍTULO
        // =========================

        Span tituloSpan =
                new Span(tituloVideo);

        tituloSpan.getStyle()
                .set("font-weight", "bold")
                .set("font-size", "1.2em");


        // =========================
        // PROPIETARIO
        // =========================

        /*
         * El propietario del vídeo se obtiene mediante
         * la relación "es_de".
         */
        Avatar propietarioAvatar =
                new Avatar(
                        propietarioNombre,
                        propietarioFotoUrl);


        HorizontalLayout infoLayout =
                new HorizontalLayout(
                        propietarioAvatar,
                        tituloSpan);

        infoLayout.setAlignItems(
                Alignment.CENTER);

        infoLayout.setSpacing(true);


        // =========================
        // ESTADÍSTICAS
        // =========================

        Span meGustasSpan =
                new Span(
                        "👍 " + numMeGustas);

        Span comentariosSpan =
                new Span(
                        "💬 " + numComentarios);


        HorizontalLayout statsLayout =
                new HorizontalLayout(
                        meGustasSpan,
                        comentariosSpan);

        statsLayout.setSpacing(true);


        // =========================
        // EXTRAER ID DE YOUTUBE
        // =========================

        /*
         * La URL del vídeo se utiliza para obtener el ID
         * que necesita YouTube para construir la miniatura.
         *
         * Por ejemplo:
         *
         * https://www.youtube.com/watch/ABC123
         *
         * nos permite obtener:
         *
         * ABC123
         */
        String videoId =
                model.getUrl()
                        .substring(
                                model.getUrl().lastIndexOf("/") + 1);


        /*
         * Eliminar parámetros de la URL.
         *
         * Ejemplo:
         *
         * ABC123?si=xxxxx
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
         * Construir la URL de la miniatura de YouTube
         * utilizando el ID del vídeo.
         */
        String thumbnailUrl =
                "https://img.youtube.com/vi/"
                        + videoId
                        + "/hqdefault.jpg";


        thumbnail =
                new Image(
                        thumbnailUrl,
                        "Miniatura del video");

        thumbnail.setWidth("100%");


        /*
         * El cursor "pointer" indica visualmente que
         * la miniatura es interactiva.
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
     * Registra los eventos de la vista.
     */
    @Override
    protected void bindEvents() {

        /*
         * Al pulsar la miniatura se navega hasta el vídeo.
         */
        thumbnail.addClickListener(
                e -> VerVideo());
    }


    /**
     * Navega a la vista correspondiente al vídeo.
     *
     * <p>
     * La factoría determina qué implementación de VerVideo
     * debe utilizarse dependiendo del usuario actual.
     */
    public void VerVideo() {

        /*
         * getFactory() obtiene la factoría adecuada:
         *
         *     AdministradorViewFactory
         *     YoutuberViewFactory
         *     NoLogueadoViewFactory
         *
         * createVideo() devuelve la clase concreta de VerVideo.
         *
         * model.getId() se pasa como parámetro de la URL.
         */
        UI.getCurrent().navigate(
                viewFactory
                        .getFactory()
                        .createVideo(),
                model.getId());
    }
}