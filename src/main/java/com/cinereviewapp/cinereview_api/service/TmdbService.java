package com.cinereviewapp.cinereview_api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cinereviewapp.cinereview_api.dto.TmdbFilmeDTO;
import com.cinereviewapp.cinereview_api.dto.TmdbResponseDTO;
import com.cinereviewapp.cinereview_api.model.Filme;

@Service
public class TmdbService {

    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final String apiKey;

    public TmdbService(RestTemplate restTemplate, 
                       @Value("${tmdb.api.base-url}") String baseUrl, 
                       @Value("${tmdb.api.key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
    }

    public List<Filme> buscarFilmesPorTitulo(String titulo) {
        String url = baseUrl + "/search/movie?api_key={api_key}&query={query}";

        try {
            TmdbResponseDTO response = restTemplate.getForObject(url, TmdbResponseDTO.class, apiKey, titulo);

            List<Filme> filmesFiltrados = new ArrayList<>();

            if (response != null && response.getResults() != null) {
                for (TmdbFilmeDTO dto : response.getResults()) {
                    Filme filme = new Filme();
                    
                    filme.setId(String.valueOf(dto.getId()));
                    
                    filme.setTitulo(dto.getTitulo());
                    filme.setSinopse(dto.getSinopse());
                    filme.setDataLancamento(dto.getDataLancamento());
                    filme.setNotaMedia(dto.getNotaMedia());

                    filmesFiltrados.add(filme);
                }
            }

            return filmesFiltrados;

        } catch (Exception e) {
            System.err.println("Erro ao buscar no TMDB: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public Filme buscarFilmePorId(String idTmdb) {
        String url = baseUrl + "/movie/{id}?api_key={api_key}&language=pt-BR";

        try {
            TmdbFilmeDTO dto = restTemplate.getForObject(url, TmdbFilmeDTO.class, idTmdb, apiKey);

            if (dto != null) {
                Filme filme = new Filme();
                filme.setId(String.valueOf(dto.getId())); 
                filme.setTitulo(dto.getTitulo());
                filme.setSinopse(dto.getSinopse());
                filme.setDataLancamento(dto.getDataLancamento());
                filme.setNotaMedia(dto.getNotaMedia());
                
                return filme;
            }
            return null;

        } catch (Exception e) {
            System.err.println("Erro ao importar filme do TMDB: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar filme no TMDB.");
        }
    }
    
}
