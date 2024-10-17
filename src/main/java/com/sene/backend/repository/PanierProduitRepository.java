package com.sene.backend.repository;

import com.sene.backend.entity.achat.PanierProduit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface PanierProduitRepository extends JpaRepository<PanierProduit,Long> {
    Set<PanierProduit> findByPanierId(Long panierId);
    Optional<PanierProduit> findById(Long id);
}
