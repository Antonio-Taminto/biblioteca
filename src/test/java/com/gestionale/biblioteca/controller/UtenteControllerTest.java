package com.gestionale.biblioteca.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionale.biblioteca.model.Utente;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles(value = "test")
public class UtenteControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private UtenteController utenteController;

	@Test
	@Order(1)
	void contextLoads() throws Exception {
		// assertThat(utenteController).isNotNull();
		assertThat(utenteController).isNotNull();
	}

	@Test
	@Order(2)
	void creaUtenteTest() throws Exception {
		Utente utente = new Utente();
		utente.setNome("Giovanni");
		String utenteJson = objectMapper.writeValueAsString(utente);
		mockMvc.perform(post("/utente").contentType(MediaType.APPLICATION_JSON).content(utenteJson))
				.andExpect(status().isOk());
	}

	@Test
	@Order(3)
	void getUtenteByIdTest() throws Exception {
		Long id = 1L;
		mockMvc.perform(get("/utente/{id}", id)).andExpect(status().isOk())
				.andExpectAll(MockMvcResultMatchers.jsonPath("$.id").value(id));
	}

	@Test
	@Order(4)
	void getAllUtenteTest() throws Exception {
		mockMvc.perform(get("/utente")).andExpect(status().isOk())
				.andExpectAll(MockMvcResultMatchers.jsonPath("$.[*]").isNotEmpty());
	}
}
