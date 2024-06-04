package com.gestionale.biblioteca.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "libri")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titolo;

    private String autore;

    private Integer annoPubblicazione;

    private boolean disponibile;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private Utente utente;

    public Libro() {
    }

    public Libro(Integer annoPubblicazione, String autore, boolean disponibile, Long id, String titolo, Utente utente) {
        this.annoPubblicazione = annoPubblicazione;
        this.autore = autore;
        this.disponibile = disponibile;
        this.id = id;
        this.titolo = titolo;
        this.utente = utente;
    }

    public Integer getAnnoPubblicazione() {
        return annoPubblicazione;
    }

    public void setAnnoPubblicazione(Integer annoPubblicazione) {
        this.annoPubblicazione = annoPubblicazione;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public boolean isDisponibile() {
        return disponibile;
    }

    public void setDisponibile(boolean disponibile) {
        this.disponibile = disponibile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }
}
