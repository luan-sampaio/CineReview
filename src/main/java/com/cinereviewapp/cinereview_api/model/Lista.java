package com.cinereviewapp.cinereview_api.model;

import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Lista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID numérico automático (1, 2, 3...)
    @Schema(description = "ID único da lista (Gerado automaticamente)", example = "1")
    private Long id;

    @NotBlank(message = "O nome da lista não pode estar vazio")
    @Schema(description = "Nome personalizado da lista", example = "Meus Filmes de Terror Favoritos")
    private String nome;

    // Relacionamento: Cria uma tabela extra 'lista_filmes' automaticamente no H2
    @ManyToMany
    @JoinTable(
            name = "lista_filmes",
            joinColumns = @JoinColumn(name = "lista_id"),
            inverseJoinColumns = @JoinColumn(name = "filme_id")
    )
    @Schema(description = "Conjunto de filmes que pertencem a esta lista")
    private List<Filme> filmes = new ArrayList<>();

    public Lista() {}

    public Lista(String nome) {
        this.nome = nome;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public List<Filme> getFilmes() { return filmes; }
    public void setFilmes(List<Filme> filmes) { this.filmes = filmes; }
}
