package Mauro.Salernoflix.security;

import Mauro.Salernoflix.Config.JwtService;
import Mauro.Salernoflix.dto.Enum.Role;
import Mauro.Salernoflix.model.AnagraficaUtente;
import Mauro.Salernoflix.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.Serializable;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecurityPrincipal implements Serializable {

    @Autowired
    JwtService jwtService;

    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public String getUsername() {
        return user.getUsername();
    }

    public Role getRole() {
        return user.getRole();
    }

    public Long getId() {
        return user.getId();
    }

    public AnagraficaUtente getAnagraficaUtente() {
        return user.getAnagraficaUtente();
    }

}