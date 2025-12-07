package com.cinereviewapp.cinereview_api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cinereviewapp.cinereview_api.dto.TmdbFilmeDTO;
import com.cinereviewapp.cinereview_api.dto.TmdbResponseDTO;
import com.cinereviewapp.cinereview_api.model.Filme;

/**
 * Serviço responsável pela comunicação com a API externa do TMDB (The Movie Database).
 * Realiza buscas e conversão dos dados externos (DTOs) para o modelo interno da aplicação.
 */
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

    /**
     * Busca filmes na API do TMDB baseada em um termo de pesquisa (título).
     * <p>
     * Este método consome o endpoint de busca (/search/movie), recebe os dados brutos
     * e os converte para uma lista de objetos {@link Filme}.
     * * @param titulo O termo a ser pesquisado (ex: "Matrix").
     * @return Uma lista de filmes encontrados. Retorna uma lista vazia em caso de erro ou sem resultados.
     */
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

    /**
     * Busca os detalhes completos de um único filme pelo seu ID no TMDB.
     * <p>
     * Utiliza o parâmetro language=pt-BR para garantir que a sinopse venha em português.
     * * @param idTmdb O ID numérico do filme no sistema do TMDB.
     * @return O objeto {@link Filme} preenchido.
     * @throws RuntimeException se ocorrer um erro na comunicação ou conversão.
     */
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
