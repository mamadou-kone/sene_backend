package com.sene.backend.entity.achat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sene.backend.entity.personne.Client;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Panier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "panier", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PanierProduit> panierProduits;

    @ManyToOne
    @JoinColumn(unique = true)
    private Client client;
}
