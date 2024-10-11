package com.sene.backend.service.services;

import com.sene.backend.entity.produit.Produit;
import com.sene.backend.repository.ProduitRepository;
import com.sene.backend.service.ActiveDesactiveService;
import com.sene.backend.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProduitService implements CrudService<Produit, Long>, ActiveDesactiveService<Produit,Long> {

    @Autowired
    private ProduitRepository produitRepository;

    @Override
    public Produit ajout(Produit entity) {
        return produitRepository.save(entity);
    }

    @Override
    public List<Produit> liste() {
        return produitRepository.findAll();
    }

    @Override
    public Optional<Produit> trouverParId(Long id) {
        return produitRepository.findById(id);
    }

    @Override
    public Produit miseAJour(Produit entity, Long id) {
        return produitRepository.findById(id)
                .map(existingProduit -> {
                    existingProduit.setNom(entity.getNom());
                    existingProduit.setDescription(entity.getDescription());
                    existingProduit.setPrix(entity.getPrix());
                    existingProduit.setQuantite(entity.getQuantite());
                    return produitRepository.save(existingProduit);
                })
                .orElse(null);
    }

    @Override
    public void supprimer(Long id) {
        produitRepository.deleteById(id);
    }

    @Override
    public Produit ActiveDesactive(Produit entity, Long id) {
        Optional<Produit> existingProduit = produitRepository.findById(id);
        if (existingProduit.isPresent()) {
            Produit  updateProduit = existingProduit.get();
            updateProduit.setStatut(entity.getStatut());
            return produitRepository.save(updateProduit); // Utiliser
        } else {
            return null; // Si le produit n'existe pas
        }
    }

}