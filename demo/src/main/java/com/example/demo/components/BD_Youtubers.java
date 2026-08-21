package com.example.demo.components;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

    public void actualizarConfiguracion(
        String login,
        String password,
        InputStream avatar,
        String avatarNombre,
        InputStream fondo,
        String fondoNombre) {

    Youtuber usuario =
            findYoutuberById(login);

    // -------------------------
    // Contraseña
    // -------------------------

    if (password != null &&
            !password.isBlank()) {

        usuario.setPassword(
                passwordEncoder.encode(password));
    }

    // -------------------------
    // Avatar
    // -------------------------

    if (avatar != null) {

        String rutaAvatar =
                guardarImagen(
                        avatar,
                        avatarNombre,
                        login,
                        "avatar");

        usuario.setFotoPerfil(rutaAvatar);
    }

    // -------------------------
    // Fondo
    // -------------------------

    if (fondo != null) {

        String rutaFondo =
                guardarImagen(
                        fondo,
                        fondoNombre,
                        login,
                        "banner");

        usuario.setBanner(rutaFondo);
    }

    repository.save(usuario);
}


private String guardarImagen(
        InputStream inputStream,
        String nombreOriginal,
        String login,
        String tipo) {

    try {

        // Carpeta: uploads/usuarios/login
        Path directorio =
                Paths.get(
                        "uploads",
                        "usuarios",
                        login);

        Files.createDirectories(directorio);

        // Obtener extensión
        String extension = "";

        if (nombreOriginal != null
                && nombreOriginal.contains(".")) {

            extension =
                    nombreOriginal.substring(
                            nombreOriginal.lastIndexOf("."));
        }

        // Nombre del archivo
        String nombreArchivo =
                tipo + extension;

        Path archivo =
                directorio.resolve(nombreArchivo);

        // Guardar archivo
        Files.copy(
                inputStream,
                archivo,
                StandardCopyOption.REPLACE_EXISTING);

        // Ruta que guardaremos en la BD
        return "/uploads/usuarios/"
                + login
                + "/"
                + nombreArchivo;

    } catch (IOException e) {

        throw new RuntimeException(
                "Error al guardar la imagen",
                e);
    }
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
        repository.save(seguido);
    }

    public void dejardeseguirUsuario(String loginSeguidor, String loginSeguido) {
        Youtuber seguidor = findYoutuberById(loginSeguidor);
        Youtuber seguido = findYoutuberById(loginSeguido);
        seguido.getSeguido_por().remove(seguidor);
        repository.save(seguido);

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
