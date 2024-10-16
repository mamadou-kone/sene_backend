package com.sene.backend.entity.personne;

import com.sene.backend.entity.achat.Panier;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client extends Utilisateur {

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private Set<Panier> panier; // Un client peut avoir plusieurs paniers si nécessaire
}
