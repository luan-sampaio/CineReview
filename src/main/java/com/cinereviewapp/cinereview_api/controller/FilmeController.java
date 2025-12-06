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
import com.cinereviewapp.cinereview_api.service.TmdbService;


@RestController
@RequestMapping("/api/filmes")
public class FilmeController {
    
    private FilmeService filmeService;
    private ReviewService reviewService;
    private TmdbService tmdbService;

    @Autowired
    public FilmeController(FilmeService filmeService, ReviewService reviewService, TmdbService tmdbService) {
        this.filmeService = filmeService;
        this.reviewService = reviewService;
        this.tmdbService = tmdbService;
    }

    //GET /api/filmes
    @GetMapping
    public List<Filme> getFilmes() {
        return filmeService.getFilmes();
    }
    

    // URL: GET /api/filmes/busca?titulo=
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

    // GET /api/filmes/tmdb?titulo=
    @GetMapping("/tmdb")
    public ResponseEntity<List<Filme>> buscarFilmesExternos(@RequestParam String titulo) {
        
        // Agora o serviço retorna uma LISTA de objetos Filme, não mais uma String
        List<Filme> filmes = tmdbService.buscarFilmesPorTitulo(titulo);
        
        return ResponseEntity.ok(filmes);
    }

    @PostMapping
    public Filme addFilme(@RequestBody Filme filme) {
        return filmeService.addFilme(filme);
    }

    // POST /api/filmes/importar/550
    // (550 é o ID do Clube da Luta no TMDB, por exemplo)
    @PostMapping("/importar/{idTmdb}")
    public ResponseEntity<Filme> importarFilmeDoTmdb(@PathVariable String idTmdb) {
        
        if (filmeService.getFilmePorId(idTmdb).isPresent()) {
            return ResponseEntity.status(409).build(); 
        }

        Filme filmeParaSalvar = tmdbService.buscarFilmePorId(idTmdb);

        if (filmeParaSalvar == null) {
            return ResponseEntity.notFound().build();
        }

        Filme filmeSalvo = filmeService.addFilme(filmeParaSalvar);

        return ResponseEntity.ok(filmeSalvo);
    }
    
    
    @PutMapping("/{id}")
    public ResponseEntity<Filme> updateFilme(@PathVariable String id, @RequestBody Filme filmeDetalhes) {
        Filme updatedFilme = filmeService.updateFilme(id, filmeDetalhes);
        return ResponseEntity.ok(updatedFilme);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilme(@PathVariable String id) {
        var filmeOpt = filmeService.getFilmePorId(id);

        if (filmeOpt.isPresent()) {
            String filmeId = filmeOpt.get().getId();

            reviewService.deleteReviewsPorFilmeId(filmeId);

            filmeService.deleteFilme(id);

            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
