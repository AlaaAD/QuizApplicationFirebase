package com.example.quizapp_m3.Model;

public class LocationHelper {
    private double Longitude;
    private double Latitude;
    private String user;

    public LocationHelper(String user,double Latitude,double Longitude){
        this.user = user;
        this.Latitude = Latitude;
        this.Longitude = Longitude;

    }



    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
