package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.demo.tables.Registrado;

public interface RepositorioRegistrado extends JpaRepository<Registrado, String>, JpaSpecificationExecutor<Registrado> {

}
