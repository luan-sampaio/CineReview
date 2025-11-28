package com.cinereviewapp.cinereview_api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cinereviewapp.cinereview_api.model.Filme;
import com.cinereviewapp.cinereview_api.model.Review;

@Service
public class ReviewService {

    public List<Review> reviews = new ArrayList<>();

    private final FilmeService filmeService;

    public ReviewService(FilmeService filmeService) {
        this.filmeService = filmeService;
    }

    public boolean addReview(String filmeId, Review review) {
        Optional<Filme> filmeExistente = filmeService.getFilmePorId(filmeId);

        if (filmeExistente != null) {
            review.setFilmeId(filmeId);

            reviews.add(review);
            return true;
        } else {
            return false;
        }
    }

    public List<Review> getReviewsPorFilmeId(String filmeId) {
        return reviews.stream()
                .filter(review -> review.getFilmeId().equals(filmeId))
                .toList();
    }
}
