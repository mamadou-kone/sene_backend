package com.sene.backend.service.services;

import com.sene.backend.entity.achat.Achat;
import com.sene.backend.repository.AchatRepository;
import com.sene.backend.repository.PanierProduitRepository;
import com.sene.backend.service.CrudService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AchatService implements CrudService<Achat, Long> {

    @Autowired // Ajoutez cette annotation pour injecter le repository
    private AchatRepository achatRepository;
    @Autowired
    private PanierProduitRepository panierProduitRepository;

    @Override
    public Achat ajout(Achat entity) {
        return achatRepository.save(entity);
    }

    @Override
    public List<Achat> liste() {
        return achatRepository.findAll();
    }

    @Override
    public Optional<Achat> trouverParId(Long id) {
        return achatRepository.findById(id);
    }

    @Override
    public Achat miseAJour(Achat entity, Long id) {
        Optional<Achat> existingAchat = achatRepository.findById(id);
        if (existingAchat.isPresent()) {
            Achat updatedAchat = existingAchat.get();
            updatedAchat.setDateAchat(entity.getDateAchat());
            updatedAchat.setPaiementAchat(entity.getPaiementAchat());
            return achatRepository.save(updatedAchat);
        } else {
            return null;
        }
    }

    @Override
    public void supprimer(Long id) {
        achatRepository.deleteById(id);
    }


    @Transactional
    public void updateAcheterBooleanByPanierId(Long panierId) {
        panierProduitRepository.updateAcheterBooleanByPanierId(panierId);
    }
}