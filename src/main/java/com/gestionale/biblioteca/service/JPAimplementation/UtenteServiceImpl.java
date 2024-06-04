package com.gestionale.biblioteca.service.JPAimplementation;

import com.gestionale.biblioteca.exception.EntityNotFoundException;
import com.gestionale.biblioteca.model.Utente;
import com.gestionale.biblioteca.repository.UtenteRepository;
import com.gestionale.biblioteca.service.UtenteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtenteServiceImpl implements UtenteService {
    @Autowired
    private UtenteRepository utenteRepository;

    @Override
    public List<Utente> getAllUtente() {
        return utenteRepository.findAll();
    }

    @Override
    public Utente getUtenteById(Long id) {
        return utenteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Utente non trovato"));
    }

    @Override
    public void addUtente(Utente utente) {
        utenteRepository.save(utente);
    }

    @Override
    public void updateUtente(Long id, Utente utente) {
        Utente utenteToUpdate = this.getUtenteById(id);
        BeanUtils.copyProperties(utente,utenteToUpdate,"libriInPrestitoList","id");
        utenteRepository.save(utenteToUpdate);
    }

    @Override
    public void deleteUtente(Long id) {
        if (utenteRepository.existsById(id))
            utenteRepository.deleteById(id);
        else throw new EntityNotFoundException("Utente non trovato");
    }
}
