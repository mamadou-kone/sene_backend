package com.sene.backend.entity.personne;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Investisseur extends Utilisateur {

    public Investisseur() {
        // Constructeur sans argument nécessaire pour la désérialisation
    }

    public Investisseur(Long id) {
        this.setId(id);  // Assurez-vous que 'id' est une propriété de la classe 'Utilisateur'
    }

}


