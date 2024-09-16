package com.salonMaster_springBoot.salonMaster.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PasswordUpdateDto {

    @NotBlank(message = "Le mot de passe est requis")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}",
            message = "Le mot de passe doit contenir au moins une majuscule, un chiffre et un caractère spécial")
    private String newPassword;

    public String getNewPassword(){
        return newPassword;
    }
    public void setNewPassword(String newPassword){
        this.newPassword=newPassword;
    }
}
