package Mauro.Salernoflix.controller;

import Mauro.Salernoflix.Config.OpenApiConfig;
import Mauro.Salernoflix.dto.Requests.PrenotazioneRequest;
import Mauro.Salernoflix.model.Prenotazione;
import Mauro.Salernoflix.service.PrenotazioniService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
