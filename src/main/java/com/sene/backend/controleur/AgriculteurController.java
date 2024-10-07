package com.sene.backend.controleur;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sene.backend.entity.personne.Agriculteur;
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
@RequestMapping("/api/agriculteur")
public class AgriculteurController {

    @Autowired
    private AgriculteurService agriculteurService;

    // Ajouter un nouvel Agriculteur
    @PostMapping
    public ResponseEntity<Agriculteur> ajout(@RequestParam("agriculteur") String agriculteurJson,
                                             @RequestParam(value = "image", required = false) MultipartFile imageFile) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Agriculteur agriculteur = objectMapper.readValue(agriculteurJson, Agriculteur.class);

            // Gérer l'image si elle est fournie
            if (imageFile != null && !imageFile.isEmpty()) {
                agriculteur.setImage(imageFile.getBytes());
            }

            Agriculteur savedAgriculteur = agriculteurService.ajout(agriculteur);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAgriculteur);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Liste des Agriculteurs
    @GetMapping
    public ResponseEntity<List<Agriculteur>> liste() {
        List<Agriculteur> agriculteurs = agriculteurService.liste();
        return ResponseEntity.ok(agriculteurs);
    }

    // Trouver un Agriculteur par ID
    @GetMapping("/{id}")
    public ResponseEntity<Agriculteur> trouverParId(@PathVariable Long id) {
        Optional<Agriculteur> agriculteurOpt = agriculteurService.trouverParId(id);
        return agriculteurOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Mise à jour d'un Agriculteur
    @PutMapping("/{id}")
    public ResponseEntity<Agriculteur> miseAJour(@PathVariable Long id,
                                                 @RequestParam("agriculteur") String agriculteurJson,
                                                 @RequestParam(value = "image", required = false) MultipartFile imageFile) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Agriculteur agriculteur = objectMapper.readValue(agriculteurJson, Agriculteur.class);

            // Si une nouvelle image est fournie, la mettre à jour
            if (imageFile != null && !imageFile.isEmpty()) {
                agriculteur.setImage(imageFile.getBytes());
            }

            Agriculteur updatedAgriculteur = agriculteurService.miseAJour(agriculteur, id);
            if (updatedAgriculteur != null) {
                return ResponseEntity.ok(updatedAgriculteur);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Suppression d'un Agriculteur par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        agriculteurService.supprimer(id);
        return ResponseEntity.noContent().build();
    }
}