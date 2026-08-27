
package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.vaadin.flow.spring.security.VaadinWebSecurity;

@Configuration
/**
 * Configuración de seguridad de la aplicación.
 *
 * <p>
 * Extiende VaadinWebSecurity para utilizar la integración de
 * Spring Security proporcionada por Vaadin.
 *
 * <p>
 * Aquí se configura principalmente:
 *
 *     - La seguridad de las vistas de Vaadin.
 *     - Los recursos públicos de la aplicación.
 *     - La pantalla de login.
 *     - El AuthenticationManager.
 *     - El PasswordEncoder utilizado para las contraseñas.
 */
public class SecurityConfig extends VaadinWebSecurity {

    /**
     * Proveedor personalizado de autenticación.
     *
     * <p>
     * Este objeto contiene nuestra lógica para comprobar
     * usuarios, contraseñas, roles y cuentas bloqueadas.
     */
    private final CustomAuthProvider customAuthProvider;


    /**
     * Constructor.
     *
     * <p>
     * Spring inyecta automáticamente el CustomAuthProvider.
     */
    public SecurityConfig(CustomAuthProvider customAuthProvider) {
        this.customAuthProvider = customAuthProvider;
    }


    /**
     * Configuración principal de Spring Security.
     *
     * <p>
     * Aquí se indican las reglas de acceso a los recursos
     * de la aplicación.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {


        /*
         * Permitir el acceso público a los archivos subidos.
         *
         * Las imágenes de perfiles y banners se sirven mediante
         * URLs como:
         *
         *     /uploads/usuarios/...
         *
         * Por eso esta ruta debe ser accesible incluso cuando
         * el usuario no ha iniciado sesión.
         *
         * Esto está relacionado con WebConfig, que indica a Spring
         * dónde buscar físicamente esos archivos.
         */
        http.authorizeHttpRequests(auth -> auth
            .requestMatchers("/uploads/**").permitAll()
        );


        /*
         * Aplicar la configuración de seguridad proporcionada
         * por Vaadin.
         *
         * Es importante llamar a super.configure(http), ya que
         * Vaadin necesita configurar internamente determinados
         * recursos y mecanismos necesarios para que funcionen
         * correctamente sus vistas.
         */
        super.configure(http);


        /*
         * Indicar cuál es la vista que se utilizará como pantalla
         * de inicio de sesión.
         *
         * Cuando Spring Security necesita que un usuario se
         * autentique, Vaadin mostrará esta vista.
         */
        setLoginView(
                http,
                com.example.demo.views.nologueado.Login.class);
    }


    /**
     * Crea el AuthenticationManager utilizado por Spring Security.
     *
     * <p>
     * ProviderManager permite utilizar nuestro
     * CustomAuthProvider como proveedor de autenticación.
     *
     * <p>
     * De esta manera, cuando un usuario intenta iniciar sesión,
     * Spring Security terminará llamando a:
     *
     *     CustomAuthProvider.authenticate(...)
     */
    @Bean
    public AuthenticationManager authenticationManager(
            CustomAuthProvider customAuthProvider) {

        return new ProviderManager(customAuthProvider);
    }


    /**
     * Define el PasswordEncoder que utilizará la aplicación.
     *
     * <p>
     * BCrypt se utiliza para almacenar las contraseñas de forma
     * segura mediante hashes.
     *
     * <p>
     * Este mismo PasswordEncoder se utiliza posteriormente
     * tanto al registrar usuarios como al comprobar sus
     * contraseñas durante el login.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}