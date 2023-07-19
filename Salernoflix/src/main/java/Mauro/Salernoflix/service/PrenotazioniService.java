package Mauro.Salernoflix.service;

import Mauro.Salernoflix.dto.Enum.Role;
import Mauro.Salernoflix.dto.Enum.TipologiaVeicolo;
import Mauro.Salernoflix.dto.Requests.PrenotazioneRequest;
import Mauro.Salernoflix.model.Prenotazione;
import Mauro.Salernoflix.repository.PrenotazioneRepository;
import Mauro.Salernoflix.repository.UserRepository;
import Mauro.Salernoflix.repository.VeicoloRepository;
import Mauro.Salernoflix.security.SalSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PrenotazioniService {

    @Autowired
    PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VeicoloRepository veicoloRepository;

    public Prenotazione inserisciPrenotazione(PrenotazioneRequest prenotazioneRequest) {

        if (SalSecurityContext.getPrincipal().getRole().equals(Role.ADMIN)) {
            if (!prenotazioneRepository.existsByUser_IdAndVeicolo_Id(prenotazioneRequest.getIdCliente(),prenotazioneRequest.getIdVeicolo())) {
                return prenotazioneRepository.save(
                    Prenotazione.builder()
                        .dataInizio(prenotazioneRequest.getDataInizio())
                        .dataFine(prenotazioneRequest.getDataFine())
                        .user(userRepository.findById(prenotazioneRequest.getIdCliente()).orElseThrow(
                            () -> new RuntimeException("Utente con id " + prenotazioneRequest.getIdCliente() + " non trovato")
                        ))
                        .veicolo(veicoloRepository.findById(prenotazioneRequest.getIdVeicolo()).orElseThrow(
                            () -> new RuntimeException("Veicolo con id: " + prenotazioneRequest.getIdVeicolo() + " non trovato")
                        ))
                        .build()
                );
            } else
                throw new RuntimeException("Prenotazione già presente in archivio");
        } else
            throw new RuntimeException("Utente non abilitato all'inserimento prenotazioni.");
    }

    public List<Prenotazione> prenotazioni(int pageSize, int pageNumber, LocalDateTime dataInizio, LocalDateTime dataFine) {
        if (Objects.nonNull(dataInizio) && Objects.nonNull(dataFine)) {
            return prenotazioneRepository.findAll().stream().filter(
                    prenotazione -> prenotazione.getDataInizio().isAfter(dataInizio)  ||
                        prenotazione.getDataInizio().isEqual(dataInizio) &&
                        prenotazione.getDataFine().isBefore(dataFine) ||
                        prenotazione.getDataFine().isEqual(dataFine)
                ).skip((long) pageSize * pageNumber)
                .limit(pageSize)
                .toList();
        } else if (Objects.nonNull(dataInizio)) {
            return prenotazioneRepository.findAll().stream().filter(
                    prenotazione -> prenotazione.getDataInizio().isAfter(dataInizio) ||
                        prenotazione.getDataInizio().isEqual(dataInizio)
                ).skip((long) pageSize * pageNumber)
                .limit(pageSize)
                .toList();
        } else if (Objects.nonNull(dataFine)) {
            return prenotazioneRepository.findAll().stream().filter(
                    prenotazione -> prenotazione.getDataFine().isBefore(dataFine) ||
                        prenotazione.getDataFine().isEqual(dataFine)
                ).skip((long) pageSize * pageNumber)
                .limit(pageSize)
                .toList();
        }
        return prenotazioneRepository.findAll().stream()
            .skip((long) pageSize * pageNumber)
            .limit(pageSize)
            .toList();

    }

    public void eliminaPrenotazioni(List<Long> idPrenotazioni) {
        if (SalSecurityContext.getPrincipal().getRole().equals(Role.ADMIN)) {
            if (Objects.nonNull(idPrenotazioni)) {
                prenotazioneRepository.deleteAllById(idPrenotazioni);
            } else
                throw new RuntimeException("Non sono stati inseriti id da eliminare.");
        } else
            throw new RuntimeException("Utente non abilitato alla cancellazione delle prenotazioni.");
    }
}
