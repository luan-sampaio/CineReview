package com.cinereviewapp.cinereview_api.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinereviewapp.cinereview_api.exception.ResourceNotFoundException;
import com.cinereviewapp.cinereview_api.model.Filme;
import com.cinereviewapp.cinereview_api.repository.FilmeRepository;


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
    public Filme addFilme(Filme filme) {
        var id = UUID.randomUUID().toString();
        filme.setId(id);
        return filmeRepository.save(filme);
    }

    // Filtrar por id
    public Optional<Filme> getFilmePorId(String id) {
        return filmeRepository.findById(id);
    }


    public Optional<Filme> getFilmePorNome(String titulo) {
        return filmeRepository.findByTitulo(titulo);
    }

    public Filme updateFilme(String titulo, Filme filmeDetalhes) {
        // Busca pelo titulo correto agora
        Filme filme = filmeRepository.findByTitulo(titulo)
                .orElseThrow(() -> new ResourceNotFoundException("Filme não encontrado com titulo " + titulo));

        // Atualiza os dados
        filme.setTitulo(filmeDetalhes.getTitulo());
        filme.setSinopse(filmeDetalhes.getSinopse());
        filme.setNotaMedia(filmeDetalhes.getNotaMedia());
        filme.setDataLancamento(filmeDetalhes.getDataLancamento());

        return filmeRepository.save(filme);
    }

    // Testar
    // Deletar um filme
    // Deletar um filme pelo Título
    public void deleteFilme(String titulo) {
        Filme filme;
        filme = filmeRepository.findByTitulo(titulo)
                .orElseThrow(() -> new ResourceNotFoundException("Filme não encontrado com titulo: " + titulo));

        filmeRepository.delete(filme);
    }
}
