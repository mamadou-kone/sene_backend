package com.sene.backend.service.services;

import com.sene.backend.entity.achat.Panier;
import com.sene.backend.entity.achat.PanierProduit; // Ajoutez ceci
import com.sene.backend.repository.PanierProduitRepository;
import com.sene.backend.repository.PanierRepository;
import com.sene.backend.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PanierService implements CrudService<Panier, Long> {

    @Autowired
    private PanierRepository panierRepository;
    @Autowired
    private PanierProduitRepository panierProduitRepository;

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

    public Set<PanierProduit> getProduitsDuPanier(Long panierId) {
        return panierProduitRepository.findByPanierId(panierId);
    }

    // Méthode pour obtenir les produits d'un panier à partir de l'ID du client
    public Set<PanierProduit> getProduitsParClientId(Long clientId) {
        Optional<Panier> panier = panierRepository.findByClientId(clientId);
        if (panier.isPresent()) {
            return panierProduitRepository.findByPanierId(panier.get().getId());
        }
        return Collections.emptySet(); // Retourne un ensemble vide si aucun panier n'est trouvé pour ce client
    }


    public Long findPanierIdByClientId(Long clientId) {
        // Supposez que vous ayez une méthode dans votre repository pour trouver le panier par client
        Optional<Panier> panier = panierRepository.findByClientId(clientId);
        return panier != null ? panier.get().getId() : null;
    }

}
