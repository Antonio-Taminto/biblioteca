package com.gestionale.biblioteca.service;


import com.gestionale.biblioteca.model.Utente;

import java.util.List;

public interface UtenteService {

    List<Utente> getAllUtente(); //Restituisce l'elenco completo degli utenti.

    Utente getUtenteById(Long id); //Restituisce un utente specifico per ID.

    void addUtente(Utente utente); //Aggiunge un nuovo utente.

    void updateUtente(Long id, Utente utente); //Aggiorna i dettagli di un utente.

    void deleteUtente(Long id); //Rimuove un utente dall'elenco.
}
