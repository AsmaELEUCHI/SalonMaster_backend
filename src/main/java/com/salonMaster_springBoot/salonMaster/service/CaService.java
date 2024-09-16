package com.salonMaster_springBoot.salonMaster.service;

import com.salonMaster_springBoot.salonMaster.dto.CaDto;
import com.salonMaster_springBoot.salonMaster.entity.ChiffreAffaires;
import com.salonMaster_springBoot.salonMaster.entity.Salon;
import com.salonMaster_springBoot.salonMaster.repository.CaRepository;
import com.salonMaster_springBoot.salonMaster.repository.SalonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaService {

    @Autowired
    private CaRepository caRepository;
    @Autowired
    private SalonRepository salonRepository;

   //Methode de création d'un objet ChiffreAffaires lors de la saisie sur le formulaire pour la premiére fois
    public void saveCa(CaDto caDto, Long salonId) {
        // Rechercher le salon par ID
        Salon salon = salonRepository.findById(salonId)
                .orElseThrow(() -> new RuntimeException("Salon non trouvé"));

        // Créer un nouvel objet ChiffreAffaires et lier le salon
        ChiffreAffaires ca = new ChiffreAffaires();
        ca.setMois(caDto.getMois());
        ca.setAnnee(caDto.getAnnee());
        ca.setCa(caDto.getCa());
        ca.setSalon(salon);

        // Sauvegarder dans la base de données
        caRepository.save(ca);

        // Debug: Afficher le résultat pour vérification
        System.out.println("Saved ChiffreAffaires: " + ca);
    }
    // Methode pour mettre à jour l'objet ChiffreAffaires
    public void updateCa(CaDto caDto, Long caId) {
        // Rechercher le chiffre d'affaires par ID
        ChiffreAffaires ca = caRepository.findById(caId)
                .orElseThrow(() -> new RuntimeException("Chiffre d'affaires non trouvé"));

        // Mettre à jour les champs
        ca.setMois(caDto.getMois());
        ca.setAnnee(caDto.getAnnee());
        ca.setCa(caDto.getCa());

        // Sauvegarder dans la base de données
        caRepository.save(ca);

        // Debug: Afficher le résultat pour vérification
        System.out.println("Updated ChiffreAffaires: " + ca);
    }



    // Obtenir l'historique des chiffres d'affaires pour un salon

    public List<ChiffreAffaires> getCaHistory(Long salonId){
        return  caRepository.findBySalonId(salonId);
    }

    public void deleteCa(Long id) {
        caRepository.deleteById(id);
    }

    // Obtenir les statistiques des chiffres d'affaires par année pour un salon
    public double getTotalCaForYear(Long salonId, Integer annee){
        List<ChiffreAffaires> caList = getCaHistory(salonId);
        return caList.stream()
                    .filter(ca -> ca.getAnnee().equals(annee))
                    .mapToDouble(ChiffreAffaires::getCa)
                    .sum(); // sum est disponible que sur streams de types primitifs comme DoubleStream
    }

    //Obtenir les statistiques des chiffres d'affaires par moins pour un salon
     public double getTotalCaForMonth(Long salonId, Integer annee, String mois){
        List<ChiffreAffaires> caList = caRepository.findBySalonIdAndMois(salonId, mois);
        return caList.stream()
                .filter(ca -> ca.getAnnee().equals(annee))
                .mapToDouble(ChiffreAffaires::getCa)
                .sum();
     }


    // CA moyen pour toute la France
     public double getAvgCAFrance() {
        return caRepository.findAvgCaForFrance();
     }


    // CA moyen pour la région du salon de l'utilisateur
    public double getAvgCARegion(String region) {
        return caRepository.findAvgCaForRegion(region);
    }

    // CA moyen pour le département du salon de l'utilisateur
    public double getAvgCADepartement(String department) {
        return caRepository.findAvgCaForDepartement(department);
    }

    public double getAvgCaUser(Long salonId){
        Double avgCaUser = caRepository.findAvgCaForUser(salonId);
        return avgCaUser != null ? avgCaUser : 0.0;
    }

}
