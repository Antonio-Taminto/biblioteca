package com.gestionale.biblioteca.batch;

import org.springframework.batch.item.ItemProcessor;

import com.gestionale.biblioteca.model.Utente;

public class UtenteProcessor implements ItemProcessor<Utente, Utente> {

	
	@Override
	public Utente process(Utente item) throws Exception {
		// TODO Auto-generated method stub
		return item;
	}

	
}
