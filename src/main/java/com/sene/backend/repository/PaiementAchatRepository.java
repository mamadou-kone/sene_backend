package com.sene.backend.repository;

import com.sene.backend.entity.paiement.PaiementAchat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaiementAchatRepository extends JpaRepository<PaiementAchat, Long> {
}
