package com.sene.backend.service.services;

import com.sene.backend.entity.investissement.Investissement;
import com.sene.backend.entity.investissement.Projet;
import com.sene.backend.repository.InvestissementRepository;
import com.sene.backend.repository.ProjetRepository; // Assurez-vous d'importer le ProjetRepository
import com.sene.backend.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvestissementService implements CrudService<Investissement, Long> {

    @Autowired
    private InvestissementRepository investissementRepository;

    @Autowired
    private ProjetRepository projetRepository; // Déclaration du ProjetRepository

    @Override
    public Investissement ajout(Investissement entity) {
        Long projetId = entity.getProjet().getId(); // Récupérer l'ID du projet
        Projet projet = projetRepository.findById(projetId) // Récupérer le projet depuis la base de données
                .orElseThrow(() -> new IllegalArgumentException("Le projet associé à l'investissement n'existe pas."));

        Double montantInvestissement = entity.getMontant();
        if (montantInvestissement == null) {
            throw new IllegalArgumentException("Le montant de l'investissement ne peut pas être null.");
        }

        Double montantCollecteActuel = projet.getMontantCollecte();
        if (montantCollecteActuel == null) {
            montantCollecteActuel = 0.0; // Valeur par défaut
        }

        Double montantNecessaire = projet.getMontantNecessaire();
        if (montantNecessaire == null) {
            throw new IllegalArgumentException("Le montant nécessaire du projet ne peut pas être null.");
        }

        // Vérification de la validité de l'investissement
        if (montantCollecteActuel + montantInvestissement <= montantNecessaire) {
            projet.setMontantCollecte(montantCollecteActuel + montantInvestissement);
            projetRepository.save(projet); // Sauvegarder les modifications du projet

            return investissementRepository.save(entity); // Sauvegarder l'investissement
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