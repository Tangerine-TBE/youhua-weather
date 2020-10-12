package com.example.tianqi.model.bean;

public class LocationBean {

    @Override
    public String toString() {
        return "LocationBean{" +
                "city='" + city + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", wea='" + wea + '\'' +
                ", highTeam=" + highTeam +
                ", lowTeam=" + lowTeam +
                '}';
    }


   public LocationBean() {

    }

    public LocationBean(String city, String wea, double highTeam, double lowTeam) {
        this.city = city;
        this.wea = wea;
        this.highTeam = highTeam;
        this.lowTeam = lowTeam;
    }

    public LocationBean(String city, double longitude, double latitude) {
        this.city = city;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public LocationBean(String city, double longitude , double latitude, String wea, double highTeam, double lowTeam) {
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.wea = wea;
        this.highTeam = highTeam;
        this.lowTeam = lowTeam;
    }






    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    private String city;

    //纬度
    private double latitude;

    //经度
    private double longitude;

    //天气
    private String wea;

    //高温
    private double highTeam;

    //低温
    private double lowTeam;

    public String getWea() {
        return wea;
    }

    public void setWea(String wea) {
        this.wea = wea;
    }

    public double getHighTeam() {
        return highTeam;
    }

    public void setHighTeam(double highTeam) {
        this.highTeam = highTeam;
    }

    public double getLowTeam() {
        return lowTeam;
    }

    public void setLowTeam(double lowTeam) {
        this.lowTeam = lowTeam;
    }




}
