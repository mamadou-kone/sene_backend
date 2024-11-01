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

    @Transactional
    @Modifying
    @Query("UPDATE PanierProduit pp SET pp.acheterBoolean = true WHERE pp.panier.id = :panierId")
    void updateAcheterBooleanByPanierId(Long panierId);
}
