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

    public List<Filme> getFilmes() {
        return filmeRepository.findAll();
    }

    /**
     * Salva um novo filme no banco de dados.
     * <p>
     * Gera automaticamente um UUID para o ID do filme antes de salvar.
     * * @param filme O objeto filme com os dados a serem salvos.
     * @return O filme salvo com o ID preenchido.
     */
    public Filme addFilme(Filme filme) {
        var id = UUID.randomUUID().toString();
        filme.setId(id);
        return filmeRepository.save(filme);
    }

    public Optional<Filme> getFilmePorId(String id) {
        return filmeRepository.findById(id);
    }

    public Optional<Filme> getFilmePorNome(String titulo) {
        return filmeRepository.findByTitulo(titulo);
    }

    /**
     * Atualiza os dados de um filme existente.
     * * @param id O ID do filme a ser atualizado.
     * @param filmeDetalhes O objeto contendo os novos dados (título, sinopse, nota, etc).
     * @return O filme atualizado.
     * @throws ResourceNotFoundException se o filme não for encontrado pelo ID.
     */
    public Filme updateFilme(String id, Filme filmeDetalhes) {
        Filme filme = filmeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Filme não encontrado com id " + id));

        filme.setTitulo(filmeDetalhes.getTitulo());
        filme.setSinopse(filmeDetalhes.getSinopse());
        filme.setNotaMedia(filmeDetalhes.getNotaMedia());
        filme.setDataLancamento(filmeDetalhes.getDataLancamento());

        return filmeRepository.save(filme);
    }

    /**
     * Remove um filme do banco de dados.
     * * @param id O ID do filme a ser excluído.
     * @throws ResourceNotFoundException se o filme não existir.
     */
    public void deleteFilme(String id) {
        Filme filme;
        filme = filmeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Filme não encontrado com titulo: " + id));

        filmeRepository.delete(filme);
    }
}
