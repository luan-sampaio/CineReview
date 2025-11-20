package com.cinereviewapp.cinereview_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long usuarioId;
    private Long filmeId;
    private float notaReview;
    private String comentario;

    public Review() {

    };

    public Review(
        Long usuarioId,
        Long filmeId,
        float notaReview,
        String comentario
    ) {
        this.usuarioId = usuarioId;
        this.filmeId = filmeId;
        this.notaReview = notaReview;
        this.comentario = comentario;
    };

    // Getters e Setters
    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getFilmeId() {
        return filmeId;
    }

    public void setFilmeId(Long filmeId) {
        this.filmeId = filmeId;
    }

    public float getNotaReview() {
        return notaReview;
    }

    public void setNotaReview(float notaReview) {
        this.notaReview = notaReview;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    @Override
    public String toString() {
        return "Review{" +
                "usuario_id=" + usuarioId +
                ", filme_id='" + filmeId + '\'' +
                ", nota_review='" + notaReview + '\'' +
                ", comentario='" + comentario + '\'' +
                '}';
    }
}
