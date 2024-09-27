package com.sene.backend.repository;

import com.sene.backend.entity.investissement.Investissement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InvestissementRepository extends JpaRepository<Investissement, Long> {

    @Query("SELECT i FROM Investissement i WHERE i.dateInvestissement BETWEEN :startDate AND :endDate")
    List<Investissement> findByDateInvestissementBetween(@Param("startDate") LocalDate startDate,
                                                         @Param("endDate") LocalDate endDate);
}