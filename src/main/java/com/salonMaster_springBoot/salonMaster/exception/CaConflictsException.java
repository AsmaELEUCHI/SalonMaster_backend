package com.salonMaster_springBoot.salonMaster.exception;

//Gère les erreurs liées à des conflits lors de la saisie de chiffres d'affaires
public class CaConflictsException extends RuntimeException{
    public CaConflictsException(){
        super("Les données de chiffre d'affaires pour ce mois et cette année existent déjà.");
    }

}
