package com.sene.backend.service.services;

import com.sene.backend.entity.personne.Agriculteur;
import com.sene.backend.entity.personne.Role;
import com.sene.backend.repository.AgriculteurRepository;
import com.sene.backend.repository.RoleRepository;
import com.sene.backend.security.configurationSecurity.ConfigurationCryptageMotDePasse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgriculteurService {
    ConfigurationCryptageMotDePasse passwordEncoder = new ConfigurationCryptageMotDePasse();

    @Autowired
    private AgriculteurRepository agriculteurRepository;

    @Autowired
    private RoleRepository roleRepository;


    // Ajouter un nouvel Agriculteur
    public Agriculteur ajout(Agriculteur agriculteur) {
        // Récupérer le rôle "Agriculteur"
        Role roleAgriculteur = roleRepository.findByNom("Agriculteur");
        agriculteur.setPassword(passwordEncoder.passwordEncoder().encode(agriculteur.getPassword()));

        if (roleAgriculteur == null) {
            // Si le rôle n'existe pas, le créer
            roleAgriculteur = new Role();
            roleAgriculteur.setNom("Agriculteur");
            roleRepository.save(roleAgriculteur);
        }

        // Assigner le rôle à l'agriculteur
        agriculteur.setRole(roleAgriculteur);
        return agriculteurRepository.save(agriculteur);
    }

    // Lister tous les Agriculteurs
    public List<Agriculteur> liste() {
        return agriculteurRepository.findAll();
    }

    // Trouver un Agriculteur par ID
    public Optional<Agriculteur> trouverParId(Long id) {
        return agriculteurRepository.findById(id);
    }

    // Mise à jour d'un Agriculteur
    public Agriculteur miseAJour(Agriculteur entity, Long id) {
        Optional<Agriculteur> existingAgriculteur = agriculteurRepository.findById(id);
        if (existingAgriculteur.isPresent()) {
            Agriculteur updatedAgriculteur = existingAgriculteur.get();
            updatedAgriculteur.setNom(entity.getNom());
            updatedAgriculteur.setPrenom(entity.getPrenom());
            updatedAgriculteur.setEmail(entity.getEmail());
            updatedAgriculteur.setTel(entity.getTel());
            updatedAgriculteur.setAddress(entity.getAddress());
            updatedAgriculteur.setStatutCompte(entity.getStatutCompte());
            updatedAgriculteur.setImage(entity.getImage()); // Gérer l'image
            return agriculteurRepository.save(updatedAgriculteur);
        } else {
            return null; // Si l'agriculteur n'existe pas
        }
    }

    // Supprimer un Agriculteur par ID
    public void supprimer(Long id) {
        agriculteurRepository.deleteById(id);
    }
}