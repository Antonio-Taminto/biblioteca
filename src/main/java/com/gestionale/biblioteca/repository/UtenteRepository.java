package com.gestionale.biblioteca.repository;

import com.gestionale.biblioteca.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {

    @Query("SELECT u FROM Utente u WHERE creationDate BETWEEN ?1 AND ?2")
    List<Utente> findBeetweenCreationDate(LocalDate start,LocalDate end);

}
