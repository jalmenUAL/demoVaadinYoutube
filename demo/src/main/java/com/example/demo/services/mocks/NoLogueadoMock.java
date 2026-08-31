package com.example.demo.services.mocks;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.example.demo.services.interfaces.iNoLogueado;
import com.example.demo.tables.Administrador;
import com.example.demo.tables.Registrado;
import com.example.demo.tables.Youtuber;

 

@Service
@Profile("mock")

public class NoLogueadoMock extends InicioMock
        implements iNoLogueado {

    private final List<Registrado> usuarios;

    public NoLogueadoMock(DatosMock datosMock) {
        super(datosMock);
        usuarios = new ArrayList<>();

    }

    /**
     * Carga los usuarios ficticios utilizados
     * durante el prototipado.
     */

    @Override
    public Registrado Login(String login, String password) {

        DatosMock datos = new DatosMock();

        for (Administrador y : datos.administradores) {

            if (y.getLogin().equals(login)
                    && y.getPassword().equals(password)) {

                return y;
            }
        }

        for (Youtuber y : datos.youtubers) {

            if (y.getLogin().equals(login)
                    && y.getPassword().equals(password)) {

                return y;
            }
        }

        return null;
    }

    @Override
    public void registrar(
            String login,
            String password,
            String avatarUrl,
            String fondoUrl) {

        /*
         * Comprobamos que el login no exista.
         */
        boolean existe = usuarios.stream()
                .anyMatch(usuario -> usuario.getLogin()
                        .equals(login));

        if (existe) {
            return;
        }

        /*
         * Creamos un nuevo Youtuber utilizando
         * la entidad del modelo.
         */
        Youtuber nuevo = new Youtuber();

        nuevo.setLogin(login);
        nuevo.setPassword(password);
        nuevo.setBanner(fondoUrl);

        usuarios.add(nuevo);
    }
}
