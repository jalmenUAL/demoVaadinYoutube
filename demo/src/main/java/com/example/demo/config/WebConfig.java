package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
/**
 * Configuración de recursos estáticos de Spring.
 *
 * <p>
 * Las imágenes que suben los usuarios se guardan físicamente
 * en la carpeta "uploads" del proyecto.
 *
 * <p>
 * Por seguridad y por la configuración habitual de Spring,
 * una carpeta del sistema de archivos no se sirve automáticamente
 * como una URL web.
 *
 * <p>
 * Esta clase crea la relación entre:
 *
 *     URL:        /uploads/...
 *     Carpeta:    uploads/...
 *
 * Por ejemplo:
 *
 *     /uploads/usuarios/pepe/avatar.png
 *
 * se busca físicamente en:
 *
 *     uploads/usuarios/pepe/avatar.png
 */
public class WebConfig implements WebMvcConfigurer {

    /**
     * Registra una ruta para que Spring pueda servir
     * archivos almacenados fuera de los recursos normales
     * de la aplicación.
     */
    @Override
    public void addResourceHandlers(
            ResourceHandlerRegistry registry) {

        /*
         * Cualquier petición que empiece por:
         *
         *     /uploads/
         *
         * será buscada en la carpeta:
         *
         *     uploads/
         *
         * Por ejemplo:
         *
         *     /uploads/usuarios/pepe/banner.jpg
         *
         * corresponde a:
         *
         *     uploads/usuarios/pepe/banner.jpg
         */
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }
}