package com.example.demo.components;

import java.util.Vector;

import org.springframework.stereotype.Service;

import com.example.demo.repositories.RepositorioComentario;
import com.example.demo.tables.Comentario;
import com.example.demo.tables.Video;
import com.example.demo.tables.Youtuber;

@Service
/**
 * Clase encargada de gestionar las operaciones relacionadas con los comentarios.
 *
 * <p>
 * Los comentarios se almacenan en la base de datos mediante
 * {@link RepositorioComentario}, que es un repositorio de Spring Data JPA.
 * </p>
 *
 * <p>
 * Esta clase actúa como una capa intermedia entre las vistas de la aplicación
 * y el repositorio. De esta forma, las vistas no necesitan conocer cómo se
 * guardan o eliminan los comentarios en la base de datos.
 * </p>
 */
public class BD_Comentarios {

    

    /*
     * Colección generada por Visual Paradigm.
     *
     * En nuestra implementación actual no es necesario mantener aquí todos
     * los comentarios, ya que los comentarios se consultan y almacenan
     * mediante el repositorio.
     */
    public Vector<Comentario> _comentarios =
            new Vector<Comentario>();

    /*
     * Repositorio de Spring Data JPA.
     *
     * Nos proporciona operaciones como:
     *
     *     save()
     *     deleteById()
     *     findById()
     *     findAll()
     *
     * sin tener que escribir nosotros las consultas SQL básicas.
     */
    private RepositorioComentario repository;

    /**
     * Constructor.
     *
     * Recibimos el repositorio mediante inyección de dependencias.
     *
     * No hacemos:
     *
     *     repository = new RepositorioComentario();
     *
     * porque los repositorios de Spring son gestionados por el propio
     * framework.
     */
    public BD_Comentarios(
            RepositorioComentario comentariosRepository) {

        repository = comentariosRepository;
    }

    /**
     * Publica un nuevo comentario sobre un vídeo.
     *
     * @param usuario usuario que escribe el comentario
     * @param video vídeo sobre el que se escribe
     * @param contenido texto del comentario
     */
    public void publicarComentario(
            Youtuber usuario,
            Video video,
            String contenido) {

        /*
         * Creamos una nueva entidad Comentario.
         *
         * En este momento el objeto solamente existe en memoria.
         * Todavía NO está guardado en la base de datos.
         */
        Comentario c = new Comentario();

        /*
         * Indicamos quién ha escrito el comentario.
         *
         * Esto corresponde a la relación entre Comentario y Youtuber.
         */
        c.setEscrito_por(usuario);

        /*
         * Guardamos el texto introducido por el usuario.
         */
        c.setTexto(contenido);

        /*
         * Indicamos sobre qué vídeo se ha escrito el comentario.
         *
         * Así establecemos la relación:
         *
         *     Comentario -----> Video
         */
        c.setSobre(video);

        /*
         * save() hace que Spring Data JPA persista la entidad.
         *
         * Si es un objeto nuevo, normalmente se realizará un INSERT.
         * Si ya fuese una entidad existente, podría realizarse una
         * actualización.
         */
        repository.save(c);
    }

    /**
     * Elimina un comentario de la base de datos.
     *
     * @param idComentario identificador del comentario que queremos eliminar
     */
    public void eliminarComentario(Integer idComentario) {

        /*
         * No necesitamos buscar primero el comentario para después
         * eliminarlo.
         *
         * deleteById() permite solicitar directamente su eliminación
         * utilizando su identificador.
         *
         * Equivale conceptualmente a:
         *
         *     DELETE FROM Comentario WHERE id = idComentario
         *
         * aunque la operación real la gestiona JPA/Hibernate.
         */
        repository.deleteById(idComentario);
    }
}