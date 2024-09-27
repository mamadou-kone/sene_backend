package com.sene.backend.entity.achat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sene.backend.entity.paiement.PaiementAchat;
import com.sene.backend.entity.personne.Client;
import com.sene.backend.entity.produit.Produit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Achat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateAchat;
    private Double montant;

    @ManyToOne
    private Client client;
    @ManyToOne
    private Produit produit;

    @JsonIgnore
    @OneToMany(mappedBy = "achat",cascade = CascadeType.ALL)
    private Set<PaiementAchat> paiementAchat;

}
