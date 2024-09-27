package com.sene.backend.security.configurationSecurity;

import com.sene.backend.entity.personne.Utilisateur;
import com.sene.backend.repository.UtilisateurRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CurrentUserService {
    private UtilisateurRepository utilisateurRepository;
    public Long getCurrentUtilisateurId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Utilisateur> user = utilisateurRepository.findByEmail(authentication.getName());  // Utilisation du repository Utilisateur

        Long id = 0L;
        if (user.isPresent()) {
            id = user.get().getId();  // Récupération de l'ID de l'utilisateur
        }

        return id;
    }



}