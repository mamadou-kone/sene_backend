package com.sene.backend.controleur;

import com.sene.backend.entity.achat.PanierProduit;
import com.sene.backend.repository.PanierProduitRepository;
import com.sene.backend.service.services.PanierProduitService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/panier-produit")
public class PanierProduitController {

    @Autowired
    private PanierProduitService panierProduitService;
    @Autowired
    private PanierProduitRepository panierProduitRepository;

    @PostConstruct
    public void initializeAcheterBoolean() {
        // Mettre à jour acheterBoolean pour toutes les lignes existantes
        panierProduitRepository.setAcheterBooleanToFalseForExistingRecords();
    }

    // Ajouter un produit dans le panier
    @PostMapping
    public ResponseEntity<PanierProduit> ajouterPanierProduit(@RequestBody PanierProduit panierProduit) {
        PanierProduit nouveauPanierProduit = panierProduitService.ajout(panierProduit);
        return ResponseEntity.ok(nouveauPanierProduit);
    }

    // Lister tous les produits dans les paniers
    @GetMapping
    public ResponseEntity<List<PanierProduit>> listerPanierProduits() {
        List<PanierProduit> panierProduits = panierProduitService.liste();
        return ResponseEntity.ok(panierProduits);
    }

    // Obtenir un produit dans le panier par ID
    @GetMapping("/{id}")
    public ResponseEntity<PanierProduit> obtenirPanierProduitParId(@PathVariable Long id) {
        Optional<PanierProduit> panierProduit = panierProduitService.trouverParId(id);
        return panierProduit.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Mettre à jour un produit dans le panier
    @PutMapping("/{id}")
    public ResponseEntity<PanierProduit> mettreAJourPanierProduit(@RequestBody PanierProduit panierProduit, @PathVariable Long id) {
        PanierProduit panierProduitMisAJour = panierProduitService.miseAJour(panierProduit, id);
        if (panierProduitMisAJour != null) {
            return ResponseEntity.ok(panierProduitMisAJour);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Supprimer un produit dans le panier par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerPanierProduit(@PathVariable Long id) {
        panierProduitService.supprimer(id);
        return ResponseEntity.noContent().build();
    }

    // Mettre à jour la quantité d'un produit dans le panier
    @PatchMapping("/update-quantite")
    public ResponseEntity<String> updateQuantitePanierProduit(
            @RequestParam Long panierProduitId,
            @RequestParam int nouvelleQuantite) {

        try {
            panierProduitService.updateQuantite(panierProduitId, nouvelleQuantite);
            return ResponseEntity.ok("Quantité mise à jour avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

}
