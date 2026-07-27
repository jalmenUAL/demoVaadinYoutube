package com.example.demo.components;

import java.util.List;
import java.util.Vector;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.facade.BDPrincipal;
import com.example.demo.repositories.RepositorioYoutuber;
import com.example.demo.tables.Youtuber;

@Service
public class BD_Youtubers {
    public BDPrincipal _en;
    public Vector<Youtuber> _youtubers = new Vector<Youtuber>();
    final RepositorioYoutuber repository;
    private PasswordEncoder passwordEncoder;

    public BD_Youtubers(RepositorioYoutuber repository,
            PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;

    }

    public Youtuber autenticar(String login, String rawPassword) {
        return repository.findById(login)
                .filter(youtuber -> passwordEncoder.matches(rawPassword, youtuber.getPassword()))
                .orElse(null);
    }

    public Youtuber findYoutuberById(String login) {
        return repository.findById(login)
                .orElseThrow(() -> new RuntimeException("Youtuber no encontrado"));
    }

    public void registrar(String login, String password, String avatarUrl, String fondoUrl) {
        Youtuber nuevoYoutuber = new Youtuber();
        nuevoYoutuber.setLogin(login);
        String PasswordEncoder = passwordEncoder.encode(password);
        nuevoYoutuber.setPassword(PasswordEncoder);
        nuevoYoutuber.setFotoPerfil(avatarUrl);
        nuevoYoutuber.setBanner(fondoUrl);
        nuevoYoutuber.setBloqueado(false);
        repository.save(nuevoYoutuber);
    }

    public void actualizarConfiguracion(String login, String password, String avatar, String fondo) {
        Youtuber usuario = findYoutuberById(login);
        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setFotoPerfil(avatar);
        usuario.setBanner(fondo);
        repository.save(usuario);
    }

    public List<Youtuber> buscarDenunciados() {
        List<Youtuber> denunciados = repository.findAll();
        return denunciados.stream()
                .filter(youtuber -> youtuber.getDenunciado_por().size() > 0)
                .toList();
    }

    public void seguirUsuario(String loginSeguidor, String loginSeguido) {
        Youtuber seguidor = findYoutuberById(loginSeguidor);
        Youtuber seguido = findYoutuberById(loginSeguido);
        seguido.getSeguido_por().add(seguidor);
    }

    public void dejardeseguirUsuario(String loginSeguidor, String loginSeguido) {
        Youtuber seguidor = findYoutuberById(loginSeguidor);
        Youtuber seguido = findYoutuberById(loginSeguido);
        seguido.getSeguido_por().remove(seguidor);

    }

    public void bloquearUsuario(String loginYoutuber) {
        Youtuber usuario = findYoutuberById(loginYoutuber);
        usuario.setBloqueado(true);
        repository.save(usuario);
    }

    public void desbloquearUsuario(String loginYoutuber) {
        Youtuber usuario = findYoutuberById(loginYoutuber);
        usuario.setBloqueado(false);
        repository.save(usuario);
    }

    public void denunciarUsuario(String loginDenunciante, String loginDenunciado) {
        Youtuber denunciante = findYoutuberById(loginDenunciante);
        Youtuber denunciado = findYoutuberById(loginDenunciado);
        denunciante.getDenunciado_por().add(denunciado);
        repository.save(denunciante);
    }

    public void quitardenunciaUsuario(String loginDenunciante, String loginDenunciado) {
        Youtuber denunciante = findYoutuberById(loginDenunciante);
        Youtuber denunciado = findYoutuberById(loginDenunciado);
        denunciante.getDenunciado_por().remove(denunciado);
        repository.save(denunciante);
    }

}
