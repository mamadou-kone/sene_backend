package com.sene.backend.controleur;

import com.sene.backend.entity.paiement.PaiementInvestissement;
import com.sene.backend.service.services.PaiementInvestissementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pavementInvestissement")
public class PaiementInvestissementController {

    @Autowired
    private PaiementInvestissementService paiementInvestissementService;

    // Ajouter un nouveau paiement pour un investissement
    @PostMapping
    public ResponseEntity<PaiementInvestissement> ajouterPaiementInvestissement(@RequestBody PaiementInvestissement paiementInvestissement) {
        PaiementInvestissement nouveauPaiement = paiementInvestissementService.ajout(paiementInvestissement);
        return ResponseEntity.ok(nouveauPaiement);
    }

    // Lister tous les paiements d'investissement
    @GetMapping
    public ResponseEntity<List<PaiementInvestissement>> listerPaiementsInvestissement() {
        List<PaiementInvestissement> paiements = paiementInvestissementService.liste();
        return ResponseEntity.ok(paiements);
    }

    // Obtenir un paiement d'investissement par ID
    @GetMapping("/{id}")
    public ResponseEntity<PaiementInvestissement> obtenirPaiementInvestissementParId(@PathVariable Long id) {
        Optional<PaiementInvestissement> paiementInvestissement = paiementInvestissementService.trouverParId(id);
        return paiementInvestissement.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Mettre à jour un paiement d'investissement
    @PutMapping("/{id}")
    public ResponseEntity<PaiementInvestissement> mettreAJourPaiementInvestissement(@RequestBody PaiementInvestissement paiementInvestissement, @PathVariable Long id) {
        PaiementInvestissement paiementMisAJour = paiementInvestissementService.miseAJour(paiementInvestissement, id);
        if (paiementMisAJour != null) {
            return ResponseEntity.ok(paiementMisAJour);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Supprimer un paiement d'investissement par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerPaiementInvestissement(@PathVariable Long id) {
        paiementInvestissementService.supprimer(id);
        return ResponseEntity.noContent().build();
    }
}
