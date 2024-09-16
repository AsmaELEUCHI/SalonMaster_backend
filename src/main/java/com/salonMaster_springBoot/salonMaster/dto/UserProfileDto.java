package com.salonMaster_springBoot.salonMaster.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class UserProfileDto {


    @NotBlank(message = "Le nom du salon est requis")
    @Size(min = 1, max = 100, message = "Le nom du salon doit contenir entre 1 et 100 caractères")
    private String salonName;

    @NotBlank(message = "L'adresse du salon est requise")
    @Size(min = 1, max = 255, message = "L'adresse du salon doit contenir entre 1 et 255 caractères")
    private String salonAddress;

    @Positive(message = "Le nombre d'employés doit être positif")
    private Integer numberOfEmployees;

    private LocalDate openingDate;
    @Size(min=1, max=50, message = "Le nom du salon doit contenir entre 1 et 50 caractères")
    private String region;
    @Size(min=1, max=50, message = "Le nom du salon doit contenir entre 1 et 50 caractères")
    private String departement;



    // Getters et Setters
    public String getSalonName() {
        return salonName;
    }

    public void setSalonName(String salonName) {
        this.salonName = salonName;
    }

    public String getSalonAddress() {
        return salonAddress;
    }

    public void setSalonAddress(String salonAddress) {
        this.salonAddress = salonAddress;
    }

    public Integer getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setNumberOfEmployees(Integer numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }

    public LocalDate getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(LocalDate openingDate) {
        this.openingDate = openingDate;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String department) {
        this.departement = department;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

}
