package com.example.demo.views;

import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.patterns.BaseItemView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("Youtuberseguidos_item")

public class Youtubersseguidos_item
        extends BaseItemView<com.example.demo.tables.Youtuber> {

    // Referencia a la lista de Youtubers seguidos.
    // Actualmente NO se utiliza, por lo que se puede eliminar.
    public Youtubersseguidos _youtubersseguidos;

    // Referencia a PerfilAjeno.
    // Tampoco se utiliza directamente.
    // La navegación se hace mediante UI.navigate().
    public PerfilAjeno _perfilAjeno;

    // Provider que permite obtener la fábrica correspondiente
    // al usuario actual.
    protected ViewFactoryProvider viewFactory;


    // ============================================================
    // CONSTRUCTOR
    // ============================================================

    public Youtubersseguidos_item(
            com.example.demo.tables.Youtuber youtuber,
            ViewFactoryProvider viewFactory) {

        // Pasamos el Youtuber a BaseItemView.
        //
        // El objeto quedará disponible mediante "model".
        super(youtuber);

        // Guardamos el ViewFactoryProvider para poder utilizar
        // la fábrica cuando el usuario quiera visitar el perfil.
        this.viewFactory = viewFactory;

        // Inicializamos la vista.
        initView();
    }


    // ============================================================
    // NAVEGAR AL PERFIL
    // ============================================================

    public void PerfilAjeno() {

        // Obtenemos la fábrica correspondiente al usuario actual
        // mediante ViewFactoryProvider.
        //
        // Después solicitamos la clase de PerfilAjeno adecuada.
        //
        // Por ejemplo:
        //
        // Administrador -> PerfilAjenodeAdministrador
        // Youtuber      -> PerfilAjenodeYoutuber
        // No logueado   -> PerfilAjeno
        //
        // Finalmente pasamos el login del Youtuber como parámetro.
        UI.getCurrent().navigate(
                viewFactory.getFactory().createPerfilAjeno(),
                model.getLogin());
    }


    // ============================================================
    // CONSTRUCCIÓN DE LA VISTA
    // ============================================================

    @Override
    protected void build() {

        // --------------------------------------------------------
        // DATOS DEL YOUTUBER
        // --------------------------------------------------------

        // Login/nombre del Youtuber.
        String nombreUsuario =
                model.getLogin();

        // Número de seguidores que tiene.
        int seguidores =
                model.getSeguido_por().size();

        // URL de la imagen de perfil.
        String avatarUrl =
                model.getFotoPerfil();


        // --------------------------------------------------------
        // AVATAR
        // --------------------------------------------------------

        // Creamos la imagen del perfil.
        Image avatar =
                new Image(
                        avatarUrl,
                        "Avatar");

        // Tamaño del avatar.
        avatar.setWidth("60px");
        avatar.setHeight("60px");

        // Convertimos la imagen en circular.
        avatar.getStyle()
                .set("border-radius", "50%");


        // --------------------------------------------------------
        // NOMBRE
        // --------------------------------------------------------

        // Creamos el texto con el nombre del usuario.
        Span nombre =
                new Span(nombreUsuario);

        // Aplicamos formato al nombre.
        nombre.getStyle()
                .set("font-weight", "bold")
                .set("font-size", "18px");


        // --------------------------------------------------------
        // NÚMERO DE SEGUIDORES
        // --------------------------------------------------------

        Span seguidoresLabel =
                new Span(
                        seguidores + " seguidores");


        // --------------------------------------------------------
        // BOTÓN VER PERFIL
        // --------------------------------------------------------

        // Creamos el botón.
        //
        // Cuando el usuario pulse el botón se ejecutará
        // el método PerfilAjeno().
        Button verPerfilButton =
                new Button(
                        "Ver perfil",
                        e -> PerfilAjeno());


        // Estilos del botón.
        verPerfilButton.getStyle()
                .set("background-color", "#0d6efd")
                .set("color", "white")
                .set("border-radius", "8px")
                .set("font-weight", "bold");


        // --------------------------------------------------------
        // INFORMACIÓN DEL USUARIO
        // --------------------------------------------------------

        // Agrupamos:
        //
        // Nombre
        // Seguidores
        // Botón
        //
        // verticalmente.
        VerticalLayout info =
                new VerticalLayout(
                        nombre,
                        seguidoresLabel,
                        verPerfilButton);

        // Quitamos padding.
        info.setPadding(false);

        // Quitamos separación entre componentes.
        info.setSpacing(false);


        // --------------------------------------------------------
        // FILA PRINCIPAL
        // --------------------------------------------------------

        // Creamos una fila horizontal:
        //
        // [Avatar] [Información]
        //
        HorizontalLayout fila =
                new HorizontalLayout(
                        avatar,
                        info);

        // Centramos verticalmente los elementos.
        fila.setAlignItems(
                Alignment.CENTER);

        // Añadimos separación entre avatar e información.
        fila.setSpacing(true);

        // La fila ocupa todo el ancho.
        fila.setWidthFull();


        // Añadimos la fila a este item.
        add(fila);


        // --------------------------------------------------------
        // ESTILO DE LA TARJETA
        // --------------------------------------------------------

        // Damos aspecto de tarjeta al elemento.
        getStyle()
                .set("padding", "10px")
                .set("border", "1px solid #ddd")
                .set("border-radius", "10px");
    }


    // ============================================================
    // EVENTOS
    // ============================================================

    @Override
    protected void bindEvents() {

        // No hay eventos que registrar aquí.
        //
        // El evento del botón ya se ha registrado directamente
        // en build():
        //
        // new Button("Ver perfil", e -> PerfilAjeno());
    }
}