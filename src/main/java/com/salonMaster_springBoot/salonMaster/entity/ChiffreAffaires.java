package com.salonMaster_springBoot.salonMaster.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "chiffresaffaires")
public class ChiffreAffaires {

    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    @Column(nullable = false)
    private String mois;

    @Column(nullable = false)
    private Integer annee;

    @Column(nullable = false)
    private Double ca;

    @ManyToOne
    @JoinColumn(name="salon_id")
    private Salon salon;

    public ChiffreAffaires(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Salon getSalon() {
        return salon;
    }

    public void setSalon(Salon salon) {
        this.salon = salon;
    }

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

    public Double getCa() {
        return ca;
    }

    public void setCa(Double ca) {
        this.ca = ca;
    }











}
