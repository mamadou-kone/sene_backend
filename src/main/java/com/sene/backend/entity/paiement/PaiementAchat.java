package com.sene.backend.entity.paiement;

import com.sene.backend.entity.achat.Achat;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaiementAchat extends Paiement {

    @ManyToOne
    private Achat achat;

}
