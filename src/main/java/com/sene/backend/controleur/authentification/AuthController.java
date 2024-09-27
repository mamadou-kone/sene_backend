package com.sene.backend.controleur.authentification;

import com.sene.backend.security.dto.AuthentificationDTO;
import com.sene.backend.security.configurationSecurity.JwtService;
import com.sene.backend.service.services.authentification.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping(path = "connexion")
    public Map<String, String> connexion(@RequestBody AuthentificationDTO authentificationDTO) {
        // Log la tentative de connexion
        System.out.println("Tentative de connexion pour l'utilisateur : " + authentificationDTO.username());

        // Authentification avec le manager
        final Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authentificationDTO.username(), authentificationDTO.password())
        );

        // Si l'authentification est réussie
        if (authenticate.isAuthenticated()) {
            System.out.println("Authentification réussie pour l'utilisateur : " + authentificationDTO.username());
            return jwtService.generate(authentificationDTO.username());
        }

        // Si l'authentification échoue
        System.err.println("Échec de l'authentification pour l'utilisateur : " + authentificationDTO.username());
        return null;
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        authService.logout();
        return ResponseEntity.ok("Déconnexion réussie");
    }
}
