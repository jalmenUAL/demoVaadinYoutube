package com.example.demo.security;

import java.util.Collections;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demo.services.iNoLogueado;
import com.example.demo.tables.Registrado;

@Component
public class CustomAuthProvider implements AuthenticationProvider {

    private final iNoLogueado iNoLogueado;

    public CustomAuthProvider(@Lazy iNoLogueado iNoLogueado) {
        this.iNoLogueado = iNoLogueado;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        // 🔹 Aquí usas tu método
        Registrado r = iNoLogueado.Login(username, password);

        if (r == null) {
            throw new UsernameNotFoundException(
                    "Usuario o contraseña incorrectos o Tu cuenta está bloqueada, contacta con el administrador");
        }

        // 🚨 Bloquear si es un Youtuber y está marcado como bloqueado
        if (r instanceof com.example.demo.tables.Youtuber youtuber) {
            if (Boolean.TRUE.equals(youtuber.getBloqueado())) {
                throw new DisabledException(
                        "Usuario o contraseña incorrectos o Tu cuenta está bloqueada, contacta con el administrador");
            }
        }

        String role;
        if (r instanceof com.example.demo.tables.Administrador) {
            role = "ROLE_ADMINISTRADOR";

        } else if (r instanceof com.example.demo.tables.Youtuber) {
            role = "ROLE_YOUTUBER";

        } else {
            role = "ROLE_REGISTRADO"; // fallback genérico
        }

        return new UsernamePasswordAuthenticationToken(
                r, // principal = tu entidad de dominio
                r.getPassword(),
                Collections.singletonList(() -> role));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
