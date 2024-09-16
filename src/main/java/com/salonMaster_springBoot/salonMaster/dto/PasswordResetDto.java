package com.salonMaster_springBoot.salonMaster.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class PasswordResetDto {

    @Email(message = "Email invalide")
    @NotBlank(message = "L'email est requis")
    private String email;

    // Getters et Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
