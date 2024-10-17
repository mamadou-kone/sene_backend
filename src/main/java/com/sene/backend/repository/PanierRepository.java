package com.sene.backend.repository;
import com.sene.backend.entity.achat.Panier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PanierRepository extends JpaRepository<Panier, Long> {
    // Méthode pour récupérer un panier à partir de l'ID du client
    Optional<Panier> findByClientId(Long clientId);
}
