package com.sene.backend.repository;

import com.sene.backend.entity.produit.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {
    @Query("SELECT p FROM Produit p WHERE p.agriculteur.id = :agriculteurId")
    List<Produit> findByAgriculteurId(@Param("agriculteurId") Long agriculteurId);
}
