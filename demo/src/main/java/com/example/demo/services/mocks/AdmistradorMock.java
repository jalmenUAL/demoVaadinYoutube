package com.example.demo.services.mocks;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.example.demo.services.interfaces.iAdministrador;
import com.example.demo.tables.Comentario;
import com.example.demo.tables.Video;
import com.example.demo.tables.Youtuber;

@Service
@Profile("mock")
public class AdmistradorMock extends RegistradoMock implements iAdministrador {

    /*
     * =========================================================
     * DATOS DEL PROTOTIPO
     * =========================================================
     *
     * El Mock utiliza el escenario común de DatosMock.
     *
     * De esta forma, todos los servicios Mock trabajan sobre
     * los mismos objetos en memoria y las vistas pueden utilizar
     * datos de ejemplo sin necesidad de acceder a la BD.
     */
    private final DatosMock datos;


    /**
     * Constructor.
     *
     * <p>
     * DatosMock se inyecta mediante Spring y contiene los datos
     * ficticios utilizados durante el prototipado.
     *
     * <p>
     * Se pasa también a RegistradoMock porque AdministradorMock
     * hereda la funcionalidad correspondiente al actor Registrado.
     */
    public AdmistradorMock(DatosMock datos) {

        super(datos);

        this.datos = datos;
    }


    /**
     * Elimina un comentario del escenario Mock.
     *
     * <p>
     * Se recorren los vídeos y se elimina el comentario cuyo
     * identificador coincide con el recibido.
     *
     * <p>
     * En el Mock la operación se realiza directamente sobre
     * los objetos que están en memoria. No interviene ninguna BD.
     *
     * @param idComentario identificador del comentario que se
     *                     quiere eliminar
     */
    @Override
    public void eliminarComentario(Integer idComentario) {

        datos.videos.forEach(video ->
            video.getTiene_comentarios().removeIf(
                comentario -> ((Comentario) comentario).getId() == idComentario
            )
        );
    }


    /**
     * Obtiene los Youtubers que han sido denunciados.
     *
     * <p>
     * Un Youtuber se considera denunciado si su colección
     * {@code denunciado_por} contiene al menos una denuncia.
     *
     * <p>
     * Los datos se obtienen directamente de DatosMock.
     *
     * @return lista de Youtubers denunciados
     */
    @Override
    public List<Youtuber> buscarDenunciados() {

        return datos.youtubers.stream()
                .filter(y -> !y.getDenunciado_por().isEmpty())
                .collect(Collectors.toList());
    }


    /**
     * Obtiene todos los vídeos del escenario Mock.
     *
     * <p>
     * Se devuelve una lista construida a partir del conjunto
     * de vídeos almacenado en DatosMock.
     *
     * <p>
     * Esta operación permite que las vistas del Administrador
     * puedan mostrar contenido sin utilizar todavía la BD real.
     *
     * @return lista de todos los vídeos
     */
    @Override
    public List<Video> getAllVideos() {

        return datos.videos.stream()
                .collect(Collectors.toList());
    }


    /**
     * Elimina un vídeo del escenario Mock.
     *
     * <p>
     * La eliminación se realiza directamente sobre el conjunto
     * de vídeos almacenado en memoria.
     *
     * <p>
     * En la implementación real, esta operación se realizará
     * posteriormente sobre la persistencia.
     *
     * @param idVideo identificador del vídeo que se quiere eliminar
     */
    @Override
    public void borrarVideo(Integer idVideo) {

        datos.videos.removeIf(
            video -> video.getId() == idVideo
        );
    }


    /**
     * Bloquea un Youtuber.
     *
     * <p>
     * Se busca el usuario mediante su login y se modifica su
     * estado de bloqueo directamente en el objeto almacenado
     * en DatosMock.
     *
     * @param idYoutuber login del Youtuber que se quiere bloquear
     */
    @Override
    public void bloquearUsuario(String idYoutuber) {

        datos.youtubers.stream()
                .filter(y -> y.getLogin().equals(idYoutuber))
                .findFirst()
                .ifPresent(y -> y.setBloqueado(true));
    }


    /**
     * Desbloquea un Youtuber.
     *
     * <p>
     * Se busca el usuario mediante su login y se modifica su
     * estado de bloqueo directamente en el escenario Mock.
     *
     * @param idYoutuber login del Youtuber que se quiere desbloquear
     */
    @Override
    public void desbloquearUsuario(String idYoutuber) {

        datos.youtubers.stream()
                .filter(y -> y.getLogin().equals(idYoutuber))
                .findFirst()
                .ifPresent(y -> y.setBloqueado(false));
    }

}