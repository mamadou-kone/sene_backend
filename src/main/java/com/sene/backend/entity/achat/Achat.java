package com.sene.backend.entity.achat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sene.backend.entity.paiement.PaiementAchat;
import com.sene.backend.entity.personne.Client;
import com.sene.backend.entity.produit.Produit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Achat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateAchat = LocalDate.now(); // Date de l'achat
    private Double montant; // Montant total de l'achat

    @ManyToOne
    private Panier panier; // Référence au panier associé

    @ManyToOne
    private Produit produit; // Référence au produit acheté

    @JsonIgnore
    @OneToMany(mappedBy = "achat", cascade = CascadeType.ALL)
    private Set<PaiementAchat> paiementAchat; // Paiements associés à cet achat
}
