package com.cinereviewapp.cinereview_api.controller;

import java.net.FileNameMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import com.cinereviewapp.cinereview_api.model.Filme;
import com.cinereviewapp.cinereview_api.service.FilmeService;
import com.cinereviewapp.cinereview_api.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api")
public class FilmeController {
    
    private FilmeService filmeService;

    @Autowired
    public FilmeController(FilmeService filmeService) {
        this.filmeService = filmeService;
    }

    @GetMapping("/filmes")
    public List<Filme> getFilmes() {
        return filmeService.getFilmes();
    }
    
    @GetMapping("/filme/{titulo}")
    public ResponseEntity<Filme> getFilmePorTitulo(@PathVariable String titulo) {
        Filme filme = filmeService.getFilmePorNome(titulo)
                    .orElseThrow(() -> new ResourceNotFoundException("Filme não encontrado com título " + titulo));
        return ResponseEntity.ok(filme);
    }

    @PostMapping("/filme")
    public Filme addFilme(@RequestBody Filme filme) {
        return filmeService.addFilme(filme);
    }
    
    @PutMapping("/filme/{titulo}")
    public ResponseEntity<Filme> updateFilme(@PathVariable String titulo, @RequestBody Filme filmeDetalhes) {
        Filme updatedFilme = filmeService.updateFilme(titulo, filmeDetalhes);
        return ResponseEntity.ok(updatedFilme);
    }

    @DeleteMapping("/filme/{titulo}")
    public ResponseEntity<Void> deleteFilme(@PathVariable String titulo) {
        filmeService.deleteFilme(titulo);
        return ResponseEntity.noContent().build();
    }
}
