package com.sene.backend.controleur;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sene.backend.entity.investissement.Projet;
import com.sene.backend.entity.personne.Agriculteur;
import com.sene.backend.entity.produit.Produit;
import com.sene.backend.security.configurationSecurity.CurrentUserService;
import com.sene.backend.security.dto.statuts.StatutProduitDTO;
import com.sene.backend.security.dto.statuts.StatutProjetDTO;
import com.sene.backend.service.services.AgriculteurService;
import com.sene.backend.service.services.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/produit")
public class ProduitController {

    @Autowired
    private ProduitService produitService;

    @Autowired
    private CurrentUserService currentUserService;

    @Autowired
    private AgriculteurService agriculteurService;

    // Ajouter un nouveau produit avec image
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Produit> ajouterProduit(
            @RequestPart("produit") String produitJson,
            @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {

        // Convertir le JSON en objet Produit
        ObjectMapper objectMapper = new ObjectMapper();
        Produit produit = objectMapper.readValue(produitJson, Produit.class);




            // Gérer l'image si elle est fournie
            if (image != null && !image.isEmpty()) {
                produit.setImage(image.getBytes());


            // Sauvegarder le produit avec l'agriculteur associé
            Produit nouveauProduit = produitService.ajout(produit);

            return ResponseEntity.status(HttpStatus.CREATED).body(nouveauProduit);
        } else {
            return ResponseEntity.badRequest().build(); // Gérer le cas où l'agriculteur n'est pas trouvé
        }
    }

    // Lister tous les produits
    @GetMapping
    public ResponseEntity<List<Produit>> listerProduits() {
        List<Produit> produits = produitService.liste();
        return ResponseEntity.ok(produits);
    }

    // Obtenir un produit par ID
    @GetMapping("/{id}")
    public ResponseEntity<Produit> obtenirProduitParId(@PathVariable Long id) {
        Optional<Produit> produit = produitService.trouverParId(id);
        return produit.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Mettre à jour un produit existant
    @PutMapping("/{id}")
    public ResponseEntity<Produit> miseAJour(@PathVariable Long id,
                                             @RequestParam("produit") String produitJson,
                                             @RequestParam(value = "image", required = false) MultipartFile imageFile) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Produit produit = objectMapper.readValue(produitJson, Produit.class);

            // Gérer l'image si elle est fournie
            if (imageFile != null && !imageFile.isEmpty()) {
                produit.setImage(imageFile.getBytes());
            }

            Produit updatedProduit = produitService.miseAJour(produit, id);
            if (updatedProduit != null) {
                return ResponseEntity.ok(updatedProduit);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Supprimer un produit par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerProduit(@PathVariable Long id) {
        produitService.supprimer(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> ActDesactCompte(@PathVariable Long id, @RequestBody StatutProduitDTO statutPrduitDTO) {
        // Récupérer le produit existant par son ID
        Optional<Produit> produitOpt = produitService.trouverParId(id);

        // Vérifier si le projet existe
        if (produitOpt.isPresent()) {
            Produit produit = produitOpt.get(); // Récupérer le produit
            produit.setStatut(statutPrduitDTO.getStatut()); // Mettre à jour le statut

            // Mettre à jour le projet via le service
            Produit activeEtDesactiveProduit = produitService.ActiveDesactive(produit, id);

            // Retourner une réponse avec le projet mis à jour
            return ResponseEntity.ok(activeEtDesactiveProduit);
        }

        // Si le projet n'existe pas, retourner une erreur 404
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    // Récupérer les produits par ID d'agriculteur
    @GetMapping("/agriculteur/{id}")
    public ResponseEntity<List<Produit>> listerProduitsParAgriculteur(@PathVariable Long id) {
        List<Produit> produits = produitService.trouverParAgriculteur(id);
        return ResponseEntity.ok(produits);
    }

}