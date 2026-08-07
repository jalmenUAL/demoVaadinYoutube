package com.example.demo.factories;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ViewFactoryProvider {

    private final AdministradorViewFactory administradorFactory;
    private final YoutuberViewFactory youtuberFactory;
    private final NoLogueadoViewFactory noLogueadoFactory;

    public ViewFactoryProvider(
            AdministradorViewFactory administradorFactory,
            YoutuberViewFactory youtuberFactory,
            NoLogueadoViewFactory noLogueadoFactory) {

        this.administradorFactory = administradorFactory;
        this.youtuberFactory = youtuberFactory;
        this.noLogueadoFactory = noLogueadoFactory;
    }

    public ViewFactory getFactory() {

        Authentication auth =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (auth != null
                && auth.isAuthenticated()
                && !auth.getPrincipal().equals("anonymousUser")) {

            boolean esAdmin =
                    auth.getAuthorities().stream()
                            .anyMatch(a ->
                                    a.getAuthority()
                                            .equals("ROLE_ADMINISTRADOR"));

            boolean esYoutuber =
                    auth.getAuthorities().stream()
                            .anyMatch(a ->
                                    a.getAuthority()
                                            .equals("ROLE_YOUTUBER"));

            if (esAdmin) {
                return administradorFactory;
            }

            if (esYoutuber) {
                return youtuberFactory;
            }
        }

        return noLogueadoFactory;
    }
}