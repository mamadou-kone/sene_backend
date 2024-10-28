package com.sene.backend.repository;

import com.sene.backend.entity.achat.PanierProduit;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface PanierProduitRepository extends JpaRepository<PanierProduit,Long> {
    Set<PanierProduit> findByPanierId(Long panierId);
    Optional<PanierProduit> findById(Long id);

    @Modifying
    @Transactional
    @Query("UPDATE PanierProduit p SET p.acheterBoolean = false WHERE p.acheterBoolean IS NULL")
    void setAcheterBooleanToFalseForExistingRecords();
}
