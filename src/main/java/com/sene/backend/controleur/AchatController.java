package com.sene.backend.controleur;

import com.sene.backend.entity.achat.Achat;
import com.sene.backend.service.services.AchatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/achat")
public class AchatController {

    @Autowired
    private AchatService achatService;

    // Ajouter un nouvel achat
    @PostMapping
    public ResponseEntity<Achat> ajouterAchat(@RequestBody Achat achat) {
        Achat nouvelAchat = achatService.ajout(achat);
        return ResponseEntity.ok(nouvelAchat);
    }

    // Lister tous les achats
    @GetMapping
    public ResponseEntity<List<Achat>> listerAchats() {
        List<Achat> achats = achatService.liste();
        return ResponseEntity.ok(achats);
    }

    // Obtenir un achat par ID
    @GetMapping("/{id}")
    public ResponseEntity<Achat> obtenirAchatParId(@PathVariable Long id) {
        Optional<Achat> achat = achatService.trouverParId(id);
        return achat.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Mettre à jour un achat existant
    @PutMapping("/{id}")
    public ResponseEntity<Achat> mettreAJourAchat(@RequestBody Achat achat, @PathVariable Long id) {
        Achat achatMisAJour = achatService.miseAJour(achat, id);
        if (achatMisAJour != null) {
            return ResponseEntity.ok(achatMisAJour);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Supprimer un achat par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerAchat(@PathVariable Long id) {
        achatService.supprimer(id);
        return ResponseEntity.noContent().build();
    }
}
