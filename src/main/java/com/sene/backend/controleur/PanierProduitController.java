package com.sene.backend.controleur;

import com.sene.backend.entity.achat.PanierProduit;
import com.sene.backend.service.services.PanierProduitService;
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
}
