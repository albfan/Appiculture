package com.base.models;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

public class Diseases implements Serializable {

    //this is the autoincrmental id from database
    private int id;

    //this is the id of the hive to which belongs.
    private int id_beehive;

    //this is the id of the apiary of the beehives. You need this id, and the id_beehive to find the correct beehive
    //on database.
    private int id_apiary;

    private String disease;

    private String treatment;

    private Date startingDate;

    private Date endingDate;

    public Diseases() {
    }

    public Diseases(int id, int id_beehive, int id_apiary, String disease, String treatment, Date startingDate, Date endingDate) {
        this.id = id;
        this.id_beehive = id_beehive;
        this.id_apiary = id_apiary;
        this.disease = disease;
        this.treatment = treatment;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_beehive() {
        return id_beehive;
    }

    public void setId_beehive(int id_beehive) {
        this.id_beehive = id_beehive;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    public Date getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(Date endingDate) {
        this.endingDate = endingDate;
    }

    public int getId_apiary() {
        return id_apiary;
    }

    public void setId_apiary(int id_apiary) {
        this.id_apiary = id_apiary;
    }
}
