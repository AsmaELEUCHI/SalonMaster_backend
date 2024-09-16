package com.salonMaster_springBoot.salonMaster.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public class CaDto {

    private Long id;

    @NotBlank(message = "Le mois est requis")
    @Pattern(regexp = "^(0[1-9]|1[0-2])$", message = "Mois invalide. Veuillez entrer un mois de 01 à 12.")
    private String mois;

    @NotNull(message = "L'année est requise")
    @Pattern(regexp = "^\\d{4}$", message = "Année invalide. Veuillez entrer une année sur 4 chiffres.")
    private Integer annee;

    @NotNull(message = "Le montant est requis")
    @Positive(message = "Le montant doit être positif")
    private double ca;

    // Getters et Setters
    public String getMois() {
        return mois;
    }

    public void setMois(String mois) {
        this.mois = mois;
    }

    public Integer getAnnee() {
        return annee;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public double getCa() {
        return ca;
    }

    public void setCa(double ca) {
        this.ca = ca;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
