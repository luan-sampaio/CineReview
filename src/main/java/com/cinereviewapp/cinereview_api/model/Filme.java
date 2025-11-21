package com.cinereviewapp.cinereview_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Filme {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String sinopse;
    private String dataLancamento;
    private Float notaMedia;

    public Filme() {
    };

    public Filme(
        Long id,
        String titulo,
        String sinopse,
        String dataLancamento,
        Float notaMedia
    ) {
        this.id = id;
        this.titulo = titulo;
        this.sinopse = sinopse;
        this.dataLancamento = dataLancamento;
        this.notaMedia = notaMedia;
    };

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(String dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public Float getNotaMedia() {
        return notaMedia;
    }

    public void setNotaMedia(Float notaMedia) {
        this.notaMedia = notaMedia;
    }

    // Formato
    @Override
    public String toString() {
        return "Filme{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", sinopse='" + sinopse + '\'' +
                ", dataLancamento='" + dataLancamento + '\'' +
                ", notaMedia='" + notaMedia + '\'' +
                '}';
    }
}
