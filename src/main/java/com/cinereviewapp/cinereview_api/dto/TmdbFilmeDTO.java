package com.cinereviewapp.cinereview_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TmdbFilmeDTO {

    private Long id;

    @JsonProperty("title")
    private String titulo;

    @JsonProperty("overview")
    private String sinopse;

    @JsonProperty("release_date")
    private String dataLancamento;

    @JsonProperty("vote_average")
    private Float notaMedia;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getSinopse() { return sinopse; }
    public void setSinopse(String sinopse) { this.sinopse = sinopse; }

    public String getDataLancamento() { return dataLancamento; }
    public void setDataLancamento(String dataLancamento) { this.dataLancamento = dataLancamento; }

    public Float getNotaMedia() { return notaMedia; }
    public void setNotaMedia(Float notaMedia) { this.notaMedia = notaMedia; }
}
