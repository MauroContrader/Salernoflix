package Mauro.Salernoflix.controller;

import Mauro.Salernoflix.Config.OpenApiConfig;
import Mauro.Salernoflix.dto.Enum.TipologiaContratto;
import Mauro.Salernoflix.dto.Requests.ContrattoRequest;
import Mauro.Salernoflix.dto.Responses.ContrattoResponse;
import Mauro.Salernoflix.model.Contratto;
import Mauro.Salernoflix.service.ContrattiService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Contratti")
public class ContrattoController {

    @Autowired
    ContrattiService contrattiService;

    @PutMapping("/contratto")
    @SecurityRequirement(name = OpenApiConfig.SALERNO_SECURITY_SCHEME)
    public ResponseEntity<Contratto> inserisciContratto(@RequestBody ContrattoRequest contrattoRequest) {
        return ResponseEntity.ok(contrattiService.inserisciContratto(contrattoRequest));
    }

    @DeleteMapping("/contratto")
    @SecurityRequirement(name = OpenApiConfig.SALERNO_SECURITY_SCHEME)
    public ResponseEntity<Void> eliminaContratto(@RequestParam Long idContratto) {
        contrattiService.eliminaContratto(idContratto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/contratti-tipologia")
    @SecurityRequirement(name = OpenApiConfig.SALERNO_SECURITY_SCHEME)
    public ResponseEntity<List<ContrattoResponse>> visualizzaContrattiByTipologia(@RequestParam(required = false) TipologiaContratto tipologiaContratto,
                                                                                  @RequestParam(defaultValue = "20") int pageSize,
                                                                                  @RequestParam(defaultValue = "0") int pageNumber) {
        return ResponseEntity.ok(contrattiService.visualizzaContrattiByTipologia(tipologiaContratto,pageSize,pageNumber));
    }
}
