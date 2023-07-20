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

import java.time.LocalDate;
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
            if (!prenotazioneRepository.existsByUser_IdAndVeicolo_Id(prenotazioneRequest.getIdCliente(), prenotazioneRequest.getIdVeicolo())) {
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
                        .operatore(SalSecurityContext.getPrincipal().getUser())
                        .build()
                );
            } else
                throw new RuntimeException("Prenotazione già presente in archivio.");
        } else
            throw new RuntimeException("Utente non abilitato all'inserimento prenotazioni.");
    }

    public List<Prenotazione> prenotazioni(int pageSize, int pageNumber, LocalDate dataInizio, LocalDate dataFine, Long idUser) {
        List<Prenotazione> prenotazioni = null;
        if (Objects.nonNull(dataInizio) && Objects.nonNull(dataFine)) {
            LocalDateTime dataInizioFormattata = dataInizio.atTime(0, 0);
            LocalDateTime dataFineFormattata = dataFine.atTime(0, 0);
            prenotazioni = prenotazioneRepository.findAll().stream().filter(
                prenotazione -> prenotazione.getDataInizio().isAfter(dataInizioFormattata) ||
                    prenotazione.getDataInizio().isEqual(dataInizioFormattata) &&
                        prenotazione.getDataFine().isBefore(dataFineFormattata) ||
                    prenotazione.getDataFine().isEqual(dataFineFormattata)
            ).toList();
        } else if (Objects.nonNull(dataInizio)) {
            LocalDateTime dataInizioFormattata = dataInizio.atTime(0, 0);
            prenotazioni = prenotazioneRepository.findAll().stream().filter(
                prenotazione -> prenotazione.getDataInizio().isAfter(dataInizioFormattata) ||
                    prenotazione.getDataInizio().isEqual(dataInizioFormattata)
            ).toList();
        } else if (Objects.nonNull(dataFine)) {
            LocalDateTime dataFineFormattata = dataFine.atTime(0, 0);
            prenotazioni = prenotazioneRepository.findAll().stream().filter(
                prenotazione -> prenotazione.getDataFine().isBefore(dataFineFormattata) ||
                    prenotazione.getDataFine().isEqual(dataFineFormattata)
            ).toList();
        }
        if (Objects.isNull(prenotazioni))
            prenotazioni = prenotazioneRepository.findAll();
        if (Objects.nonNull(idUser))
            return prenotazioni.stream().filter(
                    prenotazione -> prenotazione.getUser().getId().equals(idUser)
                ).skip((long) pageSize * pageNumber)
                .limit(pageSize)
                .toList();
        else
            return prenotazioni.stream()
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
