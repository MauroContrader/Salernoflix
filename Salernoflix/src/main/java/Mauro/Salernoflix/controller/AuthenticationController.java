package Mauro.Salernoflix.controller;

import Mauro.Salernoflix.Config.OpenApiConfig;
import Mauro.Salernoflix.dto.Requests.AuthenticationRequest;
import Mauro.Salernoflix.dto.Requests.RegisterRequest;
import Mauro.Salernoflix.dto.Responses.AuthenticationResponse;
import Mauro.Salernoflix.model.User;
import Mauro.Salernoflix.service.AuthService;
import Mauro.Salernoflix.service.EmailService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AuthService authService;
    @Autowired
    EmailService emailService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
        @RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authService.authenticate(authenticationRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
        @RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PutMapping("/cambio-password")
    private ResponseEntity<User> cambioPassword(@RequestParam String username,
                                                @RequestParam String nuovaPassword) {
        return ResponseEntity.ok(authService.cambioPassword(username, nuovaPassword));
    }

    @PostMapping("/validita-token")
    private ResponseEntity<Boolean> validitaToken(@RequestParam String token,
                                                  @RequestParam String username) {
        return ResponseEntity.ok(authService.validitaToken(token, username));
    }

    @PostMapping("/email")
    @SecurityRequirement(name = OpenApiConfig.SALERNO_SECURITY_SCHEME)
    private ResponseEntity<Void> sendEmail(@RequestParam String to,
                                           @RequestParam String subject,
                                           @RequestParam String body) {
        emailService.sendEmail(to,subject,body);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/attivazione")
    private ResponseEntity<String> attivaAccount(@RequestParam String codiceDiSicurezza) {
        return ResponseEntity.ok(authService.attivaAccount(codiceDiSicurezza));
    }
}
