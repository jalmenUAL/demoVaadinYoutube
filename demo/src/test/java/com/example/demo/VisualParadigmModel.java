package com.example.demo;

import java.util.Map;
import java.util.Set;

import com.example.demo.external.ServidordeCorreo;
import com.example.demo.views.administrador.Administrador;
import com.example.demo.views.administrador.PerfilAjenodeAdministrador;
import com.example.demo.views.administrador.VerComentariosdeAdministrador_item;
import com.example.demo.views.administrador.VerVideodeAdministrador;
import com.example.demo.views.common.GaleradeVideos_item;
import com.example.demo.views.common.ListadeVideos_item;
import com.example.demo.views.common.VerComentarios_item;
import com.example.demo.views.common.Videosrelacionados_item;
import com.example.demo.views.common.Youtubersseguidos_item;
import com.example.demo.views.inicio.Buscar;
import com.example.demo.views.inicio.Inicio;
import com.example.demo.views.inicio.Perfil;
import com.example.demo.views.inicio.VerVideo;
import com.example.demo.views.nologueado.NoLogueado;
import com.example.demo.views.nologueado.Registrar;
import com.example.demo.views.registrado.Registrado;
import com.example.demo.views.youtuber.PerfilAjenodeYoutuber;
import com.example.demo.views.youtuber.PerfilPropio;
import com.example.demo.views.youtuber.VerComentariosdeYoutuber;
import com.example.demo.views.youtuber.VerVideodeYoutuber;
import com.example.demo.views.youtuber.Youtuber;

 



public class VisualParadigmModel {
 protected static final Set<String> CLASES_UML = Set.of(

                        "Administrador",
                        "Buscar",
                        "Comentar",
                        "Configuracion",
                        "GaleradeVideos",
                        "GaleradeVideos_item",
                        "Inicio",
                        "ListadeVideos",
                        "ListadeVideos_item",
                        "Login",
                        "NoLogueado",
                        "Perfil",
                        "PerfilAjeno",
                        "PerfilAjenodeAdministrador",
                        "PerfilAjenodeYoutuber",
                        "PerfilPropio",
                        "PublicarVideo",
                        "Registrado",
                        "Registrar",
                        "ResultadodeBusqueda",
                        "ServidordeCorreo",
                        "UltimosVideos",
                        "UltimosVideos_item",
                        "UltimosVideosdeAdministrador",
                        "UltimosVideosdeAdministrador_item",
                        "UltimosVideosdeYoutuber",
                        "UltimosVideosdeYoutuber_item",
                        "Usuariosdenunciados",
                        "Usuariosdenunciados_item",
                        "VerComentarios",
                        "VerComentarios_item",
                        "VerComentariosdeAdministrador",
                        "VerComentariosdeAdministrador_item",
                        "VerComentariosdeYoutuber",
                        "VerComentariosdeYoutuber_item",
                        "VerVideo",
                        "VerVideodeAdministrador",
                        "VerVideodeYoutuber",
                        "Videosgustados",
                        "Videosgustados_item",
                        "Videospublicados",
                        "Videospublicados_item",
                        "Videosrelacionados",
                        "Videosrelacionados_item",
                        "Youtuber",
                        "Youtubersseguidos",
                        "Youtubersseguidos_item"

        );

        protected static final Map<String, String> HERENCIA_UML = Map.ofEntries(

                        Map.entry("Registrado", "Inicio"),
                        Map.entry("NoLogueado", "Inicio"),

                        Map.entry("Administrador", "Registrado"),
                        Map.entry("Youtuber", "Registrado"),

                        Map.entry("PerfilPropio", "Perfil"),
                        Map.entry("PerfilAjeno", "Perfil"),

                        Map.entry("PerfilAjenodeAdministrador", "PerfilAjeno"),
                        Map.entry("PerfilAjenodeYoutuber", "PerfilAjeno"),

                        Map.entry("UltimosVideos", "GaleradeVideos"),
                        Map.entry("ResultadodeBusqueda", "GaleradeVideos"),

                        Map.entry("UltimosVideos_item", "GaleradeVideos_item"),
                        Map.entry("ResultadodeBusqueda_item", "GaleradeVideos_item"),

                        Map.entry("Videosgustados", "ListadeVideos"),
                        Map.entry("Videospublicados", "ListadeVideos"),

                        Map.entry("Videosgustados_item", "ListadeVideos_item"),
                        Map.entry("Videospublicados_item", "ListadeVideos_item"),

                        Map.entry("UltimosVideosdeAdministrador", "UltimosVideos"),
                        Map.entry("UltimosVideosdeYoutuber", "UltimosVideos"),

                        Map.entry("UltimosVideosdeAdministrador_item",
                                        "UltimosVideos_item"),
                        Map.entry("UltimosVideosdeYoutuber_item",
                                        "UltimosVideos_item"),

                        Map.entry("VerComentariosdeAdministrador",
                                        "VerComentarios"),
                        Map.entry("VerComentariosdeYoutuber",
                                        "VerComentarios"),

                        Map.entry("VerComentariosdeAdministrador_item",
                                        "VerComentarios_item"),
                        Map.entry("VerComentariosdeYoutuber_item",
                                        "VerComentarios_item"),

                        Map.entry("VerVideodeAdministrador", "VerVideo"),
                        Map.entry("VerVideodeYoutuber", "VerVideo")

        );

        protected static final Map<Class<?>, Set<String>> METODOS_UML = Map.ofEntries(

                        Map.entry(
                                        Administrador.class,
                                        Set.of("Usuariosdenunciados")),

                        Map.entry(
                                        Buscar.class,
                                        Set.of("ResultadodeBusqueda")),

                        Map.entry(
                                        GaleradeVideos_item.class,
                                        Set.of("VerVideo")),

                        Map.entry(
                                        Inicio.class,
                                        Set.of("Buscar",
                                                        "UltimosVideos")),

                        Map.entry(
                                        ListadeVideos_item.class,
                                        Set.of("VerVideo")),

                        Map.entry(
                                        NoLogueado.class,
                                        Set.of("Login",
                                                        "Registrar")),

                        Map.entry(
                                        Perfil.class,
                                        Set.of("Videosgustados",
                                                        "Videospublicados",
                                                        "Youtubersseguidos")),

                        Map.entry(
                                        PerfilAjenodeAdministrador.class,
                                        Set.of("Bloquear")),

                        Map.entry(
                                        PerfilAjenodeYoutuber.class,
                                        Set.of("Denunciar",
                                                        "Seguir")),

                        Map.entry(
                                        PerfilPropio.class,
                                        Set.of("PublicarVideo",
                                                        "Configuracion")),

                        Map.entry(
                                        Registrado.class,
                                        Set.of("Logout")),

                        Map.entry(
                                        Registrar.class,
                                        Set.of("EnviarCorreo")),

                        Map.entry(
                                        ServidordeCorreo.class,
                                        Set.of("EnviarCorreo")),

                        Map.entry(
                                        VerComentarios_item.class,
                                        Set.of("PerfilAjeno")),

                        Map.entry(
                                        VerComentariosdeAdministrador_item.class,
                                        Set.of("eliminar")),

                        Map.entry(
                                        VerComentariosdeYoutuber.class,
                                        Set.of("Comentar")),

                        Map.entry(
                                        VerVideo.class,
                                        Set.of("Videosrelacionados",
                                                        "VerComentarios",
                                                        "PerfilAjeno")),

                        Map.entry(
                                        VerVideodeAdministrador.class,
                                        Set.of("borrar")),

                        Map.entry(
                                        VerVideodeYoutuber.class,
                                        Set.of("like")),

                        Map.entry(
                                        Videosrelacionados_item.class,
                                        Set.of("VerVideo")),

                        Map.entry(
                                        Youtuber.class,
                                        Set.of("PerfilPropio")),

                        Map.entry(
                                        Youtubersseguidos_item.class,
                                        Set.of("PerfilAjeno"))

        );

        protected static final Map<Class<?>, Set<String>> ATRIBUTOS_UML = Map.ofEntries(

                        Map.entry(
                                        Inicio.class,
                                        Set.of("_buscar",
                                                        "_ultimosVideos")),

                        Map.entry(
                                        Administrador.class,
                                        Set.of("_usuariosdenunciados")),

                        Map.entry(
                                        NoLogueado.class,
                                        Set.of("_login",
                                                        "_registrar")),

                        Map.entry(
                                        Perfil.class,
                                        Set.of("_videosgustados",
                                                        "_videospublicados")),

                        Map.entry(
                                        PerfilPropio.class,
                                        Set.of("_configuracion",
                                                        "_publicarVideo")),

                        Map.entry(
                                        Buscar.class,
                                        Set.of("_resultadodeBusqueda")),

                        Map.entry(
                                        Registrar.class,
                                        Set.of("_servidordeCorreo")),

                        Map.entry(
                                        VerComentarios_item.class,
                                        Set.of("_perfilAjeno")),

                        Map.entry(
                                        VerVideo.class,
                                        Set.of("_verComentarios",
                                                        "_perfilAjeno",
                                                        "_videosrelacionados")),

                        Map.entry(
                                        Youtuber.class,
                                        Set.of("_PerfilPropio"))

        );
}
