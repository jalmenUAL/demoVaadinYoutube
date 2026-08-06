package com.example.demo.factories;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.facade.BDPrincipal;
import com.example.demo.services.iAdministrador;
import com.example.demo.tables.Comentario;
import com.example.demo.tables.Video;
import com.example.demo.views.GaleradeVideos_item;
import com.example.demo.views.Perfil;
import com.example.demo.views.PerfilAjeno;
import com.example.demo.views.VerComentarios;
import com.example.demo.views.VerComentarios_item;
import com.example.demo.views.VerVideo;

public interface ViewFactory {

    Class<? extends VerVideo> createVideo();

    Class<? extends PerfilAjeno> createPerfilAjeno(String login);

    Class<? extends VerComentarios> createVerComentarios();

    GaleradeVideos_item createGaleriaItem(Video video);


    static ViewFactory getFactory() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated()) {

            boolean esAdmin = auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMINISTRADOR"));
            boolean esYoutuber = auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_YOUTUBER"));

            if (esAdmin) {
                return new AdministradorViewFactory();
            } else if (esYoutuber) {
                return new YoutuberViewFactory();
            } else {
                return new NoLogueadoViewFactory();
            }
        }
        return null;
    }

    VerComentarios_item createComentarioItem(Comentario comentario);

    Class<? extends Perfil> createPerfilView(String idYoutuber);
}
