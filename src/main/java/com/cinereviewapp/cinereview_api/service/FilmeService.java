package com.cinereviewapp.cinereview_api.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinereviewapp.cinereview_api.model.Filme;
import com.cinereviewapp.cinereview_api.repository.FilmeRepository;
import com.cinereviewapp.cinereview_api.exception.ResourceNotFoundException;


@Service
public class FilmeService {
    
    private final FilmeRepository filmeRepository;

    @Autowired
    public FilmeService(FilmeRepository filmeRepository) {
        this.filmeRepository = filmeRepository;
    }

    // Retornar todos os filmes
    public List<Filme> getFilmes() {
        return filmeRepository.findAll();
    }

    // Adicionar filme
    public void addFilme(Filme filme) {
        var id = UUID.randomUUID().toString();
        filme.setId(id);
        filmeRepository.save(filme);
    }

    // Filtrar por id
    public Optional<Filme> getFilmePorId(String id) {
        return filmeRepository.findById(id);
    }

    // TESTAR ISSO AQUI
    // Filtrar por nome de filme
    public Optional<Filme> getFilmePorNome(String titulo) {
        return filmeRepository.findById(titulo);
    }

    // Testar
    // Método para alterar um filme
    public Filme updateFilme(String titulo, Filme filmeDetalhes) {
        Filme filme = filmeRepository.findById(titulo)
                    .orElseThrow(() -> new ResourceNotFoundException("Filme não encontrado com titulo " + titulo));
        filme.setTitulo(filmeDetalhes.getTitulo());
        filme.setSinopse(filmeDetalhes.getSinopse());
        filme.setNotaMedia(filmeDetalhes.getNotaMedia());
        filme.setDataLancamento(filmeDetalhes.getDataLancamento());
        return filmeRepository.save(filme);
    }

    // Testar
    // Deletar um filme
    public void deleteFilme(String titulo) {
        filmeRepository.deleteById(titulo);
    }
}
