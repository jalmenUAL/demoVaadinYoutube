
package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.vaadin.flow.spring.security.VaadinWebSecurity;

@Configuration
public class SecurityConfig extends VaadinWebSecurity {

    private final CustomAuthProvider customAuthProvider;

    public SecurityConfig(CustomAuthProvider customAuthProvider) {
        this.customAuthProvider = customAuthProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.authorizeHttpRequests(auth -> auth
            .requestMatchers("/uploads/**").permitAll()
    );

        // Configuración de seguridad de Vaadin
        super.configure(http);

        

        // Indica que tu vista Login es la pantalla de login
        setLoginView(http, com.example.demo.views.Login.class);
    }

    // AuthenticationManager basado en tu CustomAuthProvider
    @Bean
    public AuthenticationManager authenticationManager(CustomAuthProvider customAuthProvider) {
        return new ProviderManager(customAuthProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}