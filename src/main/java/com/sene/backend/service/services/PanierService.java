package com.sene.backend.service.services;

import com.sene.backend.entity.achat.Panier;
import com.sene.backend.repository.PanierRepository;
import com.sene.backend.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PanierService implements CrudService<Panier, Long> {

    @Autowired
    private PanierRepository panierRepository;

    @Override
    public Panier ajout(Panier entity) {
        return panierRepository.save(entity);
    }

    @Override
    public List<Panier> liste() {
        return panierRepository.findAll();
    }

    @Override
    public Optional<Panier> trouverParId(Long id) {
        return panierRepository.findById(id);
    }

    @Override
    public Panier miseAJour(Panier entity, Long id) {
        Optional<Panier> existingPanier = panierRepository.findById(id);
        if (existingPanier.isPresent()) {
            Panier updatedPanier = existingPanier.get();
            updatedPanier.setPanierProduits(entity.getPanierProduits());
            return panierRepository.save(updatedPanier);
        } else {
            return null;
        }
    }

    @Override
    public void supprimer(Long id) {
        panierRepository.deleteById(id);
    }
}
