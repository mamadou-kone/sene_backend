package com.sene.backend.service.services;

import com.sene.backend.entity.achat.Panier; // Ajoutez cet import
import com.sene.backend.entity.investissement.Investissement;
import com.sene.backend.entity.personne.Client;
import com.sene.backend.entity.personne.Investisseur;
import com.sene.backend.entity.personne.Role;
import com.sene.backend.repository.ClientRepository;
import com.sene.backend.repository.PanierRepository; // Ajoutez cet import
import com.sene.backend.repository.RoleRepository;
import com.sene.backend.security.configurationSecurity.ConfigurationCryptageMotDePasse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ConfigurationCryptageMotDePasse configurationCryptageMotDePasse;

    @Autowired
    private PanierRepository panierRepository; // Ajoutez ceci pour le panier

    // Ajouter un nouveau Client
    public Client ajout(Client client) {
        // Récupérer le rôle "Client"
        Role roleClient = roleRepository.findByNom("Client");

        if (roleClient == null) {
            // Si le rôle n'existe pas, le créer
            roleClient = new Role();
            roleClient.setNom("Client");
            roleRepository.save(roleClient);
        }

        // Assigner le rôle au client
        client.setRole(roleClient);
        client.setPassword(configurationCryptageMotDePasse.passwordEncoder().encode(client.getPassword()));

        // Enregistrer le client dans la base de données
        Client savedClient = clientRepository.save(client);

        // Créer un panier pour le client
        Panier panier = new Panier();
        panier.setClient(savedClient); // Associer le panier au client

        // Enregistrer le panier dans la base de données
        panierRepository.save(panier);

        return savedClient;
    }

    // Lister tous les Clients
    public List<Client> liste() {
        return clientRepository.findAll();
    }

    // Trouver un Client par ID
    public Optional<Client> trouverParId(Long id) {
        return clientRepository.findById(id);
    }

    public Client miseAJour(Client entity, Long id) {
        Optional<Client> existingClient = clientRepository.findById(id);
        if (existingClient.isPresent()) {
            Client updatedClient = existingClient.get();
            updatedClient.setNom(entity.getNom());
            updatedClient.setPrenom(entity.getPrenom());
            updatedClient.setEmail(entity.getEmail());
            updatedClient.setTel(entity.getTel());
            updatedClient.setAddress(entity.getAddress());
            updatedClient.setStatutCompte(entity.getStatutCompte());
            updatedClient.setImage(entity.getImage()); // Gérer l'image
            return clientRepository.save(updatedClient);
        } else {
            return null; // Si le client n'existe pas
        }
    }
    // Mise à jour d'un Client

    // Supprimer un Client par ID
    public void supprimer(Long id) {
        clientRepository.deleteById(id);
    }
}
