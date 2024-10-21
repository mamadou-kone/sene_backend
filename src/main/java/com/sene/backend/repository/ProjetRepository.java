package com.sene.backend.repository;

import com.sene.backend.entity.investissement.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjetRepository extends JpaRepository<Projet, Long> {
    // Méthode pour trouver des projets par ID d'agriculteur
    @Query("SELECT p FROM Projet p WHERE p.agriculteur.id = :agriculteurId")
    List<Projet> findByAgriculteurId(@Param("agriculteurId") Long agriculteurId);

}
