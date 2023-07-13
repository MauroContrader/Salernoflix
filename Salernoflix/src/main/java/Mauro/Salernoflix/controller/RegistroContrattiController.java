package Mauro.Salernoflix.controller;

import Mauro.Salernoflix.Config.OpenApiConfig;
import Mauro.Salernoflix.dto.Requests.ContrattoRequest;
import Mauro.Salernoflix.model.RegistroContratti;
import Mauro.Salernoflix.service.RegistroContrattiService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Contratti")
public class RegistroContrattiController {

    @Autowired
    RegistroContrattiService registroContrattiService;

    @PostMapping("/contratto")
    @SecurityRequirement(name = OpenApiConfig.SALERNO_SECURITY_SCHEME)
    public ResponseEntity<RegistroContratti> inserisciContratto(@RequestBody ContrattoRequest contrattoRequest) {
        return ResponseEntity.ok(registroContrattiService.inserisciContratto(contrattoRequest));
    }

}
