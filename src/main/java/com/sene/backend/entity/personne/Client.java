package com.sene.backend.entity.personne;

import com.sene.backend.entity.achat.Panier;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client extends Utilisateur {

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private Panier panier; // Un seul panier pour chaque client
}
