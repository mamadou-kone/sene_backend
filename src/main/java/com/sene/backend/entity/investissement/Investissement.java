package com.sene.backend.entity.investissement;

import com.sene.backend.entity.personne.Investisseur;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class Investissement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double montant;
    private LocalDate dateInvestissement=LocalDate.now();

    @ManyToOne
    private Investisseur investisseur;

    @ManyToOne
    private Projet projet;




}
