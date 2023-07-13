package Mauro.Salernoflix.controller;

import Mauro.Salernoflix.Config.OpenApiConfig;
import Mauro.Salernoflix.dto.Requests.AnagraficaUtenteRequest;
import Mauro.Salernoflix.model.AnagraficaUtente;
import Mauro.Salernoflix.model.User;
import Mauro.Salernoflix.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/anagraficaUtente")
    @SecurityRequirement(name = OpenApiConfig.SALERNO_SECURITY_SCHEME)
    public ResponseEntity<AnagraficaUtente> creaAnagraficaUtente(AnagraficaUtenteRequest anagraficaUtenteRequest) {
        return ResponseEntity.ok(userService.creaAnagraficaUtente(anagraficaUtenteRequest));
    }

    @GetMapping("/users")
    @SecurityRequirement(name = OpenApiConfig.SALERNO_SECURITY_SCHEME)
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/anagraficaUtente")
    @SecurityRequirement(name = OpenApiConfig.SALERNO_SECURITY_SCHEME)
    public ResponseEntity<AnagraficaUtente> getAnagraficaUtente() {
        return ResponseEntity.ok(userService.getAnagraficaUtente());
    }

}
