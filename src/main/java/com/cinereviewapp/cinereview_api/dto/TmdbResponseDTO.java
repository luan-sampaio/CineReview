package com.cinereviewapp.cinereview_api.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TmdbResponseDTO {
    
    private List<TmdbFilmeDTO> results;

    public List<TmdbFilmeDTO> getResults() {
        return results;
    }

    public void setResults(List<TmdbFilmeDTO> results) {
        this.results = results;
    }
}
