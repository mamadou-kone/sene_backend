package com.sene.backend.service;

import java.util.List;
import java.util.Optional;

public interface CrudService<T, ID> {
    // Ajouter une entité
    T ajout(T entity);

    // Liste toutes les entités
    List<T> liste();

    // Trouver une entité par son ID
    Optional<T> trouverParId(ID id);

    // Mettre à jour une entité existante
    T miseAJour(T entity, ID id);

    // Supprimer une entité par son ID
    void supprimer(ID id);
}
