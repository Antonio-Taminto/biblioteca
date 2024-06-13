package com.gestionale.biblioteca.service.JPAimplementation;

import com.gestionale.biblioteca.exception.EntityNotFoundException;
import com.gestionale.biblioteca.exception.NotAvailableException;
import com.gestionale.biblioteca.model.Libro;
import com.gestionale.biblioteca.model.Utente;
import com.gestionale.biblioteca.repository.LibroRepository;
import com.gestionale.biblioteca.service.LibroService;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Service
public class LibroServiceImpl implements LibroService {
	@Autowired
	private LibroRepository libroRepository;

	@Override
	public List<Libro> getAllBooks() {
		return libroRepository.findAll();
	}

	@Override
	public Libro getBookById(Long id) {
		Optional<Libro> libroOptional = libroRepository.findById(id);
		if (libroOptional.isEmpty()) {
			throw new EntityNotFoundException("Libro non trovato");
		} else {
			return libroOptional.get();
		}
	}

	@Override
	public void addBook(Libro libro) {
		libro.setDisponibile(true);
		libro.setUtente(null);
		libroRepository.save(libro);
	}

	@Override
	public void updateBook(Long id, Libro libro) {
		Libro libroToUpdate = this.getBookById(id);
		BeanUtils.copyProperties(libro, libroToUpdate, "id", "utente");
		libroRepository.save(libroToUpdate);
	}

	@Override
	public void deleteBook(Long id) {
		if (libroRepository.existsById(id)) {
			libroRepository.deleteById(id);

		} else {
			throw new EntityNotFoundException("Libro non trovato");
		}
	}

	@Override
	public void lendBook(Long id, Utente utente) {
		Libro libro = this.getBookById(id);
		if (!libro.isDisponibile()) {
			throw new NotAvailableException("non disponibile");
		}
		Integer numberOfLendBook = libroRepository.findNumberOfBooksLend(utente);
		if (numberOfLendBook >= MAX_LIBRO_DA_PRESTARE) {
			throw new NotAvailableException("massimo numero di libri prestati,restituiscine almeno uno per proseguire");
		}
		try {
			libro.setUtente(utente);
			libro.setDisponibile(false);
			libroRepository.save(libro);
		} catch (DataIntegrityViolationException e) {
			throw new EntityNotFoundException("Utente a cui prestare non trovato");
		}
	}

	@Override
	public void returnBook(Long id) {
		Libro libro = this.getBookById(id);
		if (libro.isDisponibile()) {
			throw new NotAvailableException("non puoi restituire un libro già disponibile");
		} else {
			libro.setDisponibile(true);
			libro.setUtente(null);
			libroRepository.save(libro);
		}
	}
}
