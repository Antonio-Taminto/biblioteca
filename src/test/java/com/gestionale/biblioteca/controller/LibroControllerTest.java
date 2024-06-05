package com.gestionale.biblioteca.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionale.biblioteca.model.Libro;
import com.gestionale.biblioteca.model.Utente;

@SpringBootTest
@AutoConfigureMockMvc
public class LibroControllerTest {
	@Autowired
	private LibroController libroController;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void contextLoads() throws Exception {
		// assertThat(libroController).isNotNull();
		Assert.isTrue(libroController != null, "ciao");
	}

	@Test
	void creaLibroTest() throws Exception {
		Libro libro = new Libro();
		libro.setTitolo("titolo");
		libro.setAutore("autore");
		libro.setAnnoPubblicazione(2024);
		String libroJson = objectMapper.writeValueAsString(libro);
		this.mockMvc.perform(post("/libro").contentType(MediaType.APPLICATION_JSON).content(libroJson))
				.andExpect(status().isOk());
	}

	@Test
	void getListaLibriTest() throws Exception {
		MvcResult result = this.mockMvc.perform(get("/libro")).andExpect(status().isOk()).andReturn();

		List libroListFromResult = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);

		Assert.isTrue(libroListFromResult != null, "ciao");
	}

	@Test
	void getLibroFromIdTest() throws Exception {
		Long id = 1L;
		MvcResult result = this.mockMvc.perform(get("/libro/{id}", id)).andExpect(status().isOk()).andReturn();
		Libro libroFromId = objectMapper.readValue(result.getResponse().getContentAsString(), Libro.class);
		assertThat(libroFromId.getId().equals(id));
	}

	@Test
	void updateLibro() throws Exception {
		Long id = 1L;
		Libro libro = new Libro();
		libro.setTitolo("titolo aggiornato");
		libro.setAutore("autore aggiornato");
		libro.setAnnoPubblicazione(2000);
		String libroJson = objectMapper.writeValueAsString(libro);
		this.mockMvc.perform(put("/libro/{id}", id).contentType(MediaType.APPLICATION_JSON).content(libroJson))
				.andExpect(status().isOk());
	}

	@Test
	void deleteLibro() throws Exception {
		Long id = 1L;
		this.mockMvc.perform(delete("/libro/{id}", id)).andExpect(status().isOk());
	}

	@Test
	void lendBookTest() throws Exception {
		Long idLibro = 1L;
		Utente utente = new Utente();
		utente.setId(1L);
		String utenteJson = objectMapper.writeValueAsString(utente);
		this.mockMvc.perform(patch("/libro/lend/{id}", idLibro).contentType(MediaType.APPLICATION_JSON).content(utenteJson))
			.andExpect(status().isOk());
	}

	@Test
	void returnBookTest() throws Exception {
		Long idLibro = 1L;
		this.mockMvc.perform(patch("/libro/return/{id}", idLibro))
			.andExpect(status().isOk());
	}
}
