package com.example.demo.services.mocks;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.example.demo.services.interfaces.iAdministrador;
import com.example.demo.tables.Video;
import com.example.demo.tables.Youtuber;
@Service
public class AdmistradorMock extends RegistradoMock implements iAdministrador {

    public AdmistradorMock(DatosMock datos) {
        super(datos);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void eliminarComentario(Integer idComentario) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminarComentario'");
    }

    @Override
    public List<Youtuber> buscarDenunciados() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarDenunciados'");
    }

    @Override
    public List<Video> getAllVideos() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllVideos'");
    }

    @Override
    public void borrarVideo(Integer idVideo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'borrarVideo'");
    }

    @Override
    public void bloquearUsuario(String idYoutuber) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'bloquearUsuario'");
    }

    @Override
    public void desbloquearUsuario(String idYoutuber) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'desbloquearUsuario'");
    }

}
