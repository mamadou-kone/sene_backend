package com.sene.backend.controleur;

import com.sene.backend.service.services.StatistiquesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/statistiques")
@CrossOrigin(origins = "http://localhost:4200") // Assurez-vous que cela correspond à votre frontend
public class StatistiquesController {

    @Autowired
    private StatistiquesService statistiquesService;

    @GetMapping("/utilisateurs")
    public ResponseEntity<Map<String, Object>> getStatistiquesUtilisateurs(@RequestParam String periode) {
        System.out.println("Requête pour la période: " + periode);
        Map<String, Object> statistiques = statistiquesService.getStatistiquesUtilisateursParPeriode(periode);
        return ResponseEntity.ok(statistiques);
    }

    @GetMapping("/achats")
    public ResponseEntity<Map<String, Object>> getStatistiquesAchats(@RequestParam String periode) {
        System.out.println("Requête pour la période: " + periode);
        Map<String, Object> statistiques = statistiquesService.getStatistiquesAchatsParPeriode(periode);
        return ResponseEntity.ok(statistiques);
    }

    @GetMapping("/financements")
    public ResponseEntity<Map<String, Object>> getStatistiquesFinancements(@RequestParam String periode) {
        System.out.println("Requête pour la période: " + periode);
        Map<String, Object> statistiques = statistiquesService.getStatistiquesFinancementsParPeriode(periode);
        return ResponseEntity.ok(statistiques);
    }
}