package Mauro.Salernoflix.service;

import Mauro.Salernoflix.Config.JwtService;
import Mauro.Salernoflix.dto.Enum.Role;
import Mauro.Salernoflix.dto.Requests.AuthenticationRequest;
import Mauro.Salernoflix.dto.Requests.RegisterRequest;
import Mauro.Salernoflix.dto.Responses.AuthenticationResponse;
import Mauro.Salernoflix.model.User;
import Mauro.Salernoflix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthService {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtService jwtService;

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(),
                authenticationRequest.getPassword()
            )
        );
        var user = userRepository.findByUsername(authenticationRequest.getUsername());
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
            .builder()
            .token(jwtToken)
            .build();
    }

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        User user = userRepository.findByUsername(registerRequest.getUsername());
        if (Objects.isNull(user)) {
            user = User.builder()
                .username(registerRequest.getUsername())
                .password(bCryptPasswordEncoder.encode(registerRequest.getPassword()))
                .role(registerRequest.getRole())
                .build();
            userRepository.save(user);
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
        }
        throw new RuntimeException("Utente già iscritto.");
    }

    public boolean validitaToken(String token, String username) {
        UserDetails user = userRepository.findByUsername(username);
        return jwtService.isTokenValid(token, user);
    }

    public User cambioPassword(String username, String nuovaPassword) {
        User user = userRepository.findByUsername(username);
        if (Objects.nonNull(user) && !bCryptPasswordEncoder.matches(nuovaPassword, user.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(nuovaPassword));
            return userRepository.save(user);
        }
        throw new RuntimeException("Errore nel cambio della password");
    }

}
