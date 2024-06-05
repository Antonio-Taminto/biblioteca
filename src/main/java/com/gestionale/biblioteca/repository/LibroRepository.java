package com.gestionale.biblioteca.repository;

import com.gestionale.biblioteca.model.Libro;
import com.gestionale.biblioteca.model.Utente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
	@Query("SELECT COUNT(utente) FROM Libro l WHERE utente = ?1")
	Integer findNumberOfBooksLend(Utente utente);
}
