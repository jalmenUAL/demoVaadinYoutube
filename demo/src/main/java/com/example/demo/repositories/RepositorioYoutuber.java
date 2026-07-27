package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.demo.tables.Youtuber;

public interface RepositorioYoutuber extends JpaRepository<Youtuber, String>, JpaSpecificationExecutor<Youtuber> {

}
