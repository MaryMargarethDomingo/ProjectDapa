package com.example.itadmin.projectdapa.survival;

/**
 * Created by Mary Domingo on 16/02/2018.
 */

public class DisasterBean {

    String name = null;
    boolean selected = false;

    public DisasterBean(String name, boolean selected)
    {
        super();

        this.name = name;
        this.selected = selected;
    }



    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public boolean isSelected()
    {
        return selected;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

}
