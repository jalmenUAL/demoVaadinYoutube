package com.example.demo.views;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import com.example.demo.patterns.BaseView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.VaadinServletResponse;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Route("login")
@AnonymousAllowed
public class Login extends BaseView {

    /*
     * AuthenticationManager es el componente de Spring Security
     * encargado de autenticar al usuario.
     *
     * En nuestro caso utilizará el CustomAuthProvider, que es quien
     * comprueba las credenciales contra nuestra base de datos.
     */
    private final AuthenticationManager authenticationManager;

    /*
     * LoginOverlay es el componente visual que proporciona Vaadin
     * para mostrar el formulario de inicio de sesión.
     *
     * Lo guardamos como atributo porque necesitamos acceder a él
     * también desde bindEvents(), por ejemplo para mostrar un error.
     */
    private LoginOverlay loginOverlay;

    public Login(AuthenticationManager authenticationManager) {

        this.authenticationManager = authenticationManager;

        /*
         * BaseView se encarga de ejecutar:
         *
         *     build();
         *     bindEvents();
         *
         * De esta forma separamos la construcción de la interfaz
         * de la programación de sus eventos.
         */
        initView();
    }

    /*
     * ============================================================
     * CONSTRUCCIÓN DE LA VISTA
     * ============================================================
     *
     * Aquí solamente creamos y configuramos los componentes visuales.
     *
     * Es recomendable NO meter aquí la lógica de los eventos.
     */
    @Override
    protected void build() {

        loginOverlay = new LoginOverlay();

        loginOverlay.setTitle("Mi aplicación");
        loginOverlay.setDescription(
                "Inicia sesión con tus credenciales");

        loginOverlay.setOpened(true);

        add(loginOverlay);
    }

    /*
     * ============================================================
     * EVENTOS
     * ============================================================
     *
     * Aquí conectamos los listeners de los componentes creados
     * en build().
     */
    @Override
    protected void bindEvents() {

        loginOverlay.addLoginListener(event -> {

            try {

                /*
                 * Vaadin nos proporciona el nombre de usuario y la
                 * contraseña introducidos en el formulario.
                 *
                 * Creamos un UsernamePasswordAuthenticationToken
                 * con esas credenciales y se lo entregamos al
                 * AuthenticationManager.
                 */
                Authentication auth =
                        authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                        event.getUsername(),
                                        event.getPassword()));

                /*
                 * ====================================================
                 * GUARDAR EL USUARIO AUTENTICADO
                 * ====================================================
                 *
                 * Authentication contiene la información del usuario
                 * autenticado y sus roles.
                 *
                 * Lo guardamos en el SecurityContext para que Spring
                 * Security sepa quién es el usuario actual.
                 */
                SecurityContext context =
                        SecurityContextHolder.createEmptyContext();

                context.setAuthentication(auth);

                SecurityContextHolder.setContext(context);

                /*
                 * También guardamos el SecurityContext en la sesión HTTP.
                 *
                 * Esto permite conservar la autenticación entre
                 * diferentes peticiones.
                 *
                 * Es especialmente importante en una aplicación
                 * web porque el SecurityContext no debe depender
                 * únicamente de una variable local.
                 */
                HttpServletRequest req =
                        ((VaadinServletRequest)
                                VaadinService.getCurrentRequest())
                                .getHttpServletRequest();

                HttpServletResponse res =
                        ((VaadinServletResponse)
                                VaadinService.getCurrentResponse())
                                .getHttpServletResponse();

                new HttpSessionSecurityContextRepository()
                        .saveContext(
                                context,
                                req,
                                res);

                /*
                 * ====================================================
                 * NAVEGACIÓN SEGÚN EL ROL
                 * ====================================================
                 *
                 * El Authentication contiene las autoridades
                 * (roles) del usuario.
                 *
                 * Comprobamos si es administrador.
                 *
                 * Si lo es, vamos a la vista Administrador.
                 *
                 * En caso contrario, en nuestra aplicación sabemos
                 * que se trata de un Youtuber.
                 */
                boolean esAdministrador =
                        auth.getAuthorities()
                                .stream()
                                .anyMatch(a ->
                                        a.getAuthority()
                                                .equals(
                                                        "ROLE_ADMINISTRADOR"));

                if (esAdministrador) {

                    UI.getCurrent()
                            .navigate(Administrador.class);

                } else {

                    UI.getCurrent()
                            .navigate(Youtuber.class);
                }

            } catch (AuthenticationException e) {

                /*
                 * Si AuthenticationManager no consigue autenticar
                 * al usuario, se lanza AuthenticationException.
                 *
                 * No debemos mostrar la excepción directamente al
                 * usuario. Mostramos un mensaje genérico.
                 */
                loginOverlay.setError(true);

                Notification.show(
                        "Usuario o contraseña incorrectos",
                        3000,
                        Notification.Position.MIDDLE);
            }
        });
    }
}