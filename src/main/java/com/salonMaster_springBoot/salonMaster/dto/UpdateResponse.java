package com.salonMaster_springBoot.salonMaster.dto;

import com.salonMaster_springBoot.salonMaster.entity.Salon;

import java.time.LocalDate;

public class UpdateResponse {
    private String message;
    private UserProfileDto salon;

    // Getters et Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserProfileDto getSalon() {
        return salon;
    }

    public void setSalon(UserProfileDto salon) {
        this.salon = salon;
    }
}
