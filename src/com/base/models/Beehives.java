package com.base.models;

import java.io.Serializable;
import java.time.LocalDate;

public class Beehives implements Serializable {

    //this is the number that identify the hive. It is not autogenerated and user have to introduce it.
    private int number;

    //this is the id of the apiary to which the hive belongs.
    private int id_apiary;

    //this is the date where hive was starting to be used in an Apiary
    private LocalDate date;

    //this is the type of hive. You have diferent models in the market
    private String type;

    private boolean favorite;

    public Beehives() {
    }

    public Beehives(int number, int id_apiary, LocalDate date, String type, boolean favorite) {
        this.number = number;
        this.id_apiary = id_apiary;
        this.date = date;
        this.type = type;
        this.favorite = favorite;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getId_apiary() {
        return id_apiary;
    }

    public void setId_apiary(int id_apiary) {
        this.id_apiary = id_apiary;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
