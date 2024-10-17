package com.sene.backend.entity.achat;

import com.sene.backend.entity.produit.Produit;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PanierProduit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "panier_id")
    private Panier panier;

    @ManyToOne
    @JoinColumn(name = "produit_id")
    private Produit produit;

    private Integer quantite;

    // Méthode pour mettre à jour la quantité
    public void updateQuantite(int nouvelleQuantite) {
        this.quantite = nouvelleQuantite;
    }
}
