package com.example.demo.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.demo.repositories.RepositorioAdministrador;
import com.example.demo.repositories.RepositorioYoutuber;
import com.example.demo.tables.Administrador;
import com.example.demo.tables.Youtuber;

import org.springframework.security.crypto.password.PasswordEncoder;

@Component

public class DataInitializer implements CommandLineRunner {

    private final RepositorioAdministrador adminRepo;
    private final PasswordEncoder passwordEncoder;
    private final RepositorioYoutuber youtuberRepo;

    public DataInitializer(RepositorioAdministrador adminRepo, RepositorioYoutuber youtuberRepo,
            PasswordEncoder passwordEncoder) {
        this.adminRepo = adminRepo;
        this.passwordEncoder = passwordEncoder;
        this.youtuberRepo = youtuberRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        // Verificar si existe un admin
        if (adminRepo.findById("admin").isEmpty()) {
            Administrador admin = new Administrador();
            admin.setLogin("admin");
            admin.setPassword(passwordEncoder.encode("admin123")); // siempre cifrar

            adminRepo.save(admin);

            System.out.println("✅ Usuario administrador creado: admin / admin123");
        }

        if (youtuberRepo.findById("youtuber").isEmpty()) {
            Youtuber youtuber = new Youtuber();
            youtuber.setLogin("youtuber");
            youtuber.setBloqueado(false);
            youtuber.setPassword(passwordEncoder.encode("youtuber123")); // siempre cifrar

            youtuberRepo.save(youtuber);

            System.out.println("✅ Usuario youtuber creado: youtuber / youtuber123");
        }
    }
}
