package com.sene.backend.controleur;

import com.sene.backend.entity.personne.Utilisateur;
import com.sene.backend.security.configurationSecurity.UtilisateurService;
import com.sene.backend.security.dto.statuts.StatutCompteDTO;
import com.sene.backend.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/utilisateur")
public class UtilisateurController {

    @Autowired
    private UserService utilisateurService;

    @Autowired
    private UtilisateurService utilisateur; // Injection du service

    @GetMapping("/total")
    public long getTotalUsers() {
        // Appel de la méthode du service pour récupérer le nombre total d'utilisateurs
        return utilisateur.getTotalUsers();
    }




    // Liste des Admins
    @GetMapping
    public ResponseEntity<List<Utilisateur>> liste() {
        List<Utilisateur> utilisateurs = utilisateurService.liste();
        return ResponseEntity.ok(utilisateurs);
    }

    // Trouver un Admin par ID
    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> trouverParId(@PathVariable Long id) {
        Optional<Utilisateur> utilisateurnOpt = utilisateurService.trouverParId(id);
        return utilisateurnOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Mise à jour d'un Admin
    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> miseAJour(@PathVariable Long id, @RequestBody Utilisateur utilisateur) {
        Utilisateur updatedUtilisateur = utilisateurService.miseAJour(utilisateur, id);
        if (updatedUtilisateur != null) {
            return ResponseEntity.ok(updatedUtilisateur);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Suppression d'un Admin par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        utilisateurService.supprimer(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> ActDesactCompte(@PathVariable Long id, @RequestBody StatutCompteDTO statutCompteDTO) {
        // Pas besoin d'instancier Utilisateur directement
        Utilisateur utilisateur = new Utilisateur() {
            // Implémenter les méthodes abstraites si nécessaire
            @Override
            public String getUsername() {
                return null; // À définir si besoin
            }

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null; // À définir si besoin
            }

            @Override
            public boolean isAccountNonExpired() {
                return true; // Par défaut
            }

            @Override
            public boolean isAccountNonLocked() {
                return true; // Par défaut
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true; // Par défaut
            }

            @Override
            public boolean isEnabled() {
                return statutCompteDTO.getStatutCompte(); // Utiliser le statut du DTO
            }
        };

        utilisateur.setStatutCompte(statutCompteDTO.getStatutCompte());

        Utilisateur activeEtDesactiveUtilisateur = utilisateurService.ActiveDesactive(utilisateur, id);
        if (activeEtDesactiveUtilisateur != null) {
            return ResponseEntity.ok(activeEtDesactiveUtilisateur);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }




}