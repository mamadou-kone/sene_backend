package com.sene.backend.entity.investissement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sene.backend.entity.paiement.PaiementInvestissement;
import com.sene.backend.entity.personne.Agriculteur;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Projet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String description;
    private Double montantNecessaire;
    private Double montantCollecte;
    private Boolean statut;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;  // Nouveau champ pour stocker l'image sous forme de bytes

    @ManyToOne
    private Agriculteur agriculteur;

    @JsonIgnore
    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL)
    private Set<Investissement> investments;

    @JsonIgnore
    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL)
    private Set<PaiementInvestissement> paiementInvestissement;
}
