package com.salonMaster_springBoot.salonMaster.exception;

//Cette exception est utilisée lorsque l'email est déjà enregistré dans la base de données.

public class EmailAlreadyExistsException extends RuntimeException{
    public EmailAlreadyExistsException(){
        super("Cet email est déjà utilisé.");
    }
}
