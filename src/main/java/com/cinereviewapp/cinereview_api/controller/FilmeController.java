package com.cinereviewapp.cinereview_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cinereviewapp.cinereview_api.exception.ResourceNotFoundException;
import com.cinereviewapp.cinereview_api.model.Filme;
import com.cinereviewapp.cinereview_api.service.FilmeService;
import com.cinereviewapp.cinereview_api.service.ReviewService;

@RestController
@RequestMapping("/api/filmes")
public class FilmeController {
    
    private FilmeService filmeService;
    private ReviewService reviewService;

    @Autowired
    public FilmeController(FilmeService filmeService, ReviewService reviewService) {
        this.filmeService = filmeService;
        this.reviewService = reviewService;
    }

    //GET /api/filmes
    @GetMapping
    public List<Filme> getFilmes() {
        return filmeService.getFilmes();
    }
    

    // URL: GET /api/filmes/busca?titulo=Matrix
    @GetMapping("/busca") 
    public ResponseEntity<Filme> getFilmePorTitulo(@RequestParam("titulo") String titulo) {
        return filmeService.getFilmePorNome(titulo)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Filme não encontrado com título: " + titulo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Filme> getFilmePorId(@PathVariable String id) {
        Filme filme = filmeService.getFilmePorId(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Filme não encontrado com ID " + id));
        return ResponseEntity.ok(filme);
    }

    @PostMapping
    public Filme addFilme(@RequestBody Filme filme) {
        return filmeService.addFilme(filme);
    }
    
    
    @PutMapping("/{id}")
    public ResponseEntity<Filme> updateFilme(@PathVariable String id, @RequestBody Filme filmeDetalhes) {
        Filme updatedFilme = filmeService.updateFilme(id, filmeDetalhes);
        return ResponseEntity.ok(updatedFilme);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilme(@PathVariable String id) {
        // Primeiro precisamos descobrir o ID desse filme antes de deletar
        var filmeOpt = filmeService.getFilmePorId(id);

        if (filmeOpt.isPresent()) {
            String filmeId = filmeOpt.get().getId();

            // 1. Deleta as reviews da memória
            reviewService.deleteReviewsPorFilmeId(filmeId);

            // 2. Deleta o filme do banco
            filmeService.deleteFilme(id);

            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
