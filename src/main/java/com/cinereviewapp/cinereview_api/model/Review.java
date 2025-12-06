package com.cinereviewapp.cinereview_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name="review")
public class Review {

    @Id
    private String id; // Novo ID único da Review

    private Long usuarioId; // Agora é apenas um campo comum
    private String filmeId;
    private float notaReview;
    private String comentario;

    public Review() {
        // Gera um ID único automaticamente ao criar vazio
        this.id = UUID.randomUUID().toString();
    }

    public Review(Long usuarioId, String filmeId, float notaReview, String comentario) {
        this.id = UUID.randomUUID().toString(); // Gera ID único
        this.usuarioId = usuarioId;
        this.filmeId = filmeId;
        this.notaReview = notaReview;
        this.comentario = comentario;
    }

    // Getters e Setters do NOVO ID
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    // Outros Getters e Setters (Mantenha os que você já tem)
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public String getFilmeId() { return filmeId; }
    public void setFilmeId(String filmeId) { this.filmeId = filmeId; }
    public float getNotaReview() { return notaReview; }
    public void setNotaReview(float notaReview) { this.notaReview = notaReview; }
    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
}
