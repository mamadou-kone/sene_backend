package com.sene.backend.service.services;

import com.sene.backend.entity.achat.Achat;
import com.sene.backend.repository.AchatRepository;
import com.sene.backend.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AchatService implements CrudService<Achat, Long> {

    @Autowired
    private AchatRepository achatRepository;

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
            updatedAchat.setClient(entity.getClient());
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
}
