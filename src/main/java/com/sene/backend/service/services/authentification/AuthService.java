package com.sene.backend.service.services.authentification;

import com.sene.backend.entity.personne.Utilisateur;
import com.sene.backend.repository.UtilisateurRepository;
import com.sene.backend.security.configurationSecurity.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UtilisateurRepository utilisateurRepository;

    public Map<String, String> login(String email, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            // Si l'authentification réussit, générer un JWT
            Utilisateur utilisateur = (Utilisateur) authentication.getPrincipal();
            String token = jwtService.generate(utilisateur.getEmail()).toString();

            Map<String, String> response = new HashMap<>();
            response.put("token", token); // Ajout du token à la réponse
            return response; // Retourner le token dans la réponse
        } catch (AuthenticationException e) {
            throw new RuntimeException("Échec de la connexion : identifiants incorrects");
        }
    }

    public void logout() {
        // La déconnexion est gérée au niveau client, ou tu peux implémenter un système de blacklist des tokens ici.
    }
}