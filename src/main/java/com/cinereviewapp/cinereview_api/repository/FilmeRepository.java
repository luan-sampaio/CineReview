package com.cinereviewapp.cinereview_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinereviewapp.cinereview_api.model.Filme;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, String> {
    Optional<Filme> findByTitulo(String titulo);
}
