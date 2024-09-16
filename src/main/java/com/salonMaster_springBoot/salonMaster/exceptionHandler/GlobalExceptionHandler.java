package com.salonMaster_springBoot.salonMaster.exceptionHandler;


import com.salonMaster_springBoot.salonMaster.dto.ErrorDTO;
import com.salonMaster_springBoot.salonMaster.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    // Gére les exceptions de type UserNotFoundException et Retourne une réponse HTTP 404
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDTO> handlerUserNotFound(UserNotFoundException ex){
        ErrorDTO errorDTO = new ErrorDTO(HttpStatus.NOT_FOUND.value(), "USER_NOT_FOUND", ex.getMessage());
        return new ResponseEntity<>(errorDTO,HttpStatus.NOT_FOUND);
    }

    // Gére les exceptions de type ValidationException et Retourne une réponse HTTP 400
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorDTO> handlerValidationException(ValidationException ex){

        ErrorDTO errorDTO= new ErrorDTO(HttpStatus.BAD_REQUEST.value(), "VALIDATION_ERROR", ex.getMessage());
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }
    // Gére les exceptions de type GeneralBusinessException et Retourne une réponse HTTP 500
    @ExceptionHandler(GeneralBusinessException.class)
    public ResponseEntity<ErrorDTO> handlerGeneralBusinessException(GeneralBusinessException ex){

        ErrorDTO errorDTO = new ErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "INTERNAL_ERROR", ex.getMessage());
        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Gére les exceptions de type EmailAlreadyExistsException et Retourne une réponse HTTP 409

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorDTO> handleEmailAlreadyExistsException (EmailAlreadyExistsException ex){

        ErrorDTO errorDTO = new ErrorDTO(HttpStatus.CONTINUE.value(), "EMAIL_ALREADY_EXISTS", ex.getMessage() );
        return new ResponseEntity<>(errorDTO, HttpStatus.CONFLICT);
    }

    // Gére les exceptions de type CaConflictsException et Retourne une réponse HTTP 409
    @ExceptionHandler(CaConflictsException.class)
    public ResponseEntity<ErrorDTO> handleCaConflictsException(CaConflictsException ex) {
        ErrorDTO errorDTO = new ErrorDTO(HttpStatus.CONFLICT.value(), "CA_CONFLICTS", ex.getMessage());
        return new ResponseEntity<>(errorDTO, HttpStatus.CONFLICT);
    }

}
