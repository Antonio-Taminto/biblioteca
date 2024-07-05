package com.gestionale.biblioteca.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.gestionale.biblioteca.model.Utente;

import java.util.List;

public class UtenteProcessor implements ItemProcessor<Utente, Utente> {


	private static final Logger log = LoggerFactory.getLogger(UtenteProcessor.class);

	@Override
	public Utente process(Utente item) throws Exception {
		log.info("processando utente con id: " + item.getId());
		return item;
	}
}
