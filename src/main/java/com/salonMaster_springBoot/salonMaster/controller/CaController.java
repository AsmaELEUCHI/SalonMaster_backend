package com.salonMaster_springBoot.salonMaster.controller;

import com.salonMaster_springBoot.salonMaster.dto.CaDto;
import com.salonMaster_springBoot.salonMaster.dto.MessageResponse;
import com.salonMaster_springBoot.salonMaster.dto.StatsResponse;
import com.salonMaster_springBoot.salonMaster.entity.ChiffreAffaires;
import com.salonMaster_springBoot.salonMaster.entity.Salon;
import com.salonMaster_springBoot.salonMaster.entity.User;
import com.salonMaster_springBoot.salonMaster.exception.GeneralBusinessException;
import com.salonMaster_springBoot.salonMaster.repository.SalonRepository;
import com.salonMaster_springBoot.salonMaster.service.CaService;
import com.salonMaster_springBoot.salonMaster.service.XssSanitizerService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/ca")
public class CaController {

    @Autowired
    private CaService caservice;
    @Autowired
    private XssSanitizerService xssSanitizerService;
    @Autowired
    private SalonRepository salonRepository;

    // Endpoint pour enregistrer le chiffre d'affaires
    @PostMapping("/save")
    public ResponseEntity<MessageResponse> saveCaController(@RequestBody CaDto caDto, Authentication authentication) {
        String email = authentication.getName();
        Salon salon = salonRepository.findByUserEmail(email)
                .orElseThrow(() -> new GeneralBusinessException("Salon non trouvé."));

        caservice.saveCa(caDto, salon.getId());
        return ResponseEntity.ok(new MessageResponse("Chiffre d'affaires enregistré avec succès."));
    }

    // Endpoint pour mettre à jour un Chiffre d'Affaires existant
    @PutMapping("/update/{caId}")
    public ResponseEntity<String> updateCaController(@RequestBody CaDto caDto, @PathVariable Long caId) {
        caservice.updateCa(caDto, caId);
        return ResponseEntity.ok("Chiffre d'affaires mis à jour avec succès.");
    }

    // Endpoint pour obtenir l'historique complet des chiffres d'affaires
    @GetMapping("/history")
    public ResponseEntity<List<CaDto>> getCaHistoryController(Authentication authentication) {
        String email = authentication.getName();
        Salon salon = salonRepository.findByUserEmail(email)
                .orElseThrow(() -> new GeneralBusinessException("Salon non trouvé."));
        List<ChiffreAffaires> caHistory = caservice.getCaHistory(salon.getId());
        List<CaDto> caHistoryDto = caHistory.stream()
                .map(ca -> {
                    CaDto dto = new CaDto();
                    dto.setMois(ca.getMois());
                    dto.setAnnee(ca.getAnnee());
                    dto.setCa(ca.getCa());
                    dto.setId(ca.getId());
                    return dto;
                })
                .toList();
        return ResponseEntity.ok(caHistoryDto);
    }

    @DeleteMapping("/history/{id}")
    public ResponseEntity<MessageResponse> deleteCa(@PathVariable("id") Long id) {
        try {
            caservice.deleteCa(id);
            return ResponseEntity.ok(new MessageResponse("Chiffre d'affaires supprimé avec succès"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Erreur lors de la suppression du chiffre d'affaires"));
        }
    }

    // Endpoint pour obtenir le total du chiffre d'affaires pour une année donnée
    @GetMapping("/total/{salonId}/{annee}")
    public ResponseEntity<Double> getTotalCaForYearController(@PathVariable long salonId, @PathVariable Integer annee) {
        double totalCaForYear = caservice.getTotalCaForYear(salonId, annee);
        return ResponseEntity.ok(totalCaForYear);
    }

    // Endpoint pour obtenir le total du chiffre d'affaires pour un mois donné d'une
    // année
    @GetMapping("/total/{salonId}/{annee}/{mois}")
    public ResponseEntity<Double> getTotalCaForMonthController(@PathVariable Long salonId, @PathVariable Integer annee,
            @PathVariable String mois) {
        double totalCaForMonth = caservice.getTotalCaForMonth(salonId, annee, mois);
        return ResponseEntity.ok(totalCaForMonth);
    }

    @GetMapping("/stats")
    public ResponseEntity<StatsResponse> getStats(Authentication authentication) {

        String email = authentication.getName();
        Salon salon = salonRepository.findByUserEmail(email)
                .orElseThrow(() -> new GeneralBusinessException("Salon non trouvé."));

        double avgFrance = caservice.getAvgCAFrance();
        double avgRegion = caservice.getAvgCARegion(salon.getRegion());
        double avgDepartement = caservice.getAvgCADepartement(salon.getDepartement());
        double avgUser = caservice.getAvgCaUser(salon.getId());
        StatsResponse statsResponse = new StatsResponse();
        statsResponse.setMessage("Statistiques récupérées avec succès");
        statsResponse.setAvgFrance(avgFrance);
        statsResponse.setAvgRegion(avgRegion);
        statsResponse.setAvgDepartement(avgDepartement);
        statsResponse.setAvgUserCa(avgUser);
        return ResponseEntity.ok(statsResponse);
    }

}
