package com.example.demo.repositories;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.demo.tables.Administrador;
import com.example.demo.tables.Youtuber;

import org.springframework.security.crypto.password.PasswordEncoder;

@Component

/**
 * Inicializador de datos de la aplicación.
 *
 * <p>
 * Implementa CommandLineRunner, por lo que Spring Boot ejecutará
 * automáticamente el método run() una vez que la aplicación
 * haya terminado de inicializarse.
 *
 * <p>
 * En este caso se utiliza para crear usuarios iniciales
 * (un administrador y un Youtuber) si todavía no existen
 * en la base de datos.
 *
 * <p>
 * Esto resulta especialmente útil durante el desarrollo,
 * ya que permite disponer de usuarios con los que probar
 * la aplicación nada más arrancarla.
 */
public class DataInitializer implements CommandLineRunner {

    /**
     * Repositorio utilizado para consultar y guardar
     * administradores.
     */
    private final RepositorioAdministrador adminRepo;


    /**
     * Codificador de contraseñas proporcionado por Spring Security.
     *
     * <p>
     * Las contraseñas NO deben almacenarse directamente en la
     * base de datos. Se almacenan utilizando un hash seguro.
     */
    private final PasswordEncoder passwordEncoder;


    /**
     * Repositorio utilizado para consultar y guardar Youtubers.
     */
    private final RepositorioYoutuber youtuberRepo;


    /**
     * Constructor con inyección de dependencias.
     *
     * <p>
     * Spring proporciona automáticamente los repositorios y
     * el PasswordEncoder.
     */
    public DataInitializer(
            RepositorioAdministrador adminRepo,
            RepositorioYoutuber youtuberRepo,
            PasswordEncoder passwordEncoder) {

        this.adminRepo = adminRepo;
        this.passwordEncoder = passwordEncoder;
        this.youtuberRepo = youtuberRepo;
    }


    /**
     * Método ejecutado automáticamente por Spring Boot
     * al iniciar la aplicación.
     *
     * <p>
     * Se utiliza para comprobar si existen los usuarios
     * iniciales y crearlos si es necesario.
     */
    @Override
    public void run(String... args) throws Exception {


        /*
         * Comprobar si ya existe un administrador con login "admin".
         *
         * findById() devuelve un Optional, por lo que isEmpty()
         * permite comprobar si no se ha encontrado ningún usuario.
         *
         * Es importante hacer esta comprobación para no crear
         * el mismo usuario cada vez que se inicia la aplicación.
         */
        if (adminRepo.findById("admin").isEmpty()) {


            /*
             * Crear una nueva entidad Administrador.
             */
            Administrador admin = new Administrador();


            /*
             * Establecer el login del administrador.
             */
            admin.setLogin("admin");


            /*
             * IMPORTANTE:
             *
             * Nunca debemos guardar una contraseña directamente
             * en la base de datos.
             *
             * passwordEncoder.encode() genera el hash que se
             * almacenará en la BD.
             */
            admin.setPassword(
                    passwordEncoder.encode("admin123"));


            /*
             * Guardar el administrador en la base de datos.
             */
            adminRepo.save(admin);


            /*
             * Mensaje informativo para el desarrollador.
             */
            System.out.println(
                    "✅ Usuario administrador creado: admin / admin123");
        }


        /*
         * Hacer lo mismo con el usuario Youtuber.
         *
         * Si ya existe, no se crea uno nuevo.
         */
        if (youtuberRepo.findById("youtuber").isEmpty()) {


            /*
             * Crear un nuevo Youtuber.
             */
            Youtuber youtuber = new Youtuber();


            /*
             * Establecer el login.
             */
            youtuber.setLogin("youtuber");


            /*
             * El usuario se crea inicialmente desbloqueado.
             */
            youtuber.setBloqueado(false);


            /*
             * Cifrar la contraseña antes de almacenarla.
             *
             * La contraseña real no se guarda en la BD.
             */
            youtuber.setPassword(
                    passwordEncoder.encode("youtuber123"));


            /*
             * Guardar el Youtuber.
             */
            youtuberRepo.save(youtuber);


            /*
             * Mensaje informativo para el desarrollador.
             */
            System.out.println(
                    "✅ Usuario youtuber creado: youtuber / youtuber123");
        }
    }
}