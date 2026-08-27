package com.example.demo.components;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Vector;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.repositories.RepositorioYoutuber;
import com.example.demo.tables.Video;
import com.example.demo.tables.Youtuber;

@Service
/**
 * Clase encargada de gestionar las operaciones relacionadas con los
 * Youtubers.
 *
 * <p>
 * Esta clase actúa como capa de acceso/lógica de negocio entre las vistas
 * y {@link RepositorioYoutuber}, que es el repositorio de Spring Data JPA.
 * </p>
 *
 * <p>
 * Aquí encontramos operaciones de:
 * <ul>
 *     <li>Autenticación y registro.</li>
 *     <li>Búsqueda de usuarios.</li>
 *     <li>Actualización de la configuración.</li>
 *     <li>Gestión de imágenes.</li>
 *     <li>Seguimientos.</li>
 *     <li>Denuncias.</li>
 *     <li>Bloqueos.</li>
 *     <li>Me gusta de vídeos.</li>
 * </ul>
 * </p>
 */
public class BD_Youtubers {

     

    public Vector<Youtuber> _youtubers =
            new Vector<Youtuber>();

    /*
     * Repositorio de Spring Data JPA.
     *
     * Spring proporciona automáticamente métodos como:
     *
     *     findById()
     *     findAll()
     *     save()
     *     delete()
     */
    final RepositorioYoutuber repository;

    /*
     * PasswordEncoder utilizado para cifrar y comprobar contraseñas.
     *
     * Las contraseñas NO deben almacenarse en texto plano.
     */
    private PasswordEncoder passwordEncoder;

    /**
     * Constructor.
     *
     * Las dependencias se reciben mediante inyección de dependencias.
     */
    public BD_Youtubers(
            RepositorioYoutuber repository,
            PasswordEncoder passwordEncoder) {

        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }


    // ============================================================
    // AUTENTICACIÓN Y BÚSQUEDA
    // ============================================================

    /**
     * Comprueba las credenciales de un Youtuber.
     *
     * @param login nombre de usuario
     * @param rawPassword contraseña introducida por el usuario
     * @return el Youtuber si las credenciales son correctas;
     *         null en caso contrario
     */
    public Youtuber autenticar(
            String login,
            String rawPassword) {

        /*
         * findById() devuelve Optional porque el usuario puede no existir.
         *
         * matches() compara la contraseña introducida con la contraseña
         * cifrada almacenada en la base de datos.
         *
         * No debemos hacer:
         *
         *     rawPassword.equals(youtuber.getPassword())
         *
         * porque la contraseña almacenada está cifrada.
         */
        return repository.findById(login)
                .filter(youtuber ->
                        passwordEncoder.matches(
                                rawPassword,
                                youtuber.getPassword()))
                .orElse(null);
    }

    /**
     * Busca un Youtuber por su login.
     *
     * @param login identificador del Youtuber
     * @return Youtuber encontrado
     * @throws RuntimeException si no existe
     */
    public Youtuber findYoutuberById(String login) {

        /*
         * En este caso utilizamos orElseThrow() porque consideramos
         * que el usuario solicitado debe existir.
         */
        return repository.findById(login)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Youtuber no encontrado"));
    }


    // ============================================================
    // REGISTRO
    // ============================================================

    /**
     * Registra un nuevo Youtuber.
     *
     * @param login nombre de usuario
     * @param password contraseña introducida por el usuario
     * @param avatarUrl ruta del avatar
     * @param fondoUrl ruta de la imagen de fondo
     */
    public void registrar(
            String login,
            String password,
            String avatarUrl,
            String fondoUrl) {

        /*
         * Creamos una entidad nueva.
         *
         * En este momento solamente existe en memoria.
         */
        Youtuber nuevoYoutuber =
                new Youtuber();

        nuevoYoutuber.setLogin(login);

        /*
         * IMPORTANTE:
         *
         * La contraseña se cifra antes de guardarla.
         *
         * Nunca debemos guardar directamente:
         *
         *     nuevoYoutuber.setPassword(password);
         */
        String passwordCifrada =
                passwordEncoder.encode(password);

        nuevoYoutuber.setPassword(
                passwordCifrada);

        nuevoYoutuber.setFotoPerfil(
                avatarUrl);

        nuevoYoutuber.setBanner(
                fondoUrl);

        /*
         * Un usuario nuevo comienza sin estar bloqueado.
         */
        nuevoYoutuber.setBloqueado(false);

        /*
         * save() persiste el nuevo usuario en la base de datos.
         */
        repository.save(nuevoYoutuber);
    }


    // ============================================================
    // CONFIGURACIÓN E IMÁGENES
    // ============================================================

    /**
     * Actualiza la configuración de un Youtuber.
     *
     * <p>
     * Los parámetros de imagen son InputStream porque las imágenes llegan
     * desde un componente Upload de Vaadin.
     * </p>
     *
     * <p>
     * Si un parámetro es null, significa que el usuario no ha cambiado
     * esa imagen.
     * </p>
     */
    public void actualizarConfiguracion(
            String login,
            String password,
            InputStream avatar,
            String avatarNombre,
            InputStream fondo,
            String fondoNombre) {

        /*
         * Primero obtenemos el usuario que queremos modificar.
         */
        Youtuber usuario =
                findYoutuberById(login);


        // -------------------------
        // Contraseña
        // -------------------------

        /*
         * Solo modificamos la contraseña si el usuario ha introducido
         * una nueva.
         *
         * Así podemos cambiar únicamente el avatar o el banner sin
         * modificar accidentalmente la contraseña.
         */
        if (password != null &&
                !password.isBlank()) {

            usuario.setPassword(
                    passwordEncoder.encode(password));
        }


        // -------------------------
        // Avatar
        // -------------------------

        /*
         * Si se ha subido un nuevo avatar, guardamos físicamente
         * el archivo y almacenamos en la BD la ruta que permite acceder
         * a él.
         */
        if (avatar != null) {

            String rutaAvatar =
                    guardarImagen(
                            avatar,
                            avatarNombre,
                            login,
                            "avatar");

            usuario.setFotoPerfil(
                    rutaAvatar);
        }


        // -------------------------
        // Fondo
        // -------------------------

        /*
         * Lo mismo hacemos con el banner.
         */
        if (fondo != null) {

            String rutaFondo =
                    guardarImagen(
                            fondo,
                            fondoNombre,
                            login,
                            "banner");

            usuario.setBanner(
                    rutaFondo);
        }

        /*
         * Guardamos todos los cambios realizados en el usuario.
         */
        repository.save(usuario);
    }


    /**
     * Guarda una imagen en el sistema de archivos.
     *
     * <p>
     * Las imágenes no se guardan directamente en la entidad como
     * datos binarios. Guardamos el archivo en la carpeta "uploads"
     * y almacenamos en la base de datos la ruta que permite acceder
     * posteriormente a él.
     * </p>
     */
    private String guardarImagen(
            InputStream inputStream,
            String nombreOriginal,
            String login,
            String tipo) {

        try {

            /*
             * Creamos una carpeta específica para cada usuario:
             *
             * uploads/
             *     usuarios/
             *         pepe/
             *             avatar.jpg
             *             banner.jpg
             */
            Path directorio =
                    Paths.get(
                            "uploads",
                            "usuarios",
                            login);

            /*
             * createDirectories() crea toda la estructura necesaria
             * si todavía no existe.
             */
            Files.createDirectories(
                    directorio);


            // -------------------------
            // Obtener extensión
            // -------------------------

            String extension = "";

            /*
             * Conservamos la extensión original del archivo:
             *
             * foto.jpg  -> .jpg
             * imagen.png -> .png
             */
            if (nombreOriginal != null
                    && nombreOriginal.contains(".")) {

                extension =
                        nombreOriginal.substring(
                                nombreOriginal.lastIndexOf("."));
            }


            // -------------------------
            // Nombre del archivo
            // -------------------------

            /*
             * Utilizamos nombres fijos para evitar tener que almacenar
             * nombres aleatorios en la base de datos.
             *
             * Por ejemplo:
             *
             * avatar.jpg
             * banner.png
             */
            String nombreArchivo =
                    tipo + extension;

            Path archivo =
                    directorio.resolve(
                            nombreArchivo);


            // -------------------------
            // Guardar archivo
            // -------------------------

            /*
             * Copiamos el contenido del InputStream al archivo.
             *
             * REPLACE_EXISTING permite sustituir la imagen anterior
             * si el usuario vuelve a subir una nueva.
             */
            Files.copy(
                    inputStream,
                    archivo,
                    StandardCopyOption.REPLACE_EXISTING);


            // -------------------------
            // Ruta almacenada en BD
            // -------------------------

            /*
             * En la base de datos NO guardamos la ruta física:
             *
             * uploads/usuarios/pepe/avatar.jpg
             *
             * sino la ruta mediante la que nuestra aplicación web
             * puede acceder al recurso:
             *
             * /uploads/usuarios/pepe/avatar.jpg
             */
            return "/uploads/usuarios/"
                    + login
                    + "/"
                    + nombreArchivo;

        } catch (IOException e) {

            /*
             * Convertimos la IOException en una RuntimeException
             * para indicar que la operación no ha podido realizarse.
             */
            throw new RuntimeException(
                    "Error al guardar la imagen",
                    e);
        }
    }


    // ============================================================
    // BÚSQUEDAS
    // ============================================================

    /**
     * Obtiene los Youtubers que tienen alguna denuncia.
     *
     * @return lista de Youtubers denunciados
     */
    public List<Youtuber> buscarDenunciados() {

        /*
         * Primero obtenemos todos los usuarios.
         */
        List<Youtuber> denunciados =
                repository.findAll();

        /*
         * Nos quedamos solamente con aquellos usuarios que tienen
         * al menos una denuncia.
         */
        return denunciados.stream()
                .filter(youtuber ->
                        youtuber
                                .getDenunciado_por()
                                .size() > 0)
                .toList();
    }


    // ============================================================
    // SEGUIMIENTO DE USUARIOS
    // ============================================================

    /**
     * Hace que un Youtuber siga a otro.
     *
     * @param loginSeguido usuario que será seguido
     * @param loginSeguidor usuario que realiza el seguimiento
     */
    public void seguirUsuario(
            String loginSeguido,
            String loginSeguidor) {

        Youtuber seguidor =
                findYoutuberById(
                        loginSeguidor);

        Youtuber seguido =
                findYoutuberById(
                        loginSeguido);

        /*
         * seguido_por es el lado propietario de la relación en nuestro
         * modelo para esta operación.
         *
         * Añadimos el seguidor a la colección del usuario seguido.
         */
        seguido.getSeguido_por()
                .add(seguidor);

        /*
         * Guardamos el lado que hemos modificado.
         */
        repository.save(seguido);
    }


    /**
     * Deja de seguir a un Youtuber.
     */
    public void dejardeseguirUsuario(
            String loginSeguido,
            String loginSeguidor) {

        Youtuber seguidor =
                findYoutuberById(
                        loginSeguidor);

        Youtuber seguido =
                findYoutuberById(
                        loginSeguido);

        /*
         * seguido_por es el lado que estamos modificando.
         *
         * Utilizamos removeIf() en lugar de remove(seguidor).
         *
         * Esto es especialmente útil con las colecciones generadas por
         * Visual Paradigm y con entidades gestionadas por Hibernate,
         * ya que podemos eliminar el elemento comparando explícitamente
         * su identificador.
         */
        seguido.getSeguido_por()
                .removeIf(o ->
                        ((Youtuber) o)
                                .getLogin()
                                .equals(
                                        seguidor.getLogin()));

        repository.save(seguido);
    }


    // ============================================================
    // BLOQUEAR / DESBLOQUEAR
    // ============================================================

    /**
     * Bloquea un Youtuber.
     */
    public void bloquearUsuario(
            String loginYoutuber) {

        Youtuber usuario =
                findYoutuberById(
                        loginYoutuber);

        usuario.setBloqueado(true);

        repository.save(usuario);
    }


    /**
     * Desbloquea un Youtuber.
     */
    public void desbloquearUsuario(
            String loginYoutuber) {

        Youtuber usuario =
                findYoutuberById(
                        loginYoutuber);

        usuario.setBloqueado(false);

        repository.save(usuario);
    }


    // ============================================================
    // DENUNCIAS
    // ============================================================

    /**
     * Denuncia a un Youtuber.
     *
     * @param loginDenunciante usuario que realiza la denuncia
     * @param loginDenunciado usuario que recibe la denuncia
     */
    public void denunciarUsuario(
            String loginDenunciante,
            String loginDenunciado) {

        Youtuber denunciante =
                findYoutuberById(
                        loginDenunciante);

        Youtuber denunciado =
                findYoutuberById(
                        loginDenunciado);

        /*
         * denunciado_por es el lado propietario que estamos utilizando
         * para modificar la relación.
         */
        denunciante.getDenunciado_por()
                .add(denunciado);

        repository.save(denunciante);
    }


    /**
     * Elimina una denuncia realizada anteriormente.
     */
    public void quitardenunciaUsuario(
            String loginDenunciante,
            String loginDenunciado) {

        Youtuber denunciante =
                findYoutuberById(
                        loginDenunciante);

        Youtuber denunciado =
                findYoutuberById(
                        loginDenunciado);

        /*
         * denunciado_por es la colección que modificamos.
         *
         * login es un String, por lo que utilizamos equals() para
         * comparar los identificadores.
         */
        denunciante.getDenunciado_por()
                .removeIf(o ->
                        ((Youtuber) o)
                                .getLogin()
                                .equals(
                                        denunciado.getLogin()));

        repository.save(denunciante);
    }


    // ============================================================
    // ME GUSTA / NO ME GUSTA
    // ============================================================

    /**
     * Añade un vídeo a los vídeos que le gustan al usuario.
     */
    public void likeVideo(
            Youtuber usuario,
            Video video) {

        /*
         * le_gusta es la colección que utilizamos para modificar
         * la relación.
         *
         * Añadimos el vídeo a los vídeos que le gustan al usuario.
         */
        usuario.getLe_gusta()
                .add(video);

        repository.save(usuario);
    }


    /**
     * Quita un vídeo de los vídeos que le gustan al usuario.
     */
    public void dislikeVideo(
            Youtuber usuario,
            Video video) {

        /*
         * getId() devuelve un int.
         *
         * Al tratarse de un tipo primitivo utilizamos == para comparar
         * los identificadores.
         *
         * No sería correcto intentar:
         *
         *     getId().equals(...)
         *
         * porque int no es un objeto y no tiene métodos.
         */
        usuario.getLe_gusta()
                .removeIf(o ->
                        ((Video) o)
                                .getId()
                                == video.getId());

        repository.save(usuario);
    }


    /**
     * Elimina específicamente un "me gusta" de un usuario.
     *
     * <p>
     * Es equivalente a dislikeVideo() en esta implementación.
     * Se mantiene como método independiente porque puede resultar útil
     * cuando queremos expresar explícitamente que estamos eliminando
     * una relación.
     * </p>
     */
    public void eliminarMeGusta(
            Youtuber usuario,
            Video video) {

        /*
         * Comparamos los IDs porque getId() devuelve un int.
         */
        usuario.getLe_gusta()
                .removeIf(o ->
                        ((Video) o)
                                .getId()
                                == video.getId());

        repository.save(usuario);
    }
}