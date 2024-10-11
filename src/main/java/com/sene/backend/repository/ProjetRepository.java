package com.sene.backend.repository;

import com.sene.backend.entity.investissement.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjetRepository extends JpaRepository<Projet, Long> {
    // Méthode pour trouver des projets par ID d'agriculteur
    List<Projet> findByAgriculteurId(Long agriculteurId);
}
