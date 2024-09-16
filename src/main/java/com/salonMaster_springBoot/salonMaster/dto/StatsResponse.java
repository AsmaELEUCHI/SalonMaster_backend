package com.salonMaster_springBoot.salonMaster.dto;

public class StatsResponse {

    private double avgFrance;
    private double avgRegion;
    private double avgDepartement;
    private double avgUserCa;
    private String message;


    public double getAvgFrance() {
        return avgFrance;

    }

    public void setAvgFrance(double avgFrance) {
        this.avgFrance = avgFrance;
    }

    public double getAvgRegion() {
        return avgRegion;
    }

    public void setAvgRegion(double avgRegion) {
        this.avgRegion = avgRegion;
    }

    public double getAvgDepartement() {
        return avgDepartement;
    }

    public void setAvgDepartement(double avgDepartment) {
        this.avgDepartement = avgDepartment;
    }

    public double getAvgUserCa(){
        return avgUserCa;
    }

    public void setAvgUserCa(double avgUserCa) {
        this.avgUserCa = avgUserCa;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



}
