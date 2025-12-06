package com.cinereviewapp.cinereview_api.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Lista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID numérico automático (1, 2, 3...)
    private Long id;

    private String nome;

    // Relacionamento: Cria uma tabela extra 'lista_filmes' automaticamente no H2
    @ManyToMany
    @JoinTable(
            name = "lista_filmes",
            joinColumns = @JoinColumn(name = "lista_id"),
            inverseJoinColumns = @JoinColumn(name = "filme_id")
    )
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