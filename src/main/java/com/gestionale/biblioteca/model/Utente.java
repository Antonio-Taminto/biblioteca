package com.gestionale.biblioteca.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "utenti")
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @OneToMany(mappedBy = "utente",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Libro> libriInPrestitoList;

    public Utente(Long id, List<Libro> libriInPrestitoList, String nome) {
        this.id = id;
        this.libriInPrestitoList = libriInPrestitoList;
        this.nome = nome;
    }

    public Utente() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Libro> getLibriInPrestitoList() {
        return libriInPrestitoList;
    }

    public void setLibriInPrestitoList(List<Libro> libriInPrestitoList) {
        this.libriInPrestitoList = libriInPrestitoList;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
