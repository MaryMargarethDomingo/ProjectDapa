package com.example.itadmin.projectdapa.survival.model;

/**
 * Created by Mary Domingo on 16/02/2018.
 */

public class SurvivalBean {
    private String list;
    private int image;

    public SurvivalBean(String list, int image) {
        this.list = list;
        this.image = image;
    }


    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

}
