package com.cinereviewapp.cinereview_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cinereviewapp.cinereview_api.model.Filme;
import com.cinereviewapp.cinereview_api.service.FilmeService;

@RestController
public class FilmeController {
    
    @Autowired
    private FilmeService filmeService;

    @GetMapping("/filmes")
    public List<Filme> getFilmes() {
        return filmeService.filmes;
    }
    
    @GetMapping("/filme/{titulo}")
    public Filme getFilmePorTitulo(@PathVariable String titulo) {
        return filmeService.getFilmePorNome(titulo);
    }

    @PostMapping("/filme")
    public void addFilme(@RequestBody Filme filme) {
        filmeService.addFilme(filme);
    }
}
