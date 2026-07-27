package com.example.demo.services;

import com.example.demo.tables.Registrado;

public interface iNoLogueado extends iInicio {

    Registrado Login(String login, String password);

    void registrar(String login, String password, String avatarUrl, String fondoUrl);
}