package com.example.demo.components;

import java.util.Vector;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.facade.BDPrincipal;
import com.example.demo.repositories.RepositorioAdministrador;
import com.example.demo.tables.Administrador;

@Service
public class BD_Administradores {
    public BDPrincipal _en;
    public Vector<Administrador> _administradores = new Vector<Administrador>();

    private RepositorioAdministrador repository;
    private PasswordEncoder passwordEncoder;

    public BD_Administradores(RepositorioAdministrador administradoresRepository,
            PasswordEncoder passwordEncoder) {

        this.repository = administradoresRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Administrador autenticar(String login, String rawPassword) {
        System.out.println(rawPassword);
        return repository.findById(login)
                .filter(admin -> passwordEncoder.matches(rawPassword, admin.getPassword()))
                .orElse(null);

    }
}