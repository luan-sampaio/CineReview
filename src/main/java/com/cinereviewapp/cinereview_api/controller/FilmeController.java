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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/filmes")
@Tag(name = "Filmes", description = "Gerenciamento do catálogo de filmes (CRUD e Integração TMDB)")
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

    @Operation(summary = "Lista todos os filmes", description = "Retorna uma lista completa de filmes salvos no banco de dados local")
    @ApiResponse(responseCode = "200", description = "Operação bem sucedida")
    @GetMapping
    public List<Filme> getFilmes() {
        return filmeService.getFilmes();
    }
    

    @Operation(summary = "Busca local por título", description = "Procura um filme no banco de dados local pelo nome exato")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Filme encontrado"),
        @ApiResponse(responseCode = "404", description = "Filme não encontrado")
    })
    @GetMapping("/busca") 
    public ResponseEntity<Filme> getFilmePorTitulo(@RequestParam("titulo") String titulo) {
        return filmeService.getFilmePorNome(titulo)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Filme não encontrado com título: " + titulo));
    }

    @Operation(summary = "Busca local por ID", description = "Retorna os detalhes de um filme específico pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Filme encontrado"),
        @ApiResponse(responseCode = "404", description = "ID não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Filme> getFilmePorId(@PathVariable String id) {
        Filme filme = filmeService.getFilmePorId(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Filme não encontrado com ID " + id));
        return ResponseEntity.ok(filme);
    }

    @Operation(summary = "Pesquisa no TMDB (Externo)", description = "Consulta a API do TMDB e retorna uma lista de possíveis filmes. Não salva no banco.")
    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso")
    @GetMapping("/tmdb")
    public ResponseEntity<List<Filme>> buscarFilmesExternos(@RequestParam String titulo) {
        
        // Agora o serviço retorna uma LISTA de objetos Filme, não mais uma String
        List<Filme> filmes = tmdbService.buscarFilmesPorTitulo(titulo);
        
        return ResponseEntity.ok(filmes);
    }

    @Operation(summary = "Adiciona filme manualmente", description = "Cria um novo filme passando todos os dados no corpo da requisição")
    @ApiResponse(responseCode = "200", description = "Filme criado com sucesso")
    @PostMapping
    public Filme addFilme(@RequestBody Filme filme) {
        return filmeService.addFilme(filme);
    }

    @Operation(summary = "Importa filme do TMDB", description = "Busca os detalhes completos no TMDB pelo ID e salva automaticamente no banco local")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Filme importado e salvo com sucesso"),
        @ApiResponse(responseCode = "409", description = "O filme já existe no banco de dados"), // Documentando o seu Conflict
        @ApiResponse(responseCode = "404", description = "ID não encontrado no TMDB")
    })
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
    
    @Operation(summary = "Atualiza um filme", description = "Atualiza os dados de um filme existente pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Filme atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Filme não encontrado para atualização")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Filme> updateFilme(@PathVariable String id, @RequestBody Filme filmeDetalhes) {
        Filme updatedFilme = filmeService.updateFilme(id, filmeDetalhes);
        return ResponseEntity.ok(updatedFilme);
    }

    @Operation(summary = "Deleta um filme", description = "Remove um filme e todas as suas reviews associadas do banco de dados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Filme deletado com sucesso (Sem conteúdo)"),
        @ApiResponse(responseCode = "404", description = "Filme não encontrado")
    })
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
