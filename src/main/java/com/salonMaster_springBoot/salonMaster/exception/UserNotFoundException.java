package com.salonMaster_springBoot.salonMaster.exception;

//Cette exception gère le cas où un utilisateur n'est pas trouvé, sans afficher l'ID ou d'autres informations sensibles.
public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(){
        super("Utilisateur non trouvé.");
    }
}
