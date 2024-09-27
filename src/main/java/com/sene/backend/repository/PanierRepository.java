package com.sene.backend.repository;
import com.sene.backend.entity.achat.Panier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PanierRepository extends JpaRepository<Panier, Long> {
}
