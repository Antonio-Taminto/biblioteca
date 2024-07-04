package com.gestionale.biblioteca.batch;

import com.gestionale.biblioteca.model.Utente;
import org.springframework.batch.item.ItemStreamReader;

import java.util.List;

public class UtenteReaderList implements ItemStreamReader<Utente> {

    private List<Utente> utenteList;

    public UtenteReaderList(List<Utente> utenteList) {
        super();
        this.utenteList = utenteList;
    }

    @Override
    public Utente read() throws Exception {
        Utente utente = utenteList.removeFirst();

        return utente;
    }

}
