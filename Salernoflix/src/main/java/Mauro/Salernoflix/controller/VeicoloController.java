package Mauro.Salernoflix.controller;

import Mauro.Salernoflix.Config.OpenApiConfig;
import Mauro.Salernoflix.dto.Requests.VeicoloRequest;
import Mauro.Salernoflix.model.Veicolo;
import Mauro.Salernoflix.service.VeicoloService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/veicolo")
public class VeicoloController {

    @Autowired
    VeicoloService veicoloService;

    @PutMapping("/veicolo")
    @SecurityRequirement(name = OpenApiConfig.SALERNO_SECURITY_SCHEME)
    private ResponseEntity<Veicolo> creaVeicolo(VeicoloRequest veicoloRequest) {
        return ResponseEntity.ok(veicoloService.creaVeicolo(veicoloRequest));
    }

    @DeleteMapping("/veicolo")
    @SecurityRequirement(name = OpenApiConfig.SALERNO_SECURITY_SCHEME)
    private ResponseEntity<Void> eliminaVeicoloBySeriale(@RequestParam String seriale) {
        veicoloService.eliminaVeicolo(seriale);
        return ResponseEntity.ok().build();
    }


}
