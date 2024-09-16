package com.salonMaster_springBoot.salonMaster.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;



@Entity
@Table (name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) //génération de clé automatique
    private Long id;

  @NotBlank(message = "Le nom de représentant est requis")
  @Size(min=1, max=50, message ="Le nom doit contenir entre 1 et 50 caractères")
    private String representativeLastName;

  @NotBlank(message = "Le prénom de représentant est requis")
  @Size(min=1, max=50, message ="Le prénom doit contenir entre 1 et 50 caractères")
    private String representativeFirstName;


    @NotBlank(message = "L'email est requis")
    private String email;


    @NotBlank(message = "Le mot de passe est requis")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
        private String password;

    private boolean enabled;

    // Constructeurs
    public User() {}

    //Getters et setters

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getRepresentativeFirstName(){
        return representativeFirstName;
    }
    public void setRepresentativeFirstName(String representativeFirstName){
        this.representativeFirstName=representativeFirstName;
    }

    public String getRepresentativeLastName(){
        return representativeLastName;
    }
    public void setRepresentativeLastName(String representativeLastName){
        this.representativeLastName=representativeLastName;
}
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Getter pour l'activation
    public boolean isEnabled() {
        return enabled;
    }

    // Setter pour l'activation
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}