package com.example.tianqi.model.bean;

public class AirBean {

    private String title;
    private String hint;
    private int value;


    public AirBean(String title, String hint, int value) {
        this.title = title;
        this.hint = hint;
        this.value = value;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
