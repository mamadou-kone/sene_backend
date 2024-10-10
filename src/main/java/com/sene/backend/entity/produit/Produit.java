package com.sene.backend.entity.produit;

import com.sene.backend.entity.personne.Agriculteur;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String description;
    private Double prix;
    private Integer quantite;
    private Boolean statut =true;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;  // Champ pour stocker l'image sous forme de bytes

    @ManyToOne
    @JoinColumn(name = "agriculteur_id", nullable = false)
    private Agriculteur agriculteur;  // Référence à l'agriculteur
}