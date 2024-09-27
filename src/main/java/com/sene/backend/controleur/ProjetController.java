package com.sene.backend.controleur;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sene.backend.entity.investissement.Projet;
import com.sene.backend.entity.personne.Agriculteur;
import com.sene.backend.security.configurationSecurity.CurrentUserService;
import com.sene.backend.security.dto.statuts.StatutProjetDTO;
import com.sene.backend.service.services.ProjetService;
import com.sene.backend.service.services.AgriculteurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projet")
public class ProjetController {

    @Autowired
    private ProjetService projetService;

    @Autowired
    private CurrentUserService currentUserService;

    @Autowired
    private AgriculteurService agriculteurService;

    // Ajouter un nouveau projet avec image
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Projet> ajouterProjet(
            @RequestPart("projet") String projetJson,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Projet projet = objectMapper.readValue(projetJson, Projet.class);

            // Récupérer l'ID de l'agriculteur connecté
            Long id = currentUserService.getCurrentUtilisateurId();
            Optional<Agriculteur> agriculteur = agriculteurService.trouverParId(id);

            if (agriculteur.isPresent()) {
                projet.setAgriculteur(agriculteur.get());

                if (image != null && !image.isEmpty()) {
                    projet.setImage(image.getBytes());
                }

                Projet nouveauProjet = projetService.ajout(projet);
                return ResponseEntity.status(HttpStatus.CREATED).body(nouveauProjet);
            } else {
                return ResponseEntity.badRequest().build(); // Agriculteur non trouvé
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Lister tous les projets
    @GetMapping
    public ResponseEntity<List<Projet>> listerProjets() {
        List<Projet> projets = projetService.liste();
        return ResponseEntity.ok(projets);
    }

    // Obtenir un projet par ID
    @GetMapping("/{id}")
    public ResponseEntity<Projet> obtenirProjetParId(@PathVariable Long id) {
        Optional<Projet> projet = projetService.trouverParId(id);
        return projet.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Mettre à jour un projet existant
    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<Projet> mettreAJourProjet(
            @PathVariable Long id,
            @RequestPart("projet") String projetJson,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Projet projet = objectMapper.readValue(projetJson, Projet.class);
            Long utilisateurId = currentUserService.getCurrentUtilisateurId();
            Optional<Agriculteur> agriculteur = agriculteurService.trouverParId(utilisateurId);

            if (agriculteur.isPresent()) {
                projet.setAgriculteur(agriculteur.get());

                if (image != null && !image.isEmpty()) {
                    projet.setImage(image.getBytes());
                }

                Projet projetMisAJour = projetService.miseAJour(projet, id);
                return projetMisAJour != null ? ResponseEntity.ok(projetMisAJour) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } else {
                return ResponseEntity.badRequest().build(); // Agriculteur non trouvé
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Supprimer un projet par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerProjet(@PathVariable Long id) {
        projetService.supprimer(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> ActDesactCompte(@PathVariable Long id, @RequestBody StatutProjetDTO statutProjetDTO) {
        // Récupérer le projet existant par son ID
        Optional<Projet> projetOpt = projetService.trouverParId(id);

        // Vérifier si le projet existe
        if (projetOpt.isPresent()) {
            Projet projet = projetOpt.get(); // Récupérer le projet
            projet.setStatut(statutProjetDTO.getStatut()); // Mettre à jour le statut

            // Mettre à jour le projet via le service
            Projet activeEtDesactiveProjet = projetService.ActiveDesactive(projet, id);

            // Retourner une réponse avec le projet mis à jour
            return ResponseEntity.ok(activeEtDesactiveProjet);
        }

        // Si le projet n'existe pas, retourner une erreur 404
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }



}