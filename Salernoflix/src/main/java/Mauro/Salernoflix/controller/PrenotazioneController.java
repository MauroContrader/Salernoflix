package Mauro.Salernoflix.controller;

import Mauro.Salernoflix.Config.OpenApiConfig;
import Mauro.Salernoflix.dto.Requests.PrenotazioneRequest;
import Mauro.Salernoflix.model.Prenotazione;
import Mauro.Salernoflix.service.PrenotazioniService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {

    @Autowired
    PrenotazioniService prenotazioniService;

    @PutMapping("/prenotazione")
    @SecurityRequirement(name = OpenApiConfig.SALERNO_SECURITY_SCHEME)
    public ResponseEntity<Prenotazione> inserisciPrenotazione(PrenotazioneRequest prenotazioneRequest) {
        return ResponseEntity.ok(prenotazioniService.inserisciPrenotazione(prenotazioneRequest));
    }

    @GetMapping("/prenotazioni")
    @SecurityRequirement(name = OpenApiConfig.SALERNO_SECURITY_SCHEME)
    public ResponseEntity<List<Prenotazione>> listaPrenotazioni(@RequestParam(defaultValue = "20") int pageSize,
                                                  @RequestParam(defaultValue = "0") int pageNumber) {
        return ResponseEntity.ok(prenotazioniService.prenotazioni(pageSize, pageNumber));
    }

}
