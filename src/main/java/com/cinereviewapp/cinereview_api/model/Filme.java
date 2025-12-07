package com.cinereviewapp.cinereview_api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="filme")
public class Filme {
    @Id
    @Schema(description = "Identificador único do filme", example = "550e8400-e29b-41d4-a716-446655440000")
    private String id;

    // Validação: Não pode ser nulo nem vazio
    @NotBlank(message = "O título do filme é obrigatório")
    @Schema(description = "Título oficial do filme", example = "O Poderoso Chefão")
    private String titulo;

    @Column(columnDefinition = "TEXT")
    @Schema(description = "Resumo da trama do filme", example = "Uma família de mafiosos luta para estabelecer sua supremacia...")
    private String sinopse;

    @Schema(description = "Data de lançamento (Formato YYYY-MM-DD)", example = "1972-03-14")
    private String dataLancamento;

    @Schema(description = "Média das avaliações dos usuários (0-10)", example = "9.2")
    private Float notaMedia;

    public Filme() {
    };

    public Filme(
        String id,
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
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getSinopse() { return sinopse; }
    public void setSinopse(String sinopse) { this.sinopse = sinopse; }
    public String getDataLancamento() { return dataLancamento; }
    public void setDataLancamento(String dataLancamento) { this.dataLancamento = dataLancamento; }
    public Float getNotaMedia() { return notaMedia;}
    public void setNotaMedia(Float notaMedia) { this.notaMedia = notaMedia;}

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
