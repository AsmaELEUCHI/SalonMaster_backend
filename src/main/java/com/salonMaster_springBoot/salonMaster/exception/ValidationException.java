package com.salonMaster_springBoot.salonMaster.exception;

// Cette exception est utilisée pour les erreurs liées à la validation des données.
public class ValidationException extends RuntimeException {
    public ValidationException(String message){
        super(message);
    }
}
