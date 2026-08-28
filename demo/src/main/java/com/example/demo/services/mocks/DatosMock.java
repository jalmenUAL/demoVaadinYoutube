package com.example.demo.services.mocks;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.example.demo.tables.Comentario;
import com.example.demo.tables.Video;
import com.example.demo.tables.Youtuber;

@Component

public class DatosMock {

    /*
     * =========================================================
     * DATOS DEL PROTOTIPO
     * =========================================================
     */

    public final Set<Youtuber> youtubers;
    public final Set<Video> videos;

    /**
     * Crea el escenario inicial utilizado durante
     * el prototipado de la aplicación.
     */
    public DatosMock() {

        youtubers = new HashSet<>();
        videos = new HashSet<>();

        cargarDatos();
    }

    /**
     * Carga los datos iniciales del prototipo.
     */
    private void cargarDatos() {

        // =========================================================
        // YOUTUBERS
        // =========================================================

        Youtuber ana = new Youtuber();
        ana.setLogin("ana");
        ana.setPassword("1234");
        ana.setBanner("https://picsum.photos/1200/400");
        ana.setBloqueado(false);
        ana.setFotoPerfil("https://api.dicebear.com/9.x/adventurer/svg?seed=ana");

        Youtuber juan = new Youtuber();
        juan.setLogin("juan");
        juan.setPassword("1234");
        juan.setBanner("https://picsum.photos/1200/400");
        juan.setBloqueado(false);
        juan.setFotoPerfil("https://api.dicebear.com/9.x/adventurer/svg?seed=juan");

        Youtuber maria = new Youtuber();
        maria.setLogin("maria");
        maria.setPassword("1234");
        maria.setBanner("https://picsum.photos/1200/400");
        maria.setBloqueado(false);
        maria.setFotoPerfil("https://api.dicebear.com/9.x/adventurer/svg?seed=maria");

        youtubers.add(ana);
        youtubers.add(juan);
        youtubers.add(maria);

        // =========================================================
        // VIDEOS
        // =========================================================

        Video video1 = crearVideo(
                1,
                "Introducción a Java",
                "https://youtu.be/lTrcM0iP7O4?si=z_tHfQt05hueTuj0",
                ana);

        Video video2 = crearVideo(
                2,
                "Programación orientada a objetos",
                "https://youtu.be/lTrcM0iP7O4?si=z_tHfQt05hueTuj0",
                ana);

        Video video3 = crearVideo(
                3,
                "Patrones de diseño",
                "https://youtu.be/lTrcM0iP7O4?si=z_tHfQt05hueTuj0",
                juan);

        Video video4 = crearVideo(
                4,
                "Spring Boot desde cero",
                "https://youtu.be/lTrcM0iP7O4?si=z_tHfQt05hueTuj0",
                maria);

        Video video5 = crearVideo(
                5,
                "Introducción a JPA",
                "https://youtu.be/lTrcM0iP7O4?si=z_tHfQt05hueTuj0",
                juan);

        Comentario c1 = new Comentario();
c1.setEscrito_por(maria);
c1.setTexto("Estupendo");
video1.getTiene_comentarios().add(c1);

Comentario c2 = new Comentario();
c2.setEscrito_por(juan);
c2.setTexto("Muy bien explicado, me ha servido mucho.");
video1.getTiene_comentarios().add(c2);

Comentario c3 = new Comentario();
c3.setEscrito_por(ana);
c3.setTexto("¿Podrías hacer otro vídeo sobre este tema?");
video1.getTiene_comentarios().add(c3);


Comentario c4 = new Comentario();
c4.setEscrito_por(maria);
c4.setTexto("Muy interesante.");
video2.getTiene_comentarios().add(c4);

Comentario c5 = new Comentario();
c5.setEscrito_por(juan);
c5.setTexto("La explicación del final está genial.");
video2.getTiene_comentarios().add(c5);


Comentario c6 = new Comentario();
c6.setEscrito_por(ana);
c6.setTexto("No conocía esta forma de hacerlo.");
video3.getTiene_comentarios().add(c6);

Comentario c7 = new Comentario();
c7.setEscrito_por(maria);
c7.setTexto("Buen vídeo, gracias por compartirlo.");
video3.getTiene_comentarios().add(c7);

Comentario c8 = new Comentario();
c8.setEscrito_por(juan);
c8.setTexto("Me ha quedado mucho más claro ahora.");
video3.getTiene_comentarios().add(c8);


Comentario c9 = new Comentario();
c9.setEscrito_por(ana);
c9.setTexto("Muy buen contenido.");
video4.getTiene_comentarios().add(c9);

Comentario c10 = new Comentario();
c10.setEscrito_por(maria);
c10.setTexto("Esperando el siguiente vídeo.");
video4.getTiene_comentarios().add(c10);


Comentario c11 = new Comentario();
c11.setEscrito_por(juan);
c11.setTexto("Justo lo que estaba buscando.");
video5.getTiene_comentarios().add(c11);

Comentario c12 = new Comentario();
c12.setEscrito_por(ana);
c12.setTexto("Perfectamente explicado.");
video5.getTiene_comentarios().add(c12);

         

        videos.add(video1);
        videos.add(video2);
        videos.add(video3);
        videos.add(video4);
        videos.add(video5);



        // =========================================================
        // RELACIÓN YOUTUBER → VIDEOS
        // =========================================================

        ana.getHa_publicado().add(video1);
        ana.getHa_publicado().add(video2);

        juan.getHa_publicado().add(video3);
        juan.getHa_publicado().add(video5);

        maria.getHa_publicado().add(video4);

        // =========================================================
        // SEGUIMIENTOS
        // =========================================================

        /*
         * Ana sigue a Juan y María.
         */
        ana.getSeguido_por().add(juan);
        ana.getSeguido_por().add(maria);

        juan.getSeguidor_de().add(ana);
        maria.getSeguidor_de().add(ana);

        /*
         * Juan sigue a María.
         */
        juan.getSeguido_por().add(maria);
        maria.getSeguidor_de().add(juan);

        // =========================================================
        // ME GUSTA
        // =========================================================

        /*
         * Ana indica que le gustan los vídeos de Juan.
         */
        ana.getLe_gusta().add(video3);
        ana.getLe_gusta().add(video5);

        video3.getLe_gusta_a().add(ana);
        video5.getLe_gusta_a().add(ana);

        /*
         * María indica que le gusta un vídeo de Ana.
         */
        maria.getLe_gusta().add(video1);
        video1.getLe_gusta_a().add(maria);

        // =========================================================
        // DENUNCIAS
        // =========================================================

        /*
         * María ha denunciado a Juan.
         *
         * Esto permite que el AdministradorMock tenga
         * información que mostrar desde el principio.
         */
        maria.getHa_denunciado_a().add(juan);
        juan.getDenunciado_por().add(maria);
    }

    /**
     * Crea un vídeo del escenario del prototipo.
     */
    private Video crearVideo(
            int id,
            String titulo,
            String url,
            Youtuber youtuber) {

        Video video = new Video();

        /*
         * ID ficticio utilizado únicamente durante
         * el prototipado.
         *
         * No se establece porque el ID es generado
         * automáticamente por JPA.
         */
        video.setId(id);

        video.setTitulo(titulo);
        video.setUrl(url);
        video.setFecha(new Date());
        video.setEs_de(youtuber);



        return video;
    }
}