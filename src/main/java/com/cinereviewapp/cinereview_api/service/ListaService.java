package com.cinereviewapp.cinereview_api.service;

import com.cinereviewapp.cinereview_api.model.Filme;
import com.cinereviewapp.cinereview_api.model.Lista;
import com.cinereviewapp.cinereview_api.repository.ListaRepository;
import com.cinereviewapp.cinereview_api.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;

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

    public void deleteLista(Long id) {
        listaRepository.deleteById(id);
    }
    @Transactional // Garante que a remoção e o salvamento ocorram na mesma transação
    public Lista removeFilmeFromLista(Long listaId, String filmeId) {
        // 1. Busca a lista
        Lista lista = listaRepository.findById(listaId)
                .orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada com ID: " + listaId));

        // 2. Remove o filme da lista se o ID bater
        // O removeIf retorna 'true' se algo foi removido
        boolean removed = lista.getFilmes().removeIf(filme -> filme.getId().equals(filmeId));

        if (!removed) {
            throw new ResourceNotFoundException("Filme com ID " + filmeId + " não encontrado nesta lista.");
        }

        // 3. Salva a lista atualizada
        return listaRepository.save(lista);
    }
}