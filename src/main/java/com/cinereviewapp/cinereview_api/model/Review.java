package com.cinereviewapp.cinereview_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import io.swagger.v3.oas.annotations.media.Schema; // Import que você já tinha

@Entity
@Table(name="review")
@Schema(description = "Representa a avaliação de um filme feita por um usuário") // DESCRIÇÃO DA CLASSE
public class Review {

    @Id
    @Schema(description = "Identificador único da review (Gerado automaticamente)", example = "550e8400-e29b-41d4-a716-446655440000") // DESCRIÇÃO DO ID
    private String id;

    @Schema(description = "ID numérico do usuário que fez a crítica", example = "10") // DESCRIÇÃO DO USUÁRIO
    private Long usuarioId;

    @Schema(description = "UUID do filme que está sendo avaliado", example = "c56a4180-65aa-42ec-a945-5fd21dec0538") // DESCRIÇÃO DO FILME
    private String filmeId;

    @Schema(description = "Nota atribuída ao filme (de 0 a 10)", example = "9.5") // DESCRIÇÃO DA NOTA
    private float notaReview;

    @Schema(description = "Comentário detalhado sobre o filme", example = "Roteiro excelente e ótimas atuações.") // DESCRIÇÃO DO COMENTÁRIO
    private String comentario;

    public Review() {
        // Gera um ID único automaticamente ao criar vazio
        this.id = UUID.randomUUID().toString();
    }

    public Review(Long usuarioId, String filmeId, float notaReview, String comentario) {
        this.id = UUID.randomUUID().toString();
        this.usuarioId = usuarioId;
        this.filmeId = filmeId;
        this.notaReview = notaReview;
        this.comentario = comentario;
    }

    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public String getFilmeId() { return filmeId; }
    public void setFilmeId(String filmeId) { this.filmeId = filmeId; }

    public float getNotaReview() { return notaReview; }
    public void setNotaReview(float notaReview) { this.notaReview = notaReview; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
}
