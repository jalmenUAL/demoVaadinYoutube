package com.example.demo.components;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.example.demo.repositories.RepositorioVideo;
import com.example.demo.tables.Video;
import com.example.demo.tables.Youtuber;

@Service
@Profile("real")

/**
 * Clase encargada de gestionar las operaciones relacionadas con los vídeos.
 *
 * <p>
 * El acceso a la base de datos se realiza mediante {@link RepositorioVideo},
 * un repositorio de Spring Data JPA.
 * </p>
 *
 * <p>
 * Esta clase contiene la lógica de negocio relacionada con los vídeos:
 * buscar vídeos, obtener los últimos vídeos, buscar vídeos relacionados,
 * publicar vídeos y eliminarlos.
 * </p>
 */
public class BD_Videos {

    public Vector<Video> _videos = new Vector<Video>();

    /*
     * Repositorio de Spring Data JPA utilizado para acceder a los vídeos
     * almacenados en la base de datos.
     */
    private RepositorioVideo videorepository;

    /**
     * Constructor.
     *
     * El repositorio se recibe mediante inyección de dependencias.
     * Spring se encarga de crear el repositorio.
     */
    public BD_Videos(RepositorioVideo videorepository) {

        this.videorepository = videorepository;
    }

    /**
     * Busca un vídeo por su identificador.
     *
     * @param idVideo identificador del vídeo
     * @return vídeo encontrado
     * @throws RuntimeException si no existe un vídeo con ese identificador
     */
    public Video findVideoById(Integer idVideo) {

        /*
         * findById() devuelve un Optional porque el vídeo puede no existir.
         *
         * Utilizamos orElseThrow() para indicar explícitamente qué hacer
         * cuando no encontramos el vídeo.
         */
        return videorepository.findById(idVideo)

                /*
                 * Si no existe el vídeo, se lanza una excepción.
                 *
                 * Esto evita devolver null y tener posteriormente un
                 * NullPointerException al intentar utilizar el vídeo.
                 */
                .orElseThrow(() -> new RuntimeException(
                        "Video no encontrado"));
    }

    /**
     * Obtiene todos los vídeos almacenados en la base de datos.
     *
     * @return lista con todos los vídeos
     */
    public List<Video> getAllVideos() {

        /*
         * findAll() es proporcionado directamente por Spring Data JPA.
         */
        return videorepository.findAll();
    }

    /**
     * Obtiene como máximo los 10 primeros vídeos.
     *
     */
    public java.util.List<Video> getUltimosVideos() {

        /*
         * Primero obtenemos todos los vídeos.
         */
        java.util.List<Video> UltimosVideos = videorepository.findAll();

        /*
         * Si hay más de 10, nos quedamos solamente con los 10 primeros.
         */
        if (UltimosVideos.size() > 10) {

            /*
             * subList(0, 10) devuelve los elementos desde la posición 0
             * hasta la posición 9.
             *
             * El segundo índice es EXCLUSIVO.
             *
             * Por tanto:
             *
             * subList(0, 10)
             *
             * significa "elementos 0, 1, 2, ..., 9".
             */
            UltimosVideos = UltimosVideos.subList(0, 10);
        }

        return UltimosVideos;
    }

    /**
     * Busca vídeos relacionados con otro vídeo.
     *
     * <p>
     * En este ejemplo consideramos relacionados aquellos vídeos cuyo título
     * contiene alguna de las palabras que aparecen en el título del vídeo
     * original.
     * </p>
     *
     * @param idVideo identificador del vídeo del que queremos obtener
     *                vídeos relacionados
     * @return lista de vídeos relacionados
     */
    public List<Video> getVideosRelacionados(Integer idVideo) {

        /*
         * Primero obtenemos el vídeo original.
         */
        Video videob = findVideoById(idVideo);

        /*
         * Dividimos el título en palabras.
         *
         * Por ejemplo:
         *
         * "Los mejores vídeos de Java"
         *
         * se convierte aproximadamente en:
         *
         * ["los", "mejores", "vídeos", "de", "java"]
         *
         * split("\\s+") separa utilizando uno o más espacios.
         *
         * map(String::toLowerCase) convierte cada palabra a minúsculas
         * para que posteriormente la comparación no distinga entre
         * mayúsculas y minúsculas.
         */
        List<String> palabras = Arrays.stream(
                videob.getTitulo().split("\\s+"))
                .map(String::toLowerCase)
                .toList();

        /*
         * Obtenemos todos los vídeos para poder buscar cuáles están
         * relacionados.
         */
        List<Video> busqueda = videorepository.findAll();

        /*
         * Stream permite procesar la colección de forma declarativa:
         *
         * "qué elementos quiero"
         *
         * en lugar de indicar manualmente cómo recorrer la lista.
         */
        return busqueda.stream()

                /*
                 * Excluimos el propio vídeo.
                 *
                 * getId() devuelve un int, por eso podemos utilizar ==.
                 *
                 * Si fueran objetos Integer, normalmente utilizaríamos
                 * equals() para comparar sus valores.
                 */
                .filter(video -> !(video.getId() == videob.getId()))

                /*
                 * Comprobamos si el título del vídeo contiene al menos
                 * una de las palabras del título original.
                 *
                 * anyMatch() devuelve true en cuanto encuentra una
                 * coincidencia.
                 */
                .filter(video -> {

                    String titulo = video.getTitulo().toLowerCase();

                    return palabras.stream()
                            .anyMatch(titulo::contains);
                })

                /*
                 * toList() convierte el Stream resultante en una lista.
                 */
                .toList();
    }

    /**
     * Busca vídeos cuyo título contenga el texto indicado.
     *
     * @param texto texto que queremos buscar
     * @return vídeos cuyo título contiene el texto
     */
    public java.util.List<Video> buscar(String texto) {

        /*
         * Obtenemos los vídeos y utilizamos un Stream para filtrar
         * solamente aquellos cuyo título contiene el texto buscado.
         */
        List<Video> busqueda = videorepository.findAll();

        return busqueda.stream()

                /*
                 * filter() conserva únicamente los elementos para los
                 * que la condición devuelve true.
                 */
                .filter(video -> video.getTitulo().contains(texto))

                /*
                 * Convertimos el Stream de nuevo en una lista.
                 */
                .toList();
    }

    /**
     * Publica un nuevo vídeo.
     *
     * @param usuario youtuber que publica el vídeo
     * @param titulo  título del vídeo
     * @param url     URL del vídeo
     */
    public void publicarVideo(
            Youtuber usuario,
            String titulo,
            String url) {

        /*
         * Creamos una nueva entidad Video.
         *
         * Todavía existe únicamente en memoria.
         */
        Video video = new Video();

        /*
         * Establecemos los datos del vídeo.
         */
        video.setTitulo(titulo);
        video.setUrl(url);
        video.setFecha(Date.from(
                LocalDate.now()
                        .atStartOfDay(ZoneId.systemDefault())
                        .toInstant()));

        /*
         * Indicamos qué Youtuber es el propietario del vídeo.
         *
         * Esto corresponde a la relación ManyToOne entre Video y Youtuber.
         */
        video.setEs_de(usuario);

        /*
         * save() persiste el nuevo vídeo en la base de datos.
         *
         * Al tener el ID generado automáticamente, Hibernate se encargará
         * de obtener el identificador correspondiente.
         */
        videorepository.save(video);
    }

    /**
     * Elimina un vídeo.
     *
     * @param idVideo identificador del vídeo que queremos eliminar
     */
    public void borrarVideo(Integer idVideo) {

        /*
         * Spring Data permite eliminar directamente utilizando el ID.
         *
         * Las relaciones del vídeo deben estar correctamente configuradas
         * en JPA para que el borrado no provoque errores de integridad
         * referencial.
         *
         * En nuestro caso, por ejemplo, los comentarios y las relaciones
         * de "me gusta" tienen que estar correctamente gestionados cuando
         * se elimina un vídeo.
         */
        videorepository.deleteById(idVideo);
    }
}