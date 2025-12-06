package com.cinereviewapp.cinereview_api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cinereviewapp.cinereview_api.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.cinereviewapp.cinereview_api.model.Filme;
import com.cinereviewapp.cinereview_api.model.Review;

@Service
public class ReviewService {

    // Simulando banco em memória
    public List<Review> reviews = new ArrayList<>();

    private final FilmeService filmeService;

    public ReviewService(FilmeService filmeService) {
        this.filmeService = filmeService;
    }

    // ADICIONAR
    public Review addReview(Review review) {
        Optional<Filme> filme = filmeService.getFilmePorId(review.getFilmeId());

        if (filme.isPresent()) {
            // O ID já é gerado no construtor da classe Review ou aqui se preferir
            // review.setId(UUID.randomUUID().toString());
            reviews.add(review);
            return review;
        } else {
            throw new ResourceNotFoundException("Filme não encontrado.");
        }
    }

    // LISTAR POR FILME
    public List<Review> getReviewsPorFilmeId(String filmeId) {
        return reviews.stream()
                .filter(r -> r.getFilmeId().equals(filmeId))
                .toList();
    }

    // LISTAR POR TÍTULO
    public List<Review> getReviewsPorTituloFilme(String titulo) {
        Optional<Filme> filme = filmeService.getFilmePorNome(titulo);
        if (filme.isPresent()) {
            return getReviewsPorFilmeId(filme.get().getId());
        }
        return List.of();
    }

    // --- NOVA LÓGICA DE DELEÇÃO ---

    // Deletar UMA review específica (Pelo ID da Review)
    public boolean deleteReviewPorId(String reviewId) {
        return reviews.removeIf(r -> r.getId().equals(reviewId));
    }

    // Limpeza (Cascata): Deletar todas as reviews de um filme que foi apagado
    public void deleteReviewsPorFilmeId(String filmeId) {
        reviews.removeIf(r -> r.getFilmeId().equals(filmeId));
    }
}
