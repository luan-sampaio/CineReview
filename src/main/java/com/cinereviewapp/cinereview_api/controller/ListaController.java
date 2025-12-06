package com.cinereviewapp.cinereview_api.controller;

import com.cinereviewapp.cinereview_api.model.Lista;
import com.cinereviewapp.cinereview_api.service.ListaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/listas")
public class ListaController {

    @Autowired
    private ListaService listaService;

    // 1. Criar uma nova lista
    @PostMapping
    public Lista criarLista(@RequestBody Lista lista) {
        return listaService.createLista(lista.getNome());
    }

    // 2. Ver todas as listas (e os filmes dentro delas)
    @GetMapping
    public List<Lista> listarTodas() {
        return listaService.getAllListas();
    }

    // 3. Adicionar um filme a uma lista específica
    @PostMapping("/{listaId}/adicionar/{filmeId}")
    public ResponseEntity<Lista> adicionarFilme(
            @PathVariable Long listaId,
            @PathVariable String filmeId) {

        Lista listaAtualizada = listaService.addFilmeToLista(listaId, filmeId);
        return ResponseEntity.ok(listaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLista(@PathVariable Long id) {
        listaService.deleteLista(id);
        return ResponseEntity.noContent().build();
    }

    // URL: DELETE /api/listas/{listaId}/remover/{filmeId}
    @DeleteMapping("/{listaId}/remover/{filmeId}")
    public ResponseEntity<Lista> removerFilmeDaLista(
            @PathVariable Long listaId,
            @PathVariable String filmeId) {

        Lista listaAtualizada = listaService.removeFilmeFromLista(listaId, filmeId);

        // Retorna a lista atualizada (sem o filme) para você conferir visualmente
        return ResponseEntity.ok(listaAtualizada);
    }
}