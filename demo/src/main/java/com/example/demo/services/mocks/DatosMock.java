package com.example.demo.services.mocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.tables.Video;
import com.example.demo.tables.Youtuber;

@Component
public class DatosMock {

    /*
     * =========================================================
     * DATOS DEL PROTOTIPO
     * =========================================================
     */

    public final List<Youtuber> youtubers;
    public final List<Video> videos;

    /**
     * Crea el escenario inicial utilizado durante
     * el prototipado de la aplicación.
     */
    public DatosMock() {

        youtubers = new ArrayList<>();
        videos = new ArrayList<>();

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
        ana.setBanner("banner-ana.jpg");
        ana.setBloqueado(false);

        Youtuber juan = new Youtuber();
        juan.setLogin("juan");
        juan.setPassword("1234");
        juan.setBanner("banner-juan.jpg");
        juan.setBloqueado(false);

        Youtuber maria = new Youtuber();
        maria.setLogin("maria");
        maria.setPassword("1234");
        maria.setBanner("banner-maria.jpg");
        maria.setBloqueado(false);

        youtubers.add(ana);
        youtubers.add(juan);
        youtubers.add(maria);

        // =========================================================
        // VIDEOS
        // =========================================================

        Video video1 = crearVideo(
                1,
                "Introducción a Java",
                "https://ejemplo.com/java",
                ana);

        Video video2 = crearVideo(
                2,
                "Programación orientada a objetos",
                "https://ejemplo.com/poo",
                ana);

        Video video3 = crearVideo(
                3,
                "Patrones de diseño",
                "https://ejemplo.com/patrones",
                juan);

        Video video4 = crearVideo(
                4,
                "Spring Boot desde cero",
                "https://ejemplo.com/spring",
                maria);

        Video video5 = crearVideo(
                5,
                "Introducción a JPA",
                "https://ejemplo.com/jpa",
                juan);

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
         */
        // video.setId(id);

        video.setTitulo(titulo);
        video.setUrl(url);
        video.setFecha(new Date());
        video.setEs_de(youtuber);

        return video;
    }

}