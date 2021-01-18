package com.hfad.ad2noteapp.models;

public class BoardData {
    private  String name;
    private String desc;
    private int imageResourceId;

    public BoardData(String name, String desc, int imageResourceId) {
        this.name = name;
        this.desc = desc;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
}
