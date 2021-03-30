package com.example.tianqi.model.bean;

public class WeatherCacheBean {




    public String getTfTeam() {
        return TfTeam;
    }

    public void setTfTeam(String tfTeam) {
        TfTeam = tfTeam;
    }

    public String getTfTime() {
        return TfTime;
    }

    public void setTfTime(String tfTime) {
        TfTime = tfTime;
    }

    public String getTfWeaIcon() {
        return TfWeaIcon;
    }

    public void setTfWeaIcon(String tfWeaIcon) {
        TfWeaIcon = tfWeaIcon;
    }

    public String getTfRainState() {
        return TfRainState;
    }

    public void setTfRainState(String tfRainState) {
        TfRainState = tfRainState;
    }


    private String city;
    private String describe;
    private String tfData;
    private String tfQuality;
    private String TfWindy;
    private String TfTime;
    private String TfTeam;
    private String TfWeaIcon;
    private String TfRainState;
    private String fiveWea;
    private String lifeIndex;




    public WeatherCacheBean(String city, String describe, String tfdata,String tfQuality, String fiveWea, String lifeIndex) {
        this.city = city;
        this.describe = describe;
        this.tfData=tfdata;
        this.tfQuality=tfQuality;
        this.fiveWea = fiveWea;
        this.lifeIndex = lifeIndex;
    }

    public WeatherCacheBean(String city, String describe, String tfdata,String tfQuality, String fiveWea, String lifeIndex,String tfRainState ) {
        this.city = city;
        this.describe = describe;
        this.tfData=tfdata;
        this.tfQuality=tfQuality;
        this.fiveWea = fiveWea;
        this.lifeIndex = lifeIndex;
        this.TfRainState = tfRainState;
    }

    public WeatherCacheBean(String city, String describe, String tfData, String tfQuality, String tfWindy, String tfTime, String tfTeam, String tfWeaIcon, String tfRainState, String fiveWea, String lifeIndex) {
        this.city = city;
        this.describe = describe;
        this.tfData = tfData;
        this.tfQuality = tfQuality;
        this.TfWindy = tfWindy;
        this.TfTime = tfTime;
        this.TfTeam = tfTeam;
        this.TfWeaIcon = tfWeaIcon;
        this.TfRainState = tfRainState;
        this.fiveWea = fiveWea;
        this.lifeIndex = lifeIndex;
    }






    public String getTfData() {
        return tfData;
    }

    public void setTfData(String tfData) {
        this.tfData = tfData;
    }

    public String getTfWindy() {
        return TfWindy;
    }

    public void setTfWindy(String tfWindy) {
        TfWindy = tfWindy;
    }

    public String getTfQuality() {
        return tfQuality;
    }

    public void setTfQuality(String tfQuality) {
        this.tfQuality = tfQuality;
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getFiveWea() {
        return fiveWea;
    }

    public void setFiveWea(String fiveWea) {
        this.fiveWea = fiveWea;
    }

    public String getLifeIndex() {
        return lifeIndex;
    }

    public void setLifeIndex(String lifeIndex) {
        this.lifeIndex = lifeIndex;
    }
}
