package com.salonMaster_springBoot.salonMaster.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.time.LocalDate;

@Entity
@Table(name="salons")

public class Salon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom du salon est requis")
    @Size(min=1, max=50, message = "Le nom du salon doit contenir entre 1 et 50 caractères")
    private String name;

    @NotBlank(message = "L'adresse du salon est requise")
    @Size(min = 1, max = 100, message = "L'adresse du salon doit contenir entre 1 et 100 caractères")
    private String address;

    private LocalDate openingDate;

    @Positive(message = "Le nombre d'employés doit être positif")
    private Integer employeeCount;

    @Size(min=1, max=50, message = "Le nom de la region doit contenir entre 1 et 50 caractères")
    private String region;
    @Size(min = 1, max = 50, message = "Le nom du département doit contenir entre 1 et 50 caractères")
    private String departement;

    @OneToOne
    @JoinColumn(name= "user-id")
    private User user;

    public Salon(){}

    public Long getId(){
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(LocalDate openingDate) {
        this.openingDate = openingDate;
    }

    public Integer getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(Integer employeeCount) {
        this.employeeCount = employeeCount;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

















}
