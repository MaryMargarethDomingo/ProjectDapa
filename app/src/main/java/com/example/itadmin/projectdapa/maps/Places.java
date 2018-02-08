package com.example.itadmin.projectdapa.maps;

/**
 * Created by Michael on 5 Feb 2018.
 */

public class Places {
    private String name;
    private String vicinity;

    public Places(String name, String vicinity){
        this.name = name;
        this.vicinity = vicinity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }
}
