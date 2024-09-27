package com.sene.backend.service.services;

import com.sene.backend.entity.personne.Investisseur;
import com.sene.backend.entity.personne.Role;
import com.sene.backend.repository.InvestisseurRepository;
import com.sene.backend.repository.RoleRepository;
import com.sene.backend.security.configurationSecurity.ConfigurationCryptageMotDePasse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvestisseurService {

    @Autowired
    private InvestisseurRepository investisseurRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
     ConfigurationCryptageMotDePasse configurationCryptageMotDePasse;

    // Ajouter un nouvel Investisseur
    public Investisseur ajout(Investisseur investisseur) {
        // Récupérer le rôle "Investisseur"
        Role roleInvestisseur = roleRepository.findByNom("Investisseur");

        if (roleInvestisseur == null) {
            // Si le rôle n'existe pas, le créer
            roleInvestisseur = new Role();
            roleInvestisseur.setNom("Investisseur");
            roleRepository.save(roleInvestisseur);
        }

        // Assigner le rôle à l'investisseur
        investisseur.setRole(roleInvestisseur);
        investisseur.setPassword(configurationCryptageMotDePasse.passwordEncoder().encode(investisseur.getPassword()));
        return investisseurRepository.save(investisseur);
    }

    // Lister tous les Investisseurs
    public List<Investisseur> liste() {
        return investisseurRepository.findAll();
    }

    // Trouver un Investisseur par ID
    public Optional<Investisseur> trouverParId(Long id) {
        return investisseurRepository.findById(id);
    }

    // Mise à jour d'un Investisseur
    public Investisseur miseAJour(Investisseur entity, Long id) {
        Optional<Investisseur> existingInvestisseur = investisseurRepository.findById(id);
        if (existingInvestisseur.isPresent()) {
            Investisseur updatedInvestisseur = existingInvestisseur.get();
            updatedInvestisseur.setNom(entity.getNom());
            updatedInvestisseur.setPrenom(entity.getPrenom());
            updatedInvestisseur.setEmail(entity.getEmail());
            updatedInvestisseur.setTel(entity.getTel());
            updatedInvestisseur.setAddress(entity.getAddress());
            updatedInvestisseur.setStatutCompte(entity.getStatutCompte());
            updatedInvestisseur.setImage(entity.getImage()); // Gérer l'image
            return investisseurRepository.save(updatedInvestisseur);
        } else {
            return null; // Si l'investisseur n'existe pas
        }
    }

    // Supprimer un Investisseur par ID
    public void supprimer(Long id) {
        investisseurRepository.deleteById(id);
    }
}