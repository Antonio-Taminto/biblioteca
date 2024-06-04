package com.gestionale.biblioteca.controller;

import com.gestionale.biblioteca.model.Libro;
import com.gestionale.biblioteca.model.Utente;
import com.gestionale.biblioteca.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/libro")
public class LibroController {
    @Autowired
    private LibroService libroService;

    @GetMapping
    private ResponseEntity<List<Libro>> getAllLibro() {
        return ResponseEntity.ok(libroService.getAllBooks());
    }

    @GetMapping("/{id}")
    private ResponseEntity<Libro> getLibroById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(libroService.getBookById(id));
    }

    @PostMapping
    private ResponseEntity<?> createLibro(@RequestBody Libro libro) {
        libroService.addBook(libro);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    private ResponseEntity<?> updateLibro(@PathVariable("id")Long id,@RequestBody Libro libro){
        libroService.updateBook(id,libro);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{id}")
    private ResponseEntity<?> deleteLibro(@PathVariable("id") Long id){
        libroService.deleteBook(id);
        return ResponseEntity.ok().build();
    }
    @PatchMapping("/lend/{id}")
    private ResponseEntity<Libro> lendLibroById(@PathVariable("id") Long id, @RequestBody Utente utente) {
        libroService.lendBook(id,utente);
        return ResponseEntity.ok().build();
    }
    @PatchMapping("/return/{id}")
    private ResponseEntity<Libro> returnLibroById(@PathVariable("id") Long id) {
        libroService.returnBook(id);
        return ResponseEntity.ok().build();
    }
}
