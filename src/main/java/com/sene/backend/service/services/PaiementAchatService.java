package com.sene.backend.service.services;

import com.sene.backend.entity.paiement.PaiementAchat;
import com.sene.backend.repository.PaiementAchatRepository;
import com.sene.backend.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaiementAchatService implements CrudService<PaiementAchat, Long> {

    @Autowired
    private PaiementAchatRepository paiementAchatRepository;

    @Override
    public PaiementAchat ajout(PaiementAchat entity) {
        return paiementAchatRepository.save(entity);
    }

    @Override
    public List<PaiementAchat> liste() {
        return paiementAchatRepository.findAll();
    }

    @Override
    public Optional<PaiementAchat> trouverParId(Long id) {
        return paiementAchatRepository.findById(id);
    }

    @Override
    public PaiementAchat miseAJour(PaiementAchat entity, Long id) {
        Optional<PaiementAchat> existingPaiementAchat = paiementAchatRepository.findById(id);
        if (existingPaiementAchat.isPresent()) {
            PaiementAchat updatedPaiementAchat = existingPaiementAchat.get();
            updatedPaiementAchat.setMontant(entity.getMontant());
            updatedPaiementAchat.setDatePaiement(entity.getDatePaiement());
            updatedPaiementAchat.setAchat(entity.getAchat());
            return paiementAchatRepository.save(updatedPaiementAchat);
        } else {
            return null;
        }
    }

    @Override
    public void supprimer(Long id) {
        paiementAchatRepository.deleteById(id);
    }
}
