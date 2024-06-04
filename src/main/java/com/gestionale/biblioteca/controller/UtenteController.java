package com.gestionale.biblioteca.controller;


import com.gestionale.biblioteca.model.Utente;
import com.gestionale.biblioteca.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("utente")
public class UtenteController {
    @Autowired
    private UtenteService utenteService;

    @GetMapping
    private ResponseEntity<List<Utente>> getAllUtenti(){
        return ResponseEntity.ok(utenteService.getAllUtente());
    }
    @GetMapping("/{id}")
    private ResponseEntity<Utente> getUtenteById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(utenteService.getUtenteById(id));
    }

    @PostMapping
    private ResponseEntity<?> createUtente(@RequestBody Utente utente) {
        utenteService.addUtente(utente);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    private ResponseEntity<?> updateUtente(@PathVariable("id")Long id,@RequestBody Utente utente){
        utenteService.updateUtente(id,utente);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{id}")
    private ResponseEntity<?> deleteUtente(@PathVariable("id") Long id){
        utenteService.deleteUtente(id);
        return ResponseEntity.ok().build();
    }
}
