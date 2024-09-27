package com.sene.backend.service.services;

import com.sene.backend.entity.investissement.Projet;
import com.sene.backend.entity.personne.Utilisateur;
import com.sene.backend.repository.ProjetRepository;
import com.sene.backend.service.ActiveDesactiveService;
import com.sene.backend.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjetService implements CrudService<Projet, Long>, ActiveDesactiveService<Projet, Long> {

    @Autowired
    private ProjetRepository projetRepository;

    // Ajouter un nouveau Projet
    public Projet ajout(Projet projet) {
        return projetRepository.save(projet);
    }

    // Lister tous les Projets
    public List<Projet> liste() {
        return projetRepository.findAll();
    }

    // Trouver un Projet par ID
    public Optional<Projet> trouverParId(Long id) {
        return projetRepository.findById(id);
    }

    // Mise à jour d'un Projet
    public Projet miseAJour(Projet entity, Long id) {
        Optional<Projet> existingProjet = projetRepository.findById(id);
        if (existingProjet.isPresent()) {
            Projet updatedProjet = existingProjet.get();
            updatedProjet.setTitre(entity.getTitre());
            updatedProjet.setDescription(entity.getDescription());
            updatedProjet.setMontantNecessaire(entity.getMontantNecessaire());
            updatedProjet.setMontantCollecte(entity.getMontantCollecte());
            updatedProjet.setImage(entity.getImage()); // Gérer l'image
            return projetRepository.save(updatedProjet);
        } else {
            return null; // Si le projet n'existe pas
        }
    }

    // Supprimer un Projet par ID
    public void supprimer(Long id) {
        projetRepository.deleteById(id);
    }

    @Override
    public Projet ActiveDesactive(Projet entity, Long id) {
        Optional<Projet> existingProjet = projetRepository.findById(id);
        if (existingProjet.isPresent()) {
            Projet  updateProjet = existingProjet.get();
            updateProjet.setStatut(entity.getStatut());
            return projetRepository.save(updateProjet); // Utiliser updateprojet
        } else {
            return null; // Si le projet n'existe pas
        }
    }
}