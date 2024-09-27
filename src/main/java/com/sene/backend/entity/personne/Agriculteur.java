package com.sene.backend.entity.personne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sene.backend.entity.produit.Produit;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agriculteur extends Utilisateur {

    @OneToMany(mappedBy = "agriculteur", cascade = CascadeType.ALL)
    @JsonIgnore // Pour éviter la récursion infinie lors de la sérialisation
    private Set<Produit> produits;
}