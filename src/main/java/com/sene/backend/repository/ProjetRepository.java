package com.sene.backend.repository;

import com.sene.backend.entity.investissement.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjetRepository extends JpaRepository<Projet, Long> {
    // JpaRepository fournit déjà les méthodes CRUD de base (save, findAll, findById, deleteById...)
}
