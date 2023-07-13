package Mauro.Salernoflix.controller;

import Mauro.Salernoflix.dto.Requests.AuthenticationRequest;
import Mauro.Salernoflix.dto.Requests.RegisterRequest;
import Mauro.Salernoflix.dto.Responses.AuthenticationResponse;
import Mauro.Salernoflix.model.User;
import Mauro.Salernoflix.service.AuthService;
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

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
        @RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authService.authenticate(authenticationRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
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

}
