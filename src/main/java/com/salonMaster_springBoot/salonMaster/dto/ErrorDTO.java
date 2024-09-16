package com.salonMaster_springBoot.salonMaster.dto;

public class ErrorDTO {
    private int httpStatus;
    private String errorCode;
    private String message;

    public ErrorDTO(int httpStatus, String errorCode, String message){

        this.httpStatus= httpStatus;
        this.errorCode=errorCode;
        this.message=message;
    }
    // Getters
    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus){
        this.httpStatus=httpStatus;
    }
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode){
        this.errorCode=errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message){
        this.message=message;    }

}

