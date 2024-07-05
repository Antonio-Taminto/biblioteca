package com.gestionale.biblioteca.batch;

import com.gestionale.biblioteca.model.Utente;
import com.gestionale.biblioteca.repository.UtenteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class UtenteReaderList implements ItemStreamReader<Utente> {

    private static final Logger log = LoggerFactory.getLogger(UtenteReaderList.class);
    private List<Utente> utenteList;
    @Autowired
    private UtenteRepository utenteRepository;







    @Override
    public Utente read() throws Exception {
        if (utenteList == null) {
            utenteList = utenteRepository.findBeetweenCreationDate( LocalDate.now().minusMonths(1),LocalDate.now());
        }
        if (!utenteList.isEmpty()) {
            return utenteList.removeFirst();
        }
        utenteList = null;
        return null;
    }

}
