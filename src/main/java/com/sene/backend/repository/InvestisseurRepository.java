package com.sene.backend.repository;

import com.sene.backend.entity.personne.Investisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestisseurRepository extends JpaRepository<Investisseur, Long> {
}
