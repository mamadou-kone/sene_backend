package com.sene.backend.service.services;

import com.sene.backend.entity.personne.Utilisateur;
import com.sene.backend.repository.UtilisateurRepository;
import com.sene.backend.service.ActiveDesactiveService;
import com.sene.backend.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserService implements ActiveDesactiveService<Utilisateur,Long> ,CrudService<Utilisateur,Long> {
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Override
    public Utilisateur ajout(Utilisateur entity) {
        return utilisateurRepository.save(entity);
    }

    @Override
    public List<Utilisateur> liste() {
        return utilisateurRepository.findAll();
    }

    @Override
    public Optional<Utilisateur> trouverParId(Long id) {
        return utilisateurRepository.findById(id);
    }

    @Override
    public Utilisateur miseAJour(Utilisateur entity, Long id) {
        Optional<Utilisateur> existingAdmin = utilisateurRepository.findById(id);
        if (existingAdmin.isPresent()) {
            Utilisateur updateUtilisateur = existingAdmin.get();
            updateUtilisateur.setNom(entity.getNom());
            updateUtilisateur.setPrenom(entity.getPrenom());
            updateUtilisateur.setEmail(entity.getEmail());
            updateUtilisateur.setTel(entity.getTel());
            updateUtilisateur.setAddress(entity.getAddress());
            updateUtilisateur.setStatutCompte(entity.getStatutCompte());
            updateUtilisateur.setImage(entity.getImage()); // Gérer l'image
            return utilisateurRepository.save(updateUtilisateur);
        } else {
            return null; // Si l'utilisateur n'existe pas
        }
    }

    @Override
    public void supprimer(Long aLong) {

    }

    @Override
    public Utilisateur ActiveDesactive(Utilisateur entity, Long id) {
        Optional<Utilisateur> existingUtilisateur = utilisateurRepository.findById(id);
        if (existingUtilisateur.isPresent()) {
            Utilisateur updateUtilisateur = existingUtilisateur.get();
            updateUtilisateur.setStatutCompte(entity.getStatutCompte());
            return utilisateurRepository.save(updateUtilisateur); // Utiliser updateUtilisateur
        } else {
            return null; // Si l'utilisateur n'existe pas
        }
    }



}
