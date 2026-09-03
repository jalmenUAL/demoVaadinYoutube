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

@Route("Videosrelacionados_item")

public class Videosrelacionados_item extends BaseItemView<Video> {

    // Referencia a la vista de vídeo.
    // Actualmente no se utiliza directamente, por lo que podría eliminarse.
    public VerVideo _verVideo;

    // Provider que nos permite obtener la ViewFactory correspondiente
    // al usuario que está utilizando la aplicación.
    //
    // Gracias a esto, cuando pulsamos sobre un vídeo relacionado,
    // no navegamos directamente a VerVideo.class, sino que dejamos
    // que la fábrica decida qué implementación corresponde:
    //
    // - VerVideo
    // - VerVideodeAdministrador
    // - VerVideodeYoutuber
    protected ViewFactoryProvider viewFactory;

    // Imagen que contiene la miniatura del vídeo.
    // Se utiliza también para detectar el clic del usuario.
    protected Image thumbnail;


    // ============================================================
    // CONSTRUCTOR
    // ============================================================

    public Videosrelacionados_item(
            Video video,
            ViewFactoryProvider viewFactory) {

        // Pasamos el objeto Video a la clase padre.
        //
        // BaseItemView se encargará de almacenar este objeto
        // normalmente en la variable "model".
        super(video);

        // Guardamos el ViewFactoryProvider para utilizarlo
        // posteriormente cuando haya que navegar a otro vídeo.
        this.viewFactory = viewFactory;

        // Inicializamos la vista.
        //
        // Dependiendo de cómo esté implementado BaseItemView,
        // esto provocará que se ejecuten build() y bindEvents().
        initView();
    }


    // ============================================================
    // NAVEGACIÓN AL VÍDEO
    // ============================================================

    public void VerVideo() {

        // Navegamos hacia la vista correspondiente al vídeo.
        //
        // IMPORTANTE:
        // No utilizamos directamente VerVideo.class.
        //
        // En su lugar:
        //
        // 1. Obtenemos la fábrica correspondiente al usuario.
        // 2. Le pedimos que cree la clase de vista de vídeo.
        // 3. Navegamos pasando el ID del vídeo como parámetro.
        //
        // Por ejemplo:
        //
        // Usuario no logueado -> VerVideo
        // Administrador        -> VerVideodeAdministrador
        // Youtuber             -> VerVideodeYoutuber
        UI.getCurrent().navigate(
                viewFactory.getFactory().createVideo(),
                model.getId());
    }


    // ============================================================
    // CONSTRUCCIÓN DE LA VISTA
    // ============================================================

    @Override
    protected void build() {

        // --------------------------------------------------------
        // DATOS DEL VÍDEO
        // --------------------------------------------------------

        // Obtenemos el título del vídeo.
        String tituloVideo = model.getTitulo();

        // Obtenemos el login del propietario del vídeo.
        String propietarioNombre =
                model.getEs_de().getLogin();

        // Obtenemos la foto de perfil del propietario.
        String propietarioFotoUrl =
                model.getEs_de().getFotoPerfil();

        // Número de usuarios a los que les gusta el vídeo.
        int numMeGustas =
                model.getLe_gusta_a().size();

        // Número de comentarios que tiene el vídeo.
        int numComentarios =
                model.getTiene_comentarios().size();


        // --------------------------------------------------------
        // TÍTULO DEL VÍDEO
        // --------------------------------------------------------

        // Creamos un componente Span para mostrar el título.
        Span tituloSpan = new Span(tituloVideo);

        // Aplicamos estilos al título.
        tituloSpan.getStyle()
                .set("font-weight", "bold")
                .set("font-size", "1.2em");


        // --------------------------------------------------------
        // AVATAR DEL PROPIETARIO
        // --------------------------------------------------------

        // Creamos un Avatar utilizando:
        //
        // propietarioNombre -> nombre que se muestra
        // propietarioFotoUrl -> imagen de perfil
        Avatar propietarioAvatar =
                new Avatar(
                        propietarioNombre,
                        propietarioFotoUrl);


        // --------------------------------------------------------
        // INFORMACIÓN DEL VÍDEO
        // --------------------------------------------------------

        // Creamos un layout horizontal que contiene:
        //
        // [Avatar] [Título]
        //
        HorizontalLayout infoLayout =
                new HorizontalLayout(
                        propietarioAvatar,
                        tituloSpan);

        // Centramos verticalmente los componentes.
        infoLayout.setAlignItems(
                Alignment.CENTER);

        // Añadimos separación entre avatar y título.
        infoLayout.setSpacing(true);

        // Añadimos este layout a la vista.
        add(infoLayout);


        // --------------------------------------------------------
        // ESTADÍSTICAS DEL VÍDEO
        // --------------------------------------------------------

        // Texto que muestra el número de "Me gusta".
        Span meGustasSpan =
                new Span(
                        "👍 " + numMeGustas);

        // Texto que muestra el número de comentarios.
        Span comentariosSpan =
                new Span(
                        "💬 " + numComentarios);

        // Creamos un layout horizontal para las estadísticas.
        //
        // [👍 10] [💬 5]
        //
        HorizontalLayout statsLayout =
                new HorizontalLayout(
                        meGustasSpan,
                        comentariosSpan);

        statsLayout.setSpacing(true);

        // Añadimos las estadísticas a la vista.
        add(statsLayout);


        // --------------------------------------------------------
        // OBTENER ID DEL VÍDEO DE YOUTUBE
        // --------------------------------------------------------

        // Obtenemos la URL almacenada en el objeto Video.
        //
        // Ejemplo:
        // https://www.youtube.com/watch?v=ABC123
        //
        // o:
        // https://youtu.be/ABC123
        //
        // Primero obtenemos todo lo que aparece después
        // del último "/".
        String videoId =
                model.getUrl().substring(
                        model.getUrl().lastIndexOf("/") + 1);


        // Si la URL contiene parámetros después del ID:
        //
        // ABC123?si=xxxx
        //
        // nos quedamos únicamente con:
        //
        // ABC123
        if (videoId.contains("?")) {

            videoId =
                    videoId.substring(
                            0,
                            videoId.indexOf("?"));
        }


        // Si la URL contiene un fragmento:
        //
        // ABC123#algo
        //
        // eliminamos todo lo que aparece después del "#".
        if (videoId.contains("#")) {

            videoId =
                    videoId.substring(
                            0,
                            videoId.indexOf("#"));
        }


        // --------------------------------------------------------
        // CONSTRUIR URL DE LA MINIATURA
        // --------------------------------------------------------

        // YouTube permite obtener la miniatura de un vídeo
        // utilizando esta estructura:
        //
        // https://img.youtube.com/vi/ID/hqdefault.jpg
        //
        // Por ejemplo:
        //
        // https://img.youtube.com/vi/ABC123/hqdefault.jpg
        //
        String thumbnailUrl =
                "https://img.youtube.com/vi/"
                        + videoId
                        + "/hqdefault.jpg";


        // --------------------------------------------------------
        // CREAR MINIATURA
        // --------------------------------------------------------

        // Creamos la imagen utilizando la URL de la miniatura.
        thumbnail =
                new Image(
                        thumbnailUrl,
                        "Miniatura del video");

        // La imagen ocupará todo el ancho disponible.
        thumbnail.setWidth("100%");

        // Aplicamos algunos estilos:
        //
        // border-radius -> bordes redondeados
        // cursor -> indica visualmente que se puede pulsar
        thumbnail.getStyle()
                .set("border-radius", "8px")
                .set("cursor", "pointer");

        // Añadimos la miniatura a la vista.
        add(thumbnail);
    }


    // ============================================================
    // EVENTOS
    // ============================================================

    @Override
    protected void bindEvents() {

        // Cuando el usuario pulsa sobre la miniatura,
        // llamamos al método VerVideo().
        //
        // De esta forma, el usuario puede acceder al vídeo
        // pulsando sobre su miniatura.
        thumbnail.addClickListener(
                e -> VerVideo());
    }
}