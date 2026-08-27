package com.example.demo.security;

import java.util.Collections;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demo.services.interfaces.iNoLogueado;
import com.example.demo.tables.Registrado;

@Component
/**
 * Proveedor personalizado de autenticación para Spring Security.
 *
 * <p>
 * Esta clase implementa AuthenticationProvider y se encarga de
 * comprobar las credenciales utilizando la lógica de autenticación
 * de nuestra propia aplicación.
 *
 * <p>
 * En lugar de hacer que Spring Security consulte directamente
 * la base de datos, delegamos la autenticación en:
 *
 *     iNoLogueado.Login(...)
 *
 * De esta forma, Spring Security queda conectado con nuestra
 * propia capa de lógica de negocio.
 */
public class CustomAuthProvider implements AuthenticationProvider {

    /**
     * Interfaz utilizada para realizar el login.
     *
     * <p>
     * Se utiliza la interfaz iNoLogueado en lugar de acceder
     * directamente a BD_Youtubers o BD_Administradores.
     *
     * <p>
     * @Lazy evita problemas de dependencias circulares durante
     * la creación de los beans de Spring.
     */
    private final iNoLogueado iNoLogueado;


    /**
     * Constructor.
     *
     * <p>
     * iNoLogueado se obtiene mediante inyección de dependencias.
     */
    public CustomAuthProvider(@Lazy iNoLogueado iNoLogueado) {
        this.iNoLogueado = iNoLogueado;
    }


    /**
     * Método principal de autenticación.
     *
     * <p>
     * Spring Security llama a este método cuando un usuario
     * intenta iniciar sesión.
     *
     * <p>
     * El objeto Authentication recibido contiene las credenciales
     * introducidas por el usuario.
     */
    @Override
    public Authentication authenticate(
            Authentication authentication)
            throws AuthenticationException {


        /*
         * Obtener el nombre de usuario introducido en el formulario
         * de login.
         */
        String username = authentication.getName();


        /*
         * Obtener la contraseña introducida.
         *
         * getCredentials() devuelve Object, por lo que se convierte
         * a String.
         */
        String password =
                authentication.getCredentials().toString();


        /*
         * Delegar la comprobación de las credenciales en nuestra
         * propia lógica de negocio.
         *
         * Login() se encarga de buscar el usuario y comprobar
         * la contraseña.
         *
         * Puede devolver:
         *
         *     - Administrador
         *     - Youtuber
         *     - null si las credenciales no son válidas
         */
        Registrado r =
                iNoLogueado.Login(username, password);


        /*
         * Si Login() devuelve null, las credenciales no son válidas.
         *
         * Se lanza una excepción de Spring Security para indicar
         * que la autenticación ha fallado.
         */
        if (r == null) {

            throw new UsernameNotFoundException(
                    "Usuario o contraseña incorrectos o Tu cuenta está bloqueada, contacta con el administrador");
        }


        /*
         * Comprobar si el usuario es un Youtuber.
         *
         * La sintaxis:
         *
         *     if (r instanceof Youtuber youtuber)
         *
         * comprueba el tipo y, al mismo tiempo, crea la variable
         * youtuber ya convertida al tipo correcto.
         */
        if (r instanceof com.example.demo.tables.Youtuber youtuber) {


            /*
             * Comprobar si el administrador ha bloqueado la cuenta.
             *
             * Boolean.TRUE.equals(...) permite realizar la comprobación
             * incluso aunque el valor de la propiedad sea null.
             */
            if (Boolean.TRUE.equals(youtuber.getBloqueado())) {

                /*
                 * DisabledException indica que el usuario existe,
                 * pero no tiene permitido autenticarse.
                 */
                throw new DisabledException(
                        "Usuario o contraseña incorrectos o Tu cuenta está bloqueada, contacta con el administrador");
            }
        }


        /*
         * Determinar el rol que tendrá el usuario dentro de
         * Spring Security.
         *
         * Los roles se utilizan posteriormente para controlar
         * qué partes de la aplicación puede utilizar cada usuario.
         */
        String role;


        /*
         * Si es Administrador, se le asigna ROLE_ADMINISTRADOR.
         */
        if (r instanceof com.example.demo.tables.Administrador) {

            role = "ROLE_ADMINISTRADOR";


        /*
         * Si es Youtuber, se le asigna ROLE_YOUTUBER.
         */
        } else if (r instanceof com.example.demo.tables.Youtuber) {

            role = "ROLE_YOUTUBER";


        /*
         * Rol genérico de seguridad para cualquier otro tipo
         * de Registrado.
         *
         * Es un caso de respaldo (fallback).
         */
        } else {

            role = "ROLE_REGISTRADO";
        }


        /*
         * Si hemos llegado hasta aquí, la autenticación ha sido
         * correcta.
         *
         * UsernamePasswordAuthenticationToken representa al
         * usuario autenticado dentro de Spring Security.
         *
         * Los tres argumentos son:
         *
         *     1. Principal
         *        El usuario autenticado.
         *
         *     2. Credentials
         *        Las credenciales asociadas al usuario.
         *
         *     3. Authorities
         *        Los roles/permisos del usuario.
         */
        return new UsernamePasswordAuthenticationToken(

                /*
                 * Principal:
                 *
                 * Aquí guardamos directamente nuestra entidad
                 * Registrado (Administrador o Youtuber).
                 *
                 * Por eso posteriormente podemos hacer:
                 *
                 *     auth.getPrincipal()
                 *
                 * y obtener nuestro objeto de dominio.
                 */
                r,

                /*
                 * Credentials:
                 *
                 * Se utiliza la contraseña almacenada en la entidad.
                 */
                r.getPassword(),

                /*
                 * Authorities:
                 *
                 * Se proporciona el rol que hemos determinado
                 * anteriormente.
                 */
                Collections.singletonList(() -> role));
    }


    /**
     * Indica a Spring Security qué tipos de autenticación
     * puede procesar este AuthenticationProvider.
     *
     * <p>
     * En este caso solamente acepta:
     *
     *     UsernamePasswordAuthenticationToken
     *
     * que es el tipo utilizado para autenticación mediante
     * usuario y contraseña.
     */
    @Override
    public boolean supports(Class<?> authentication) {

        return authentication.equals(
                UsernamePasswordAuthenticationToken.class);
    }
}