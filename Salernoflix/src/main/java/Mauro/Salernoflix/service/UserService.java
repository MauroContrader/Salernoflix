package Mauro.Salernoflix.service;


import Mauro.Salernoflix.dto.Requests.AnagraficaUtenteRequest;
import Mauro.Salernoflix.model.AnagraficaUtente;
import Mauro.Salernoflix.model.User;
import Mauro.Salernoflix.repository.AnagraficaUtenteRepository;
import Mauro.Salernoflix.repository.UserRepository;
import Mauro.Salernoflix.security.SalSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    AnagraficaUtenteRepository anagraficaUtenteRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public AnagraficaUtente creaAnagraficaUtente(AnagraficaUtenteRequest anagraficaUtenteRequest) {
        if (Objects.isNull(SalSecurityContext.getPrincipal().getAnagraficaUtente())) {
            User user = userRepository.findByUsername(SalSecurityContext.getPrincipal().getUsername());
            AnagraficaUtente anagraficaUtente = AnagraficaUtente.builder()
                .nome(anagraficaUtenteRequest.getNome())
                .cognome(anagraficaUtenteRequest.getCognome())
                .citta(anagraficaUtenteRequest.getCitta())
                .codiceFiscale(anagraficaUtenteRequest.getCodiceFiscale())
                .indirizzo(anagraficaUtenteRequest.getIndirizzo())
                .email(anagraficaUtenteRequest.getEmail())
                .cellulare(anagraficaUtenteRequest.getCellulare())
                .build();
            anagraficaUtenteRepository.save(anagraficaUtente);
            user.setAnagraficaUtente(anagraficaUtente);
            userRepository.save(user);
            return user.getAnagraficaUtente();
        }
        throw new RuntimeException("Anagrafica utente già presente");
    }

    public AnagraficaUtente getAnagraficaUtente() {
        return Objects.nonNull(SalSecurityContext.getPrincipal().getAnagraficaUtente())
            ? SalSecurityContext.getPrincipal().getAnagraficaUtente()
            : null;
    }

}
