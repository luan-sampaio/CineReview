package com.cinereviewapp.cinereview_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cinereviewapp.cinereview_api.model.Lista;
import com.cinereviewapp.cinereview_api.service.ListaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/listas")
@Tag(name = "Listas de Favoritos", description = "Gerenciamento de coleções personalizadas de filmes")
public class ListaController {

    @Autowired
    private ListaService listaService;

    @Operation(summary = "Cria uma nova lista", description = "Cria uma lista vazia apenas com o nome definido")
    @ApiResponse(responseCode = "200", description = "Lista criada com sucesso")
    @PostMapping
    public Lista criarLista(@RequestBody Lista lista) {
        return listaService.createLista(lista.getNome());
    }

    @Operation(summary = "Exibe todas as listas", description = "Retorna todas as listas cadastradas e seus respectivos filmes")
    @GetMapping
    public List<Lista> listarTodas() {
        return listaService.getAllListas();
    }

    @Operation(summary = "Adiciona filme à lista", description = "Vincula um filme existente a uma lista existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Filme adicionado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Lista ou Filme não encontrados")
    })
    @PostMapping("/{listaId}/adicionar/{filmeId}")
    public ResponseEntity<Lista> adicionarFilme(
            @PathVariable Long listaId,
            @PathVariable String filmeId) {

        Lista listaAtualizada = listaService.addFilmeToLista(listaId, filmeId);
        return ResponseEntity.ok(listaAtualizada);
    }

    @Operation(summary = "Exclui uma lista", description = "Remove a lista inteira do banco de dados (não apaga os filmes, só a lista)")
    @ApiResponse(responseCode = "204", description = "Lista deletada com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLista(@PathVariable Long id) {
        listaService.deleteLista(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Remove filme da lista", description = "Desvincula um filme de uma lista específica, mas mantém o filme no banco")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Filme removido da lista"),
        @ApiResponse(responseCode = "404", description = "Filme não estava na lista ou Lista não encontrada")})
    @DeleteMapping("/{listaId}/remover/{filmeId}")
    public ResponseEntity<Lista> removerFilmeDaLista(
            @PathVariable Long listaId,
            @PathVariable String filmeId) {

        Lista listaAtualizada = listaService.removeFilmeFromLista(listaId, filmeId);

        return ResponseEntity.ok(listaAtualizada);
    }
}
