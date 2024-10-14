package com.sene.backend.controleur;

import com.sene.backend.entity.investissement.Investissement;
import com.sene.backend.entity.personne.Investisseur;
import com.sene.backend.security.configurationSecurity.CurrentUserService;
import com.sene.backend.service.services.InvestissementService;
import com.sene.backend.service.services.InvestisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/investissement")
public class InvestissementController {

    @Autowired
    private InvestissementService investissementService;

    @Autowired
    private CurrentUserService currentUserService;

    @Autowired
    private InvestisseurService investisseurService;

    @PostMapping
    public ResponseEntity<Investissement> ajouterInvestissement(@RequestBody Investissement investissement) {



            Investissement nouvelInvestissement = investissementService.ajout(investissement);
            return ResponseEntity.ok(nouvelInvestissement);


    }

    @GetMapping
    public ResponseEntity<List<Investissement>> listerInvestissements() {
        List<Investissement> investissements = investissementService.liste();
        return ResponseEntity.ok(investissements);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Investissement> obtenirInvestissementParId(@PathVariable Long id) {
        Optional<Investissement> investissement = investissementService.trouverParId(id);
        return investissement.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Investissement> mettreAJourInvestissement(@RequestBody Investissement investissement, @PathVariable Long id) {
        Investissement investissementMisAJour = investissementService.miseAJour(investissement, id);
        if (investissementMisAJour != null) {
            return ResponseEntity.ok(investissementMisAJour);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerInvestissement(@PathVariable Long id) {
        investissementService.supprimer(id);
        return ResponseEntity.noContent().build();
    }
}
