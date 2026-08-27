package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.demo.tables.Administrador;

public interface RepositorioAdministrador
        extends JpaRepository<Administrador, String>, JpaSpecificationExecutor<Administrador> {

}
