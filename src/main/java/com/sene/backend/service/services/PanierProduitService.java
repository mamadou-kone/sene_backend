package com.sene.backend.service.services;

import com.sene.backend.entity.achat.PanierProduit;
import com.sene.backend.repository.PanierProduitRepository;
import com.sene.backend.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PanierProduitService implements CrudService<PanierProduit, Long> {

    @Autowired
    private PanierProduitRepository panierProduitRepository;

    @Override
    public PanierProduit ajout(PanierProduit entity) {
        return panierProduitRepository.save(entity);
    }

    @Override
    public List<PanierProduit> liste() {
        return panierProduitRepository.findAll();
    }

    @Override
    public Optional<PanierProduit> trouverParId(Long id) {
        return panierProduitRepository.findById(id);
    }

    @Override
    public PanierProduit miseAJour(PanierProduit entity, Long id) {
        Optional<PanierProduit> existingPanierProduit = panierProduitRepository.findById(id);
        if (existingPanierProduit.isPresent()) {
            PanierProduit updatedPanierProduit = existingPanierProduit.get();
            updatedPanierProduit.setPanier(entity.getPanier());
            updatedPanierProduit.setProduit(entity.getProduit());
            updatedPanierProduit.setQuantite(entity.getQuantite());
            return panierProduitRepository.save(updatedPanierProduit);
        } else {
            return null;
        }
    }

    @Override
    public void supprimer(Long id) {
        panierProduitRepository.deleteById(id);
    }



    public PanierProduit updateQuantite(Long panierProduitId, int nouvelleQuantite) {
        Optional<PanierProduit> panierProduitOptional = panierProduitRepository.findById(panierProduitId);

        if (!panierProduitOptional.isPresent()) {
            throw new RuntimeException("Produit dans le panier non trouvé");
        }

        PanierProduit panierProduit = panierProduitOptional.get();
        panierProduit.updateQuantite(nouvelleQuantite);
        return panierProduitRepository.save(panierProduit);
    }



}
