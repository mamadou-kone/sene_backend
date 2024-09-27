package com.sene.backend.repository;

import com.sene.backend.entity.achat.Achat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AchatRepository extends JpaRepository<Achat, Long> {

    @Query("SELECT a FROM Achat a WHERE a.dateAchat BETWEEN :startDateTime AND :endDateTime")
    List<Achat> findByDateAchatBetween(@Param("startDateTime") LocalDateTime startDateTime,
                                       @Param("endDateTime") LocalDateTime endDateTime);
}