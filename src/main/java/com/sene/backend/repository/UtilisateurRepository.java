package com.sene.backend.repository;

import com.sene.backend.entity.personne.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    List<Utilisateur> findByDateInscriptionBetween(LocalDate startDate, LocalDate endDate);
    Optional<Utilisateur> findByEmail(String email);

}