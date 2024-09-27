package com.sene.backend.service.services;

import com.sene.backend.entity.paiement.PaiementInvestissement;
import com.sene.backend.repository.PaiementInvestissementRepository;
import com.sene.backend.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaiementInvestissementService implements CrudService<PaiementInvestissement, Long> {

    @Autowired
    private PaiementInvestissementRepository paiementInvestissementRepository;

    @Override
    public PaiementInvestissement ajout(PaiementInvestissement entity) {
        return paiementInvestissementRepository.save(entity);
    }

    @Override
    public List<PaiementInvestissement> liste() {
        return paiementInvestissementRepository.findAll();
    }

    @Override
    public Optional<PaiementInvestissement> trouverParId(Long id) {
        return paiementInvestissementRepository.findById(id);
    }

    @Override
    public PaiementInvestissement miseAJour(PaiementInvestissement entity, Long id) {
        Optional<PaiementInvestissement> existingPaiementInvestissement = paiementInvestissementRepository.findById(id);
        if (existingPaiementInvestissement.isPresent()) {
            PaiementInvestissement updatedPaiementInvestissement = existingPaiementInvestissement.get();
            updatedPaiementInvestissement.setMontant(entity.getMontant());
            updatedPaiementInvestissement.setDatePaiement(entity.getDatePaiement());
            updatedPaiementInvestissement.setProjet(entity.getProjet());
            return paiementInvestissementRepository.save(updatedPaiementInvestissement);
        } else {
            return null;
        }
    }

    @Override
    public void supprimer(Long id) {
        paiementInvestissementRepository.deleteById(id);
    }
}
