package com.example.demo.services.interfaces;

import org.springframework.stereotype.Service;

import com.example.demo.tables.Registrado;

/**
 * Interfaz que define las operaciones disponibles para un usuario
 * que todavía no ha iniciado sesión.
 *
 * <p>
 * Extiende iInicio porque un usuario no autenticado también puede
 * acceder a las funcionalidades generales de la página de inicio,
 * como buscar vídeos, consultar vídeos y ver vídeos relacionados.
 *
 * <p>
 * Además, añade las operaciones específicas de un usuario no
 * autenticado:
 *
 *     - Iniciar sesión.
 *     - Registrarse.
 */


public interface iNoLogueado extends iInicio {


    /**
     * Intenta autenticar a un usuario.
     *
     * <p>
     * Si las credenciales son correctas, devuelve el usuario
     * correspondiente. Si no son válidas, la implementación
     * devuelve null.
     *
     * <p>
     * La autenticación concreta se realiza posteriormente mediante
     * Spring Security y CustomAuthProvider.
     *
     * @param login nombre de usuario
     * @param password contraseña introducida
     *
     * @return usuario autenticado o null si las credenciales
     *         no son correctas
     */
    Registrado Login(String login, String password);


    /**
     * Registra un nuevo Youtuber en la aplicación.
     *
     * <p>
     * La contraseña debe ser cifrada antes de almacenarse en la
     * base de datos.
     *
     * @param login nombre de usuario
     * @param password contraseña
     * @param avatarUrl ruta o URL del avatar
     * @param fondoUrl ruta o URL de la imagen de fondo
     */
    void registrar(
            String login,
            String password,
            String avatarUrl,
            String fondoUrl);
}