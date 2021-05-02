package com.base.models;

import java.io.Serializable;

/**
 * This class represent where the hives are located. One apiary can have minimum 1 and N maximum hives.
 * You can have minimum 1 and maximun N apiaries.
 */
public class Apiaries implements Serializable {

    // this is the internal autoincrement id from database
    private int id;

    //this is the name that identify the apiary
    private String name;

    //this is the adress where the Apiary y located
    private String adress;

    public Apiaries() {
    }

    public Apiaries(String name, String adress) {
        this.name = name;
        this.adress = adress;
    }

    public int getId() {
        return id;
    }

    //Database create id automaticly. Use this method with caution.
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    @Override
    public String toString() {
        return name;
    }
}
