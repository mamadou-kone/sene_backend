package com.sene.backend.controleur;

import com.sene.backend.entity.achat.Panier;
import com.sene.backend.entity.achat.PanierProduit; // Ajoutez ceci
import com.sene.backend.service.services.PanierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/panier")
public class PanierController {

    @Autowired
    private PanierService panierService;

    @PostMapping
    public ResponseEntity<Panier> ajouterPanier(@RequestBody Panier panier) {
        Panier nouveauPanier = panierService.ajout(panier);
        return ResponseEntity.ok(nouveauPanier);
    }

    @GetMapping
    public ResponseEntity<List<Panier>> listerPaniers() {
        List<Panier> paniers = panierService.liste();
        return ResponseEntity.ok(paniers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Panier> obtenirPanierParId(@PathVariable Long id) {
        Optional<Panier> panier = panierService.trouverParId(id);
        return panier.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Panier> mettreAJourPanier(@RequestBody Panier panier, @PathVariable Long id) {
        Panier panierMisAJour = panierService.miseAJour(panier, id);
        if (panierMisAJour != null) {
            return ResponseEntity.ok(panierMisAJour);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerPanier(@PathVariable Long id) {
        panierService.supprimer(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint pour obtenir les produits du panier par ID
    @GetMapping("/{id}/produits")
    public ResponseEntity<Set<PanierProduit>> obtenirProduitsDuPanier(@PathVariable Long id) {
        Set<PanierProduit> produits = panierService.getProduitsDuPanier(id);

        if (produits.isEmpty()) {
            return ResponseEntity.ok(produits); // Retourne un ensemble vide avec 200 OK
        }

        return ResponseEntity.ok(produits); // Retourne les produits avec 200 OK
    }

}
