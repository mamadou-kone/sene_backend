package com.sene.backend.controleur;

import com.sene.backend.entity.paiement.PaiementAchat;
import com.sene.backend.service.services.PaiementAchatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/paiementAchat")
public class PaiementAchatController {

    @Autowired
    private PaiementAchatService paiementAchatService;

    // Ajouter un nouveau paiement pour un achat
    @PostMapping
    public ResponseEntity<PaiementAchat> ajouterPaiementAchat(@RequestBody PaiementAchat paiementAchat) {
        PaiementAchat nouveauPaiement = paiementAchatService.ajout(paiementAchat);
        return ResponseEntity.ok(nouveauPaiement);
    }

    // Lister tous les paiements d'achat
    @GetMapping
    public ResponseEntity<List<PaiementAchat>> listerPaiementsAchat() {
        List<PaiementAchat> paiements = paiementAchatService.liste();
        return ResponseEntity.ok(paiements);
    }

    // Obtenir un paiement d'achat par ID
    @GetMapping("/{id}")
    public ResponseEntity<PaiementAchat> obtenirPaiementAchatParId(@PathVariable Long id) {
        Optional<PaiementAchat> paiementAchat = paiementAchatService.trouverParId(id);
        return paiementAchat.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Mettre à jour un paiement d'achat
    @PutMapping("/{id}")
    public ResponseEntity<PaiementAchat> mettreAJourPaiementAchat(@RequestBody PaiementAchat paiementAchat, @PathVariable Long id) {
        PaiementAchat paiementMisAJour = paiementAchatService.miseAJour(paiementAchat, id);
        if (paiementMisAJour != null) {
            return ResponseEntity.ok(paiementMisAJour);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Supprimer un paiement d'achat par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerPaiementAchat(@PathVariable Long id) {
        paiementAchatService.supprimer(id);
        return ResponseEntity.noContent().build();
    }
}
