package com.cinereviewapp.cinereview_api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cinereviewapp.cinereview_api.model.Filme;

@Service
public class FilmeService {
    public List<Filme> filmes = new ArrayList<>();

    // Retornar todos os filmes
    public List<Filme> getFilmes() {
        return filmes;
    }

    // Adicionar filme
    public void addFilme(Filme filme) {
        var id = UUID.randomUUID().toString();
        filme.setId(id);
        filmes.add(filme);
    }

    // Filtrar por id
    public Filme getFilmePorId(String id) {
        return filmes.stream()
        .filter(filme -> filme.getId().equals(id))
        .findFirst()
        .orElse(null);
    }

    // Filtrar por nome de filme
    public Filme getFilmePorNome(String titulo) {
        return filmes.stream()
        .filter(filme -> filme.getTitulo().equals(titulo))
        .findFirst()
        .orElse(null);
    }
}
