package com.example.itadmin.projectdapa.maps.model;

/**
 * Created by Michael on 22 Apr 2018.
 */

public class Reports {
    private double latitude;
    private double longitude;
    private String username;
    private String description;

    public Reports(){

    }

    public Reports(double latitude, double longitude, String username, String description){
        this.latitude = latitude;
        this.longitude = longitude;
        this.username = username;
        this.description = description;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
