package com.gestionale.biblioteca.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionale.biblioteca.model.Libro;
import com.gestionale.biblioteca.model.Utente;
import com.gestionale.biblioteca.repository.UtenteRepository;
import com.gestionale.biblioteca.service.UtenteService;

@SpringBootTest
@ActiveProfiles(value = "test")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LibroControllerTest {
	@Autowired
	private LibroController libroController;
	@Autowired
	private UtenteService utenteService;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@Order(1)
	void contextLoads() throws Exception {
		// assertThat(libroController).isNotNull();
		assertThat(libroController).isNotNull();
	}

	@Test
	@Order(2)
	void creaLibroTest() throws Exception {
		Libro libro = new Libro();
		libro.setTitolo("titolo");
		libro.setAutore("autore");
		libro.setDisponibile(true);
		libro.setAnnoPubblicazione(2024);
		String libroJson = objectMapper.writeValueAsString(libro);
		this.mockMvc.perform(post("/libro").contentType(MediaType.APPLICATION_JSON).content(libroJson))
				.andExpect(status().isOk());
	}

	@Test
	@Order(3)
	void getListaLibriTest() throws Exception {
		MvcResult result = this.mockMvc.perform(get("/libro")).andExpect(status().isOk()).andReturn();

		List libroListFromResult = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);

		assertThat(!libroListFromResult.isEmpty());
	}

	@Test
	@Order(4)
	void getLibroFromIdTest() throws Exception {
		Long id = 1L;
		MvcResult result = this.mockMvc.perform(get("/libro/{id}", id)).andExpect(status().isOk()).andReturn();
		Libro libroFromId = objectMapper.readValue(result.getResponse().getContentAsString(), Libro.class);
		assertThat(libroFromId.getId().equals(id));
	}
	
	@Test
	@Order(5)
	void getLibroFromIdNotFoundTest() throws Exception {
		Long id = 2L;
		this.mockMvc.perform(get("/libro/{id}", id)).andExpect(status().isNotFound());

	}

	@Test
	@Order(6)
	void updateLibro() throws Exception {
		Long id = 1L;
		Libro libro = new Libro();
		libro.setTitolo("titolo aggiornato");
		libro.setAutore("autore aggiornato");
		libro.setAnnoPubblicazione(2000);
		libro.setDisponibile(true);
		String libroJson = objectMapper.writeValueAsString(libro);
		this.mockMvc.perform(put("/libro/{id}", id).contentType(MediaType.APPLICATION_JSON).content(libroJson))
				.andExpect(status().isOk());
	}

	@Test
	@Order(7)
	void lendBookTest() throws Exception {
		Long idLibro = 1L;
		Utente utente = new Utente();
		utente.setId(1L);
		utente.setNome("Giacomo");
		utenteService.addUtente(utente);
		String utenteJson = objectMapper.writeValueAsString(utente);
		
		this.mockMvc
				.perform(patch("/libro/lend/{id}", idLibro).contentType(MediaType.APPLICATION_JSON).content(utenteJson))
				.andExpect(status().isOk());
	}

	@Test
	@Order(8)
	void returnBookTest() throws Exception {
		Long idLibro = 1L;
		this.mockMvc.perform(patch("/libro/return/{id}", idLibro)).andExpect(status().isOk());
	}

	@Test
	@Order(9)
	void deleteLibro() throws Exception {
		Long id = 1L;
		this.mockMvc.perform(delete("/libro/{id}", id)).andExpect(status().isOk());
	}
}
