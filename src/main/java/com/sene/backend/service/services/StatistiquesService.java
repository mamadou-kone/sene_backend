package com.sene.backend.service.services;

import com.sene.backend.entity.achat.Achat;
import com.sene.backend.entity.investissement.Investissement;
import com.sene.backend.entity.personne.Utilisateur;
import com.sene.backend.repository.AchatRepository;
import com.sene.backend.repository.InvestissementRepository;
import com.sene.backend.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatistiquesService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private AchatRepository achatRepository;

    @Autowired
    private InvestissementRepository investissementRepository;

    public Map<String, Object> getStatistiquesUtilisateursParPeriode(String periode) {
        LocalDate[] dates = parsePeriode(periode);
        List<Utilisateur> utilisateurs = utilisateurRepository.findByDateInscriptionBetween(dates[0], dates[1]);

        Map<String, Long> utilisateursParMois = utilisateurs.stream()
                .collect(Collectors.groupingBy(u -> u.getDateInscription().getMonth() + " " + u.getDateInscription().getYear(), Collectors.counting()));

        return createResponse("Utilisateurs", utilisateursParMois);
    }

    public Map<String, Object> getStatistiquesAchatsParPeriode(String periode) {
        LocalDate[] dates = parsePeriode(periode);
        LocalDateTime startDateTime = dates[0].atStartOfDay();
        LocalDateTime endDateTime = dates[1].atTime(23, 59, 59);
        List<Achat> achats = achatRepository.findByDateAchatBetween(startDateTime, endDateTime);

        Map<String, Double> achatsParMois = achats.stream()
                .collect(Collectors.groupingBy(a -> a.getDateAchat().getMonth() + " " + a.getDateAchat().getYear(),
                        Collectors.summingDouble(Achat::getMontant)));

        return createResponse("Achats", achatsParMois);
    }

    public Map<String, Object> getStatistiquesFinancementsParPeriode(String periode) {
        LocalDate[] dates = parsePeriode(periode);
        List<Investissement> investissements = investissementRepository.findByDateInvestissementBetween(dates[0], dates[1]);

        Map<String, Double> financementsParMois = investissements.stream()
                .collect(Collectors.groupingBy(i -> i.getDateInvestissement().getMonth() + " " + i.getDateInvestissement().getYear(),
                        Collectors.summingDouble(Investissement::getMontant)));

        return createResponse("Financements", financementsParMois);
    }

    private LocalDate[] parsePeriode(String periode) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String[] dateParts = periode.split("to");
        LocalDate startDate = LocalDate.parse(dateParts[0].trim(), formatter);
        LocalDate endDate = LocalDate.parse(dateParts[1].trim(), formatter);
        return new LocalDate[]{startDate, endDate};
    }

    private Map<String, Object> createResponse(String label, Map<String, ? extends Number> dataMap) {
        List<String> labels = dataMap.keySet().stream().toList();
        List<Double> data = labels.stream().map(dataMap::get).map(Number::doubleValue).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("labels", labels);
        response.put("datasets", List.of(Map.of(
                "label", label,
                "data", data,
                "backgroundColor", getColor(label),
                "borderColor", getColor(label)
        )));
        return response;
    }

    private String getColor(String label) {
        switch (label) {
            case "Utilisateurs":
                return "#3e95cd";
            case "Achats":
                return "#8e5ea2";
            case "Financements":
                return "#3cba9f";
            default:
                return "#c45850"; // Couleur par défaut
        }
    }
}