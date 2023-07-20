package Mauro.Salernoflix.controller;

import Mauro.Salernoflix.Config.OpenApiConfig;
import Mauro.Salernoflix.dto.Requests.PrenotazioneRequest;
import Mauro.Salernoflix.model.Prenotazione;
import Mauro.Salernoflix.service.PrenotazioniService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {

    @Autowired
    PrenotazioniService prenotazioniService;

    @PostMapping(value = "/prenotazione")
    @SecurityRequirement(name = OpenApiConfig.SALERNO_SECURITY_SCHEME)
    public ResponseEntity<Prenotazione> inserisciPrenotazione(@RequestBody PrenotazioneRequest prenotazioneRequest) {
        return ResponseEntity.ok(prenotazioniService.inserisciPrenotazione(prenotazioneRequest));
    }

    @GetMapping("/prenotazioni")
    @SecurityRequirement(name = OpenApiConfig.SALERNO_SECURITY_SCHEME)
    public ResponseEntity<List<Prenotazione>> listaPrenotazioni(@RequestParam(defaultValue = "20") int pageSize,
                                                                @RequestParam(defaultValue = "0") int pageNumber,
                                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                @RequestParam(required = false) LocalDate dataInizio,
                                                                @RequestParam(required = false) LocalDate dataFine,
                                                                @RequestParam(required = false) Long idUser) {
        return ResponseEntity.ok(prenotazioniService.prenotazioni(pageSize, pageNumber, dataInizio, dataFine, idUser));
    }

    @DeleteMapping("/prenotazioni")
    @SecurityRequirement(name = OpenApiConfig.SALERNO_SECURITY_SCHEME)
    public ResponseEntity<Void> eliminaPrenotazioni(@RequestBody @NonNull List<Long> idPrenotazioni) {
        prenotazioniService.eliminaPrenotazioni(idPrenotazioni);
        return ResponseEntity.ok().build();
    }

}
