package com.sene.backend.repository;

import com.sene.backend.entity.achat.PanierProduit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PanierProduitRepository extends JpaRepository<PanierProduit,Long> {
}
