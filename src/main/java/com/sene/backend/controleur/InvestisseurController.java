package com.sene.backend.controleur;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sene.backend.entity.personne.Investisseur;
import com.sene.backend.service.services.InvestisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/investisseur")
public class InvestisseurController {

    @Autowired
    private InvestisseurService investisseurService;

    // Ajouter un nouvel Investisseur
    @PostMapping
    public ResponseEntity<Investisseur> ajout(@RequestParam("investisseur") String investisseurJson,
                                              @RequestParam(value = "image", required = false) MultipartFile imageFile) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Investisseur investisseur = objectMapper.readValue(investisseurJson, Investisseur.class);

            // Gérer l'image si elle est fournie
            if (imageFile != null && !imageFile.isEmpty()) {
                investisseur.setImage(imageFile.getBytes());
            }

            Investisseur savedInvestisseur = investisseurService.ajout(investisseur);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedInvestisseur);
        } catch (IOException e) {
            e.printStackTrace(); // Log l'erreur
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            e.printStackTrace(); // Log l'erreur
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Liste des Investisseurs
    @GetMapping
    public ResponseEntity<List<Investisseur>> liste() {
        List<Investisseur> investisseurs = investisseurService.liste();
        return ResponseEntity.ok(investisseurs);
    }

    // Trouver un Investisseur par ID
    @GetMapping("/{id}")
    public ResponseEntity<Investisseur> trouverParId(@PathVariable Long id) {
        Optional<Investisseur> investisseurOpt = investisseurService.trouverParId(id);
        return investisseurOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Mise à jour d'un Investisseur
    @PutMapping("/{id}")
    public ResponseEntity<Investisseur> miseAJour(@PathVariable Long id, @RequestParam("investisseur") String investisseurJson,
                                                  @RequestParam(value = "image", required = false) MultipartFile imageFile) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Investisseur investisseur = objectMapper.readValue(investisseurJson, Investisseur.class);

            // Gérer l'image si elle est fournie
            if (imageFile != null && !imageFile.isEmpty()) {
                investisseur.setImage(imageFile.getBytes());
            }

            Investisseur updatedInvestisseur = investisseurService.miseAJour(investisseur, id);
            if (updatedInvestisseur != null) {
                return ResponseEntity.ok(updatedInvestisseur);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Suppression d'un Investisseur par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        investisseurService.supprimer(id);
        return ResponseEntity.noContent().build();
    }
}