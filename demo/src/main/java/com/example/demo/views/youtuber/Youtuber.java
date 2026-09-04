package com.example.demo.views.youtuber;

import java.util.Vector;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.services.interfaces.iYoutuber;
import com.example.demo.tables.Video;
import com.example.demo.views.registrado.Registrado;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@Route("Youtuber")
@RolesAllowed("ROLE_YOUTUBER")
public class Youtuber extends Registrado {

    // Servicio específico para las operaciones del Youtuber.
    //
    // La clase hereda de Registrado, por lo que ya tiene además
    // acceso a las funcionalidades comunes de un usuario registrado.
    protected final iYoutuber iYoutuber;


    // Referencia a la vista del perfil propio.
    //
    // Actualmente NO se utiliza directamente, por lo que podría
    // eliminarse.
    protected PerfilPropio _PerfilPropio;


    // Botón que permite acceder al perfil del Youtuber.
    private Button perfilBtn;


    // ============================================================
    // CONSTRUCTOR
    // ============================================================

    public Youtuber(
            iYoutuber iYoutuber,
            ViewFactoryProvider viewFactory) {

        // Llamamos al constructor de Registrado.
        //
        // Registrado, a su vez, llama al constructor de Inicio.
        super(iYoutuber, viewFactory);

        // Guardamos el servicio específico del Youtuber.
        this.iYoutuber = iYoutuber;

        // Inicializamos la vista.
        initView();
    }


    // ============================================================
    // CARGAR ÚLTIMOS VÍDEOS
    // ============================================================

    @Override
    public void UltimosVideos() {

        // Obtenemos la autenticación actual de Spring Security.
        Authentication auth =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();


        // El principal contiene la entidad Youtuber que ha iniciado
        // sesión, porque CustomAuthProvider la estableció como
        // principal al autenticar al usuario.
        com.example.demo.tables.Youtuber usuario =
                (com.example.demo.tables.Youtuber)
                        auth.getPrincipal();


        // Vector donde almacenaremos los vídeos que queremos mostrar.
        Vector<Video> videos = new Vector<>();


        // --------------------------------------------------------
        // VÍDEOS DE LOS YOUTUBERS SEGUIDOS
        // --------------------------------------------------------

        // usuario.getSeguidor_de() contiene los Youtubers que
        // sigue el usuario actual.
        //
        // Recorremos todos ellos.
        for (Object obj : usuario.getSeguidor_de()) {

            // Convertimos el objeto a Youtuber.
            com.example.demo.tables.Youtuber seguido =
                    (com.example.demo.tables.Youtuber) obj;


            // Añadimos todos los vídeos publicados por ese Youtuber.
            videos.addAll(
                    seguido.getHa_publicado());
        }


        // --------------------------------------------------------
        // VÍDEOS PROPIOS
        // --------------------------------------------------------

        // Además de los vídeos de los usuarios seguidos,
        // añadimos los vídeos publicados por el propio usuario.
        videos.addAll(
                usuario.getHa_publicado());


        // --------------------------------------------------------
        // CREAR LA VISTA
        // --------------------------------------------------------

        // Creamos la vista especializada para Youtubers.
        //
        // Aquí se aprovecha la especialización de las vistas:
        //
        // UltimosVideosdeYoutuber
        //        extends UltimosVideos
        //
        _ultimosVideos =
                new UltimosVideosdeYoutuber(
                        videos,
                        viewFactory);


        // Añadimos la galería al cuerpo de la página.
        body.add(_ultimosVideos);
    }


    // ============================================================
    // ACCEDER AL PERFIL PROPIO
    // ============================================================

    public void PerfilPropio() {

        // Obtenemos la autenticación actual.
        Authentication auth =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();


        // Recuperamos al Youtuber autenticado.
        com.example.demo.tables.Youtuber usuario =
                (com.example.demo.tables.Youtuber)
                        auth.getPrincipal();


        // Navegamos al perfil propio.
        //
        // Se pasa el login como parámetro.
        UI.getCurrent().navigate(
                PerfilPropio.class,
                usuario.getLogin());
    }


    // ============================================================
    // CONSTRUCCIÓN DE LA VISTA
    // ============================================================

    @Override
    protected void build() {

        // Primero ejecutamos la construcción de Registrado.
        //
        // Registrado llama a Inicio y añade:
        // - Logo
        // - Buscador
        // - Botón de cerrar sesión
        super.build();


        // Creamos el botón "Mi Perfil".
        perfilBtn =
                new Button(
                        "Mi Perfil",
                        new Icon(VaadinIcon.USER));


        // Aplicamos el estilo primario de Vaadin.
        perfilBtn.addThemeVariants(
                ButtonVariant.LUMO_PRIMARY);


        // Aplicamos algunos estilos personalizados.
        perfilBtn.getStyle()
                .set("margin", "10px")
                .set("border-radius", "8px");


        // Añadimos el botón al header.
        header.add(perfilBtn);


        // Colocamos los elementos del header hacia la derecha.
        header.setJustifyContentMode(
                JustifyContentMode.END);
    }


    // ============================================================
    // EVENTOS
    // ============================================================

    @Override
    protected void bindEvents() {

        // Primero registramos los eventos heredados.
        //
        // Registrado registra el evento del botón "Cerrar sesión".
        // Inicio registra el evento del buscador.
        super.bindEvents();


        // Cuando se pulsa "Mi Perfil", llamamos a PerfilPropio().
        perfilBtn.addClickListener(
                e -> PerfilPropio());
    }


    @Override
    public com.example.demo.services.interfaces.iInicio getServicio() {
       return iYoutuber;
    }


    @Override
    public ViewFactoryProvider getViewFactory() {
       return viewFactory;
    }
}