package com.cinereviewapp.cinereview_api.controller;

import java.util.List;

import com.cinereviewapp.cinereview_api.model.Review;
import com.cinereviewapp.cinereview_api.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/reviews") // Padronização da URL Base
@Tag(name = "Reviews", description = "Endpoints para gerenciamento de críticas e avaliações")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // 1. POST: Adicionar Review
    // URL: http://localhost:8080/api/reviews

    @PostMapping
    @Operation(summary = "Criar nova review", description = "Adiciona uma crítica a um filme existente. Requer o ID do filme.")
    public ResponseEntity<Review> addReview(@RequestBody Review review) {
        Review novaReview = reviewService.addReview(review);
        return new ResponseEntity<>(novaReview, HttpStatus.CREATED);
    }

    // 2. GET: Listar Reviews (Aceita filtro por ID ou Título)
    // URL 1: http://localhost:8080/api/reviews?filmeId={UUID}
    // URL 2: http://localhost:8080/api/reviews?titulo=Matrix
    @GetMapping
    @Operation(summary = "Listar reviews", description = "Busca reviews filtrando por ID do filme ou Título do filme.")
    public ResponseEntity<List<Review>> listarReviews(
            @RequestParam(required = false) String filmeId,
            @RequestParam(required = false) String titulo
    ) {
        List<Review> resultado;

        if (filmeId != null) {
            resultado = reviewService.getReviewsPorFilmeId(filmeId);
        } else if (titulo != null) {
            resultado = reviewService.getReviewsPorTituloFilme(titulo);
        } else {
            // Se não passar parâmetros, retorna vazio ou todas (opcional)
            return ResponseEntity.ok(List.of());
        }

        if (resultado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(resultado);
    }

    @DeleteMapping("/{reviewId}")
    @Operation(summary = "Deletar review", description = "Remove uma crítica específica do sistema baseada no ID da review.")
    public ResponseEntity<Void> deletarReview(@PathVariable String reviewId) {

        boolean removido = reviewService.deleteReviewPorId(reviewId);

        if (removido) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
