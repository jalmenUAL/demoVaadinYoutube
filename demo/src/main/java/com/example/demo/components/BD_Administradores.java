package com.example.demo.components;

import java.util.Vector;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.repositories.RepositorioAdministrador;
import com.example.demo.tables.Administrador;

@Service
@Profile("real")
/**
 * Clase encargada de las operaciones relacionadas con los administradores.
 *
 * <p>
 * En esta aplicación utilizamos un repositorio de Spring Data JPA
 * ({@link RepositorioAdministrador}) para acceder a la base de datos.
 * Por eso no necesitamos escribir manualmente las consultas SQL básicas.
 * </p>
 *
 * <p>
 * La clase también recibe un {@link PasswordEncoder}, que utilizamos para
 * comprobar las contraseñas de forma segura. Las contraseñas almacenadas
 * en la base de datos están cifradas (por ejemplo, mediante BCrypt), por
 * lo que NO debemos comparar directamente:
 *
 * <pre>
 * rawPassword.equals(admin.getPassword())
 * </pre>
 *
 * En su lugar utilizamos:
 *
 * <pre>
 * passwordEncoder.matches(rawPassword, admin.getPassword())
 * </pre>
 * </p>
 */
public class BD_Administradores {

    

    /*
     * Colección generada por Visual Paradigm.
     *
     * En esta implementación el acceso real a los administradores se hace
     * mediante el repositorio de Spring Data, por lo que este Vector no
     * interviene en la autenticación.
     */
    public Vector<Administrador> _administradores =
            new Vector<Administrador>();

    /*
     * Repositorio utilizado para consultar los administradores en la BD.
     *
     * Spring se encarga de crear e inyectar esta dependencia.
     */
    private RepositorioAdministrador repository;

    /*
     * PasswordEncoder proporcionado por Spring Security.
     *
     * Sirve para comprobar contraseñas cifradas sin tener que descifrarlas.
     */
    private PasswordEncoder passwordEncoder;

    /**
     * Constructor.
     *
     * Las dependencias se reciben por parámetro en lugar de crear los objetos
     * manualmente con "new".
     *
     * Esto se conoce como INYECCIÓN DE DEPENDENCIAS y permite que Spring
     * gestione los objetos que necesita nuestra clase.
     */
    public BD_Administradores(
            RepositorioAdministrador administradoresRepository,
            PasswordEncoder passwordEncoder) {

        this.repository = administradoresRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Comprueba las credenciales de un administrador.
     *
     * @param login nombre de usuario del administrador
     * @param rawPassword contraseña introducida por el usuario,
     *                    todavía sin cifrar
     * @return el administrador si las credenciales son correctas;
     *         null si el usuario no existe o la contraseña no coincide
     */
    public Administrador autenticar(
            String login,
            String rawPassword) {

        

        /*
         * findById() devuelve un Optional porque el administrador puede
         * no existir.
         *
         * En lugar de hacer:
         *
         *     Administrador admin = repository.findById(login).get();
         *
         * utilizamos filter(), evitando lanzar una excepción si el usuario
         * no existe.
         */
        return repository.findById(login)

                /*
                 * filter() solamente deja pasar al administrador si la
                 * contraseña introducida coincide con la contraseña
                 * cifrada almacenada en la base de datos.
                 *
                 * rawPassword:
                 *     contraseña introducida por el usuario.
                 *
                 * admin.getPassword():
                 *     contraseña cifrada almacenada en la BD.
                 */
                .filter(admin ->
                        passwordEncoder.matches(
                                rawPassword,
                                admin.getPassword()))

                /*
                 * Si el Optional contiene un administrador, lo devolvemos.
                 *
                 * Si el usuario no existe o la contraseña no coincide,
                 * devolvemos null.
                 */
                .orElse(null);
    }
}