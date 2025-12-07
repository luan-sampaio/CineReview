package com.cinereviewapp.cinereview_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinereviewapp.cinereview_api.exception.ResourceNotFoundException;
import com.cinereviewapp.cinereview_api.model.Filme;
import com.cinereviewapp.cinereview_api.model.Lista;
import com.cinereviewapp.cinereview_api.repository.ListaRepository;

import jakarta.transaction.Transactional;

@Service
public class ListaService {

    @Autowired
    private ListaRepository listaRepository;

    @Autowired
    private FilmeService filmeService; // Usamos o serviço que você já tem!

    public Lista createLista(String nome) {
        Lista novaLista = new Lista(nome);
        return listaRepository.save(novaLista);
    }

    public List<Lista> getAllListas() {
        return listaRepository.findAll();
    }

    /**
     * Adiciona um filme existente a uma lista de favoritos.
     * <p>
     * Realiza a validação de existência tanto da Lista quanto do Filme antes de fazer a associação.
     * * @param listaId O ID da lista onde o filme será adicionado.
     * @param filmeId O ID do filme a ser inserido.
     * @return A lista atualizada contendo o novo filme.
     * @throws ResourceNotFoundException caso a lista ou o filme não sejam encontrados.
     */
    public Lista addFilmeToLista(Long listaId, String filmeId) {
        // 1. Busca a lista
        Lista lista = listaRepository.findById(listaId)
                .orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada com ID: " + listaId));

        // 2. Busca o filme (usando seu serviço existente)
        Filme filme = filmeService.getFilmePorId(filmeId)
                .orElseThrow(() -> new ResourceNotFoundException("Filme não encontrado com ID: " + filmeId));

        // 3. Adiciona e salva
        lista.getFilmes().add(filme);
        return listaRepository.save(lista);
    }

    /**
     * Remove um filme específico de uma lista.
     * <p>
     * A anotação {@code @Transactional} garante que a operação de leitura, remoção e salvamento
     * ocorra de forma atômica no banco de dados.
     * * @param listaId O ID da lista.
     * @param filmeId O ID do filme a ser removido.
     * @return A lista atualizada após a remoção.
     * @throws ResourceNotFoundException se a lista não existir ou se o filme não estiver nela.
     */
    public void deleteLista(Long id) {
        listaRepository.deleteById(id);
    }
    @Transactional // Garante que a remoção e o salvamento ocorram na mesma transação
    public Lista removeFilmeFromLista(Long listaId, String filmeId) {
        Lista lista = listaRepository.findById(listaId)
                .orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada com ID: " + listaId));

        // O removeIf retorna 'true' se algo foi removido
        boolean removed = lista.getFilmes().removeIf(filme -> filme.getId().equals(filmeId));

        if (!removed) {
            throw new ResourceNotFoundException("Filme com ID " + filmeId + " não encontrado nesta lista.");
        }

        return listaRepository.save(lista);
    }
}
