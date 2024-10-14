package com.sene.backend.service.services;

import com.sene.backend.entity.investissement.Investissement;
import com.sene.backend.entity.investissement.Projet;
import com.sene.backend.repository.InvestissementRepository;
import com.sene.backend.repository.ProjetRepository;
import com.sene.backend.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvestissementService implements CrudService<Investissement, Long> {

    private final InvestissementRepository investissementRepository;
    private final ProjetRepository projetRepository;

    @Autowired
    public InvestissementService(InvestissementRepository investissementRepository, ProjetRepository projetRepository) {
        this.investissementRepository = investissementRepository;
        this.projetRepository = projetRepository;
    }

    @Override
    public Investissement ajout(Investissement entity) {
        Long projetId = entity.getProjet().getId();
        Projet projet = projetRepository.findById(projetId)
                .orElseThrow(() -> new IllegalArgumentException("Le projet associé à l'investissement n'existe pas."));

        Double montantInvestissement = entity.getMontant();
        if (montantInvestissement == null) {
            throw new IllegalArgumentException("Le montant de l'investissement ne peut pas être null.");
        }

        Double montantCollecteActuel = projet.getMontantCollecte();
        if (montantCollecteActuel == null) {
            montantCollecteActuel = 0.0;
        }

        Double montantNecessaire = projet.getMontantNecessaire();
        if (montantNecessaire == null) {
            throw new IllegalArgumentException("Le montant nécessaire du projet ne peut pas être null.");
        }

        if (montantCollecteActuel + montantInvestissement <= montantNecessaire) {
            projet.setMontantCollecte(montantCollecteActuel + montantInvestissement);
            projetRepository.save(projet);

            return investissementRepository.save(entity);
        } else {
            throw new IllegalArgumentException("Le montant de l'investissement dépasse le montant nécessaire du projet.");
        }
    }

    @Override
    public List<Investissement> liste() {
        return investissementRepository.findAll();
    }

    @Override
    public Optional<Investissement> trouverParId(Long id) {
        return investissementRepository.findById(id);
    }

    @Override
    public Investissement miseAJour(Investissement entity, Long id) {
        Optional<Investissement> existingInvestissement = investissementRepository.findById(id);
        if (existingInvestissement.isPresent()) {
            Investissement updatedInvestissement = existingInvestissement.get();
            updatedInvestissement.setMontant(entity.getMontant());
            updatedInvestissement.setProjet(entity.getProjet());
            updatedInvestissement.setInvestisseur(entity.getInvestisseur());
            return investissementRepository.save(updatedInvestissement);
        } else {
            throw new IllegalArgumentException("L'investissement à mettre à jour n'existe pas.");
        }
    }

    @Override
    public void supprimer(Long id) {
        if (!investissementRepository.existsById(id)) {
            throw new IllegalArgumentException("L'investissement à supprimer n'existe pas.");
        }
        investissementRepository.deleteById(id);
    }
}
