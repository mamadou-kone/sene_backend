package com.sene.backend.entity.paiement;

import com.sene.backend.entity.investissement.Projet;
import com.sene.backend.entity.personne.Investisseur;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaiementInvestissement extends Paiement{
    @ManyToOne
    private Projet projet;

}
