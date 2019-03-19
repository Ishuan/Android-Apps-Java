package com.example.android.quakereport;

public class EarthQuake {

    private double quakeMagnitude;
    private String quakeCity;
    private long quakeTime;
    private String quakeURL;

    public EarthQuake() {
    }

    public EarthQuake(double quakeMagnitude, String quakeCity, long quakeTime, String quakeURL) {
        this.quakeMagnitude = quakeMagnitude;
        this.quakeCity = quakeCity;
        this.quakeTime = quakeTime;
        this.quakeURL = quakeURL;
    }

    public double getQuakeMagnitude() {
        return quakeMagnitude;
    }

    public void setQuakeMagnitude(double quakeMagnitude) {
        this.quakeMagnitude = quakeMagnitude;
    }

    public String getQuakeCity() {
        return quakeCity;
    }

    public void setQuakeCity(String quakeCity) {
        this.quakeCity = quakeCity;
    }

    public long getQuakeTime() {
        return quakeTime;
    }

    public void setQuakeTime(long quakeTime) {
        this.quakeTime = quakeTime;
    }

    public String getQuakeURL() {
        return quakeURL;
    }

    public void setQuakeURL(String quakeURL) {
        this.quakeURL = quakeURL;
    }
}
