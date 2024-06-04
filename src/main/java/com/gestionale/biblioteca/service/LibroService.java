package com.gestionale.biblioteca.service;

import com.gestionale.biblioteca.model.Libro;
import com.gestionale.biblioteca.model.Utente;

import java.util.List;

public interface LibroService {
    Integer MAX_LIBRO_DA_PRESTARE = 10;

    List<Libro> getAllBooks(); //Restituisce l'elenco completo dei libri.

    Libro getBookById(Long id); //Restituisce un libro specifico per ID.

    void addBook(Libro libro); //Aggiunge un nuovo libro.

    void updateBook(Long id, Libro libro); //Aggiorna i dettagli di un libro.

    void deleteBook(Long id); //Rimuove un libro dall'elenco.

    void lendBook(Long id, Utente utente); //Gestisce il prestito di un libro.

    void returnBook(Long id); //Gestisce il ritorno di un libro prestato.
}
