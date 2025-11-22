package com.cinereviewapp.cinereview_api.controller;

import java.util.List;

import com.cinereviewapp.cinereview_api.model.Review;
import com.cinereviewapp.cinereview_api.service.FilmeService;
import com.cinereviewapp.cinereview_api.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReviewController {

    @Autowired
    private ReviewService reviewService;
    @PostMapping("/filmes/{filmeId}/reviews")
    public ResponseEntity<String> addReviewParaFilme(
            @PathVariable Long filmeId,
            @RequestBody Review review
    ) {
        boolean sucesso = reviewService.addReview(filmeId, review);

        if (sucesso) {
            return new ResponseEntity<>("Review adicionada com sucesso!", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Filme com ID " + filmeId + " não encontrado.", HttpStatus.NOT_FOUND);
        }
    }

    @Autowired
    private FilmeService filmeService;

    @GetMapping("/filmes/{filmeId}/reviews")
    public ResponseEntity<List<Review>> listarReviewsDoFilme(@PathVariable Long filmeId) {
        if (filmeService.getFilmePorId(filmeId) == null) {
            return ResponseEntity.notFound().build();
        }

        List<Review> reviewsDoFilme = reviewService.getReviewsPorFilmeId(filmeId);
        return ResponseEntity.ok(reviewsDoFilme);
    }
}
