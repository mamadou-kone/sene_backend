package com.sene.backend.repository;

import com.sene.backend.entity.personne.Agriculteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgriculteurRepository extends JpaRepository<Agriculteur, Long> {
}
