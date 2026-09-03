package com.example.demo.views.inicio;

import java.util.Date;
import java.util.List;

import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.patterns.BaseParameterizedView;
import com.example.demo.services.interfaces.iInicio;
import com.example.demo.tables.Video;
import com.example.demo.views.common.VerComentarios;
import com.example.demo.views.common.Videosrelacionados;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@AnonymousAllowed
@Route("VerVideo")
public class VerVideo extends BaseParameterizedView<Integer> {

    /*
     * Vistas hijas que forman parte de la pantalla.
     *
     * Se mantienen como atributos porque posteriormente
     * pueden actualizarse o utilizarse desde otros métodos
     * de esta vista.
     */
    public Videosrelacionados _videosrelacionados;
    public VerComentarios _verComentarios;
    public PerfilAjeno _perfilAjeno;

    /*
     * Dependencia de la capa de servicios.
     *
     * iInicio proporciona las operaciones necesarias para
     * consultar el vídeo y obtener información relacionada.
     */
    protected final iInicio iInicio;

    /*
     * Proveedor de factorías.
     *
     * Permite obtener la factoría correspondiente al usuario
     * actualmente autenticado (Administrador, Youtuber o
     * usuario no logueado).
     *
     * De esta forma, esta vista no necesita conocer directamente
     * qué implementación concreta de VerComentarios o PerfilAjeno
     * debe utilizar.
     */
    protected final ViewFactoryProvider viewFactory;

    /*
     * Modelo principal de la vista.
     *
     * Contiene el vídeo que se está visualizando.
     */
    protected Video video;

    /*
     * Layout principal.
     *
     * La pantalla se divide en dos zonas:
     * - vídeo + comentarios
     * - vídeos relacionados
     */
    protected HorizontalLayout video_y_relacionados;

    /*
     * Contenedor de la información del vídeo,
     * reproductor y comentarios.
     */
    protected VerticalLayout frame_y_comentarios;

    /*
     * Contenedor donde se muestran los comentarios.
     */
    protected VerticalLayout comentarios;

    /*
     * Contenedor donde se muestran los vídeos relacionados.
     */
    protected VerticalLayout relacionados;

    /*
     * Avatar del propietario del vídeo.
     *
     * Se utiliza también para permitir navegar a su perfil.
     */
    protected Image avatar;


    /*
     * Constructor.
     *
     * Recibe las dependencias necesarias mediante inyección
     * de dependencias.
     *
     * No se inicializa aquí la vista porque esta clase utiliza
     * BaseParameterizedView y necesita primero recibir el
     * parámetro de la URL (el id del vídeo).
     */
    public VerVideo(
            iInicio iInicio,
            ViewFactoryProvider viewFactory) {

        this.iInicio = iInicio;
        this.viewFactory = viewFactory;
    }


    /*
     * Registra los eventos de la vista.
     *
     * En este caso, el avatar del propietario funciona como
     * enlace hacia su perfil.
     */
    @Override
    protected void bindEvents() {

        avatar.addClickListener(
                e -> PerfilAjeno());
    }


    /*
     * Obtiene y muestra los vídeos relacionados con el vídeo actual.
     *
     * El contenido anterior se elimina antes de volver a cargar
     * los resultados para evitar duplicados.
     */
    public void Videosrelacionados() {

        relacionados.removeAll();

        List<Video> videosrelacionados =
                iInicio.getVideosRelacionados(
                        video.getId());

        /*
         * La vista se crea mediante la factoría correspondiente
         * al usuario actual.
         */
        _videosrelacionados =
                new Videosrelacionados(
                        videosrelacionados,
                        viewFactory);

        relacionados.add(
                _videosrelacionados);
    }


    /*
     * Obtiene y muestra los comentarios del vídeo actual.
     *
     * La creación de VerComentarios se delega en ViewFactory.
     *
     * Esto permite que Administrador, Youtuber y usuario no
     * autenticado puedan tener distintas implementaciones
     * de esta vista.
     */
    public void VerComentarios() {

        comentarios.removeAll();

        _verComentarios =
                viewFactory
                        .getFactory()
                        .createVerComentarios(
                                video.getTiene_comentarios(),
                                video.getId(),
                                viewFactory);

        comentarios.add(
                _verComentarios);
    }


    /*
     * Navega al perfil del propietario del vídeo.
     *
     * De nuevo, la clase concreta de PerfilAjeno se obtiene
     * mediante la factoría y no se instancia directamente.
     */
    public void PerfilAjeno() {

        UI.getCurrent().navigate(
                viewFactory
                        .getFactory()
                        .createPerfilAjeno(),
                video.getEs_de().getLogin());
    }


    /*
     * Construye la vista utilizando el parámetro recibido
     * desde la URL.
     *
     * El parámetro es el identificador del vídeo.
     */
    @Override
    protected void build(Integer parameter) {

        /*
         * Se limpia la vista por si build() vuelve a ejecutarse.
         */
        removeAll();


        /*
         * Recuperamos el vídeo utilizando el identificador
         * recibido como parámetro.
         */
        video =
                iInicio.findVideoById(parameter);


        /*
         * Creamos la estructura principal de la pantalla.
         */
        video_y_relacionados =
                new HorizontalLayout();

        frame_y_comentarios =
                new VerticalLayout();

        comentarios =
                new VerticalLayout();

        relacionados =
                new VerticalLayout();


        /*
         * Añadimos el layout principal a la vista.
         */
        add(video_y_relacionados);


        /*
         * La primera columna contiene el vídeo y sus comentarios.
         */
        video_y_relacionados.add(
                frame_y_comentarios);

        video_y_relacionados
                .getStyle()
                .set("width", "100%");


        /*
         * Creamos el avatar del propietario del vídeo.
         */
        avatar =
                new Image(
                        video.getEs_de().getFotoPerfil(),
                        "Avatar");

        avatar.setWidth("50px");
        avatar.setHeight("50px");

        avatar.getStyle()
                .set("border-radius", "50%");


        /*
         * Información básica del vídeo.
         */
        String nombreUsuario =
                video.getEs_de().getLogin();

        String tituloVideo =
                video.getTitulo();

        Date fechaVideo = video.getFecha();


        /*
         * Nombre del propietario.
         */
        VerticalLayout infoUsuario =
                new VerticalLayout();

        infoUsuario.setSpacing(false);
        infoUsuario.setPadding(false);

        infoUsuario.add(
                new Span(nombreUsuario));


        /*
         * Título del vídeo.
         */
        H2 titulo =
                new H2(tituloVideo);

        Span fecha = new Span(fechaVideo.toString());
        


        /*
         * Cabecera formada por avatar y nombre
         * del propietario.
         */
        HorizontalLayout cabecera =
                new HorizontalLayout(
                        avatar,
                        infoUsuario);

        cabecera.setAlignItems(
                Alignment.CENTER);

        cabecera.setSpacing(true);
        cabecera.setWidthFull();

        HorizontalLayout tituloconfecha = new HorizontalLayout(titulo, new Span(" Subido el...."), fecha);


        /*
         * Agrupamos título y propietario.
         */
        VerticalLayout cabeceraCompleta =
                new VerticalLayout(
                        tituloconfecha,
                        cabecera);

        cabeceraCompleta.setSpacing(false);
        cabeceraCompleta.setPadding(false);


        frame_y_comentarios.add(
                cabeceraCompleta);


        /*
         * Extraemos el identificador del vídeo de YouTube
         * a partir de su URL.
         */
        String videoId =
                video.getUrl()
                        .substring(
                                video.getUrl()
                                        .lastIndexOf("/") + 1);


        /*
         * Eliminamos los parámetros de la URL.
         *
         * Ejemplo:
         * https://youtube.com/watch/abc123?x=1
         *
         * pasa a utilizar únicamente:
         * abc123
         */
        if (videoId.contains("?")) {

            videoId =
                    videoId.substring(
                            0,
                            videoId.indexOf("?"));
        }


        /*
         * Eliminamos también posibles fragmentos.
         */
        if (videoId.contains("#")) {

            videoId =
                    videoId.substring(
                            0,
                            videoId.indexOf("#"));
        }


        /*
         * Construimos la URL utilizada para incrustar
         * el reproductor de YouTube.
         */
        String embedUrl =
                "https://www.youtube.com/embed/"
                        + videoId;


        /*
         * Contenedor del reproductor.
         *
         * Se utiliza un iframe para incrustar el vídeo
         * directamente en la aplicación Vaadin.
         */
        Div iframeContainer =
                new Div();


        /*
         * Se introduce el iframe como HTML dentro del
         * componente Vaadin.
         */
        iframeContainer
                .getElement()
                .setProperty(
                        "innerHTML",
                        "<iframe width='100%' height='600' "
                                + "src='" + embedUrl + "' "
                                + "title='YouTube video player' "
                                + "frameborder='0' "
                                + "allow='accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture' "
                                + "allowfullscreen></iframe>");

        iframeContainer.setWidth("100%");


        /*
         * Añadimos el reproductor a la columna principal.
         */
        frame_y_comentarios.add(
                iframeContainer);

        frame_y_comentarios
                .getStyle()
                .set("width", "350%");


        /*
         * Cargamos las dos partes dinámicas de la página:
         *
         * - vídeos relacionados
         * - comentarios
         */
        Videosrelacionados();
        VerComentarios();


        /*
         * Los comentarios se muestran debajo del vídeo.
         */
        frame_y_comentarios.add(
                comentarios);


        /*
         * Los vídeos relacionados se muestran en la segunda
         * zona de la pantalla.
         */
        video_y_relacionados.add(
                relacionados);


        /*
         * La vista ocupa todo el ancho disponible.
         */
        getStyle()
                .set("width", "100%");
    }
}