package com.example.itadmin.projectdapa.maps.model;

/**
 * Created by Michael on 22 Apr 2018.
 */

public class Reports {
    private double latitude;
    private double longitude;
    private String email;
    private String disasterType;
    private String reportDateTime;

    public Reports(){

    }

    public Reports(double latitude, double longitude, String email, String disasterType){
        this.latitude = latitude;
        this.longitude = longitude;
        this.email = email;
        this.disasterType = disasterType;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getEmail() {
        return email;
    }

    public void setEmsil(String email) {
        this.email = email;
    }

    public String getDisasterType() {
        return disasterType;
    }

    public void setDisasterType(String disasterType) {
        this.disasterType = disasterType;
    }
}
