package com.salonMaster_springBoot.salonMaster.exception;

//Pour les autres erreurs métiers qui ne rentrent pas dans les autres catégories
public class GeneralBusinessException extends RuntimeException{

    public GeneralBusinessException(String message){

        super(message);
    }
}
