package com.example.demo.services.mocks;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.example.demo.services.interfaces.iYoutuber;
import com.example.demo.tables.Video;
import com.example.demo.tables.Youtuber;



@Service
@Profile("mock")

public class YoutuberMock extends RegistradoMock implements iYoutuber {

    public YoutuberMock(DatosMock datosMock) {
        super(datosMock);

    }

    @Override
    public Video findVideoById(int idVideo) {

        return videos.stream()
                .filter(video -> video.getId() == idVideo)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void publicarVideo(
            String loginYoutuber,
            String titulo,
            String url) {

        Youtuber youtuber = buscarYoutuber(loginYoutuber);

        if (youtuber == null) {
            return;
        }

        Video video = new Video();

        /*
         * En el mock generamos un identificador ficticio.
         */
        int nuevoId = videos.stream()
                .mapToInt(Video::getId)
                .max()
                .orElse(0) + 1;

        video.setId(nuevoId);
        video.setTitulo(titulo);
        video.setUrl(url);
        video.setFecha(new Date());
        video.setEs_de(youtuber);

        videos.add(video);

        youtuber.getHa_publicado().add(video);
    }

    @Override
    public void publicarComentario(
            String loginYoutuber,
            String idVideo,
            String contenido) {

        Video video = findVideoById(Integer.parseInt(idVideo));

        Youtuber youtuber = buscarYoutuber(loginYoutuber);

        if (video == null || youtuber == null) {
            return;
        }

        /*
         * Aquí se crearía un Comentario utilizando la entidad
         * generada por Visual Paradigm y se asociaría al vídeo
         * y al usuario.
         *
         * La implementación exacta depende de los atributos
         * de tu clase Comentario.
         */
    }

    @Override
    public void seguirUsuario(
            String loginSeguido,
            String loginSeguidor) {

        Youtuber seguido = buscarYoutuber(loginSeguido);

        Youtuber seguidor = buscarYoutuber(loginSeguidor);

        if (seguido == null || seguidor == null) {
            return;
        }

        /*
         * El seguidor pasa a seguir al usuario.
         */
        seguidor.getSeguido_por().add(seguido);

        /*
         * Y el usuario seguido obtiene un seguidor.
         */
        seguido.getSeguidor_de().add(seguidor);
    }

    @Override
    public void dejardeseguirUsuario(
            String loginSeguido,
            String loginSeguidor) {

        Youtuber seguido = buscarYoutuber(loginSeguido);

        Youtuber seguidor = buscarYoutuber(loginSeguidor);

        if (seguido == null || seguidor == null) {
            return;
        }

        seguidor.getSeguido_por().remove(seguido);
        seguido.getSeguidor_de().remove(seguidor);
    }

    @Override
    public void likeVideo(
            String loginYoutuber,
            Integer idVideo) {

        Youtuber youtuber = buscarYoutuber(loginYoutuber);

        Video video = findVideoById(idVideo);

        if (youtuber == null || video == null) {
            return;
        }

        youtuber.getLe_gusta().add(video);
        video.getLe_gusta_a().add(youtuber);
    }

    @Override
    public void dislikeVideo(
            String loginYoutuber,
            Integer idVideo) {

        Youtuber youtuber = buscarYoutuber(loginYoutuber);

        Video video = findVideoById(idVideo);

        if (youtuber == null || video == null) {
            return;
        }

        youtuber.getLe_gusta().remove(video);
        video.getLe_gusta_a().remove(youtuber);
    }

    @Override
    public void denunciarUsuario(
            String loginDenunciante,
            String loginDenunciado) {

        Youtuber denunciante = buscarYoutuber(loginDenunciante);

        Youtuber denunciado = buscarYoutuber(loginDenunciado);

        if (denunciante == null || denunciado == null) {
            return;
        }

        denunciante
                .getHa_denunciado_a()
                .add(denunciado);

        denunciado
                .getDenunciado_por()
                .add(denunciante);
    }

    @Override
    public void quitardenunciaUsuario(
            String loginDenunciante,
            String loginDenunciado) {

        Youtuber denunciante = buscarYoutuber(loginDenunciante);

        Youtuber denunciado = buscarYoutuber(loginDenunciado);

        if (denunciante == null || denunciado == null) {
            return;
        }

        denunciante
                .getHa_denunciado_a()
                .remove(denunciado);

        denunciado
                .getDenunciado_por()
                .remove(denunciante);
    }

    /**
     * Busca un Youtuber en los datos del prototipo.
     */
    private Youtuber buscarYoutuber(
            String login) {

        return youtubers.stream()
                .filter(youtuber -> youtuber.getLogin().equals(login))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Video> buscar(String texto) {

        if (texto == null || texto.isBlank()) {
            return new ArrayList<>();
        }

        String textoBusqueda = texto.toLowerCase();

        return videos
                .stream()
                .filter(video -> video.getTitulo() != null &&
                        video.getTitulo()
                                .toLowerCase()
                                .contains(textoBusqueda))
                .toList();
    }

    @Override
    public Video findVideoById(Integer idVideo) {

        if (idVideo == null) {
            return null;
        }

        return videos
                .stream()
                .filter(video -> video.getId() == idVideo)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Youtuber findYoutuberById(
            String loginYoutuber) {

        if (loginYoutuber == null) {
            return null;
        }

        return youtubers
                .stream()
                .filter(youtuber -> loginYoutuber.equals(
                        youtuber.getLogin()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Video> getUltimosVideos() {

        return (List<Video>) videos;
    }

    @Override
    public List<Video> getVideosRelacionados(
            Integer idVideo) {

        Video video = findVideoById(idVideo);

        if (video == null || video.getEs_de() == null) {
            return new ArrayList<>();
        }

        Youtuber autor = video.getEs_de();

        return videos
                .stream()
                .filter(v -> v.getEs_de() == autor &&
                        v.getId() != video.getId())
                .toList();
    }

    @Override
    public void actualizarConfiguracion(
            String login,
            String password,
            InputStream avatar,
            String avatarNombre,
            InputStream fondo,
            String fondoNombre) {

        Youtuber youtuber = buscarYoutuber(login);

        if (youtuber == null) {
            return;
        }

        if (password != null && !password.isBlank()) {
            youtuber.setPassword(password);
        }

        /*
         * En el prototipo no almacenamos físicamente los archivos.
         * Simulamos la actualización del fondo utilizando su nombre.
         */
        if (fondoNombre != null && !fondoNombre.isBlank()) {
            youtuber.setBanner(fondoNombre);
        }
    }
}
