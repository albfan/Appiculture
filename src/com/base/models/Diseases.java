package com.base.models;

import java.io.Serializable;
import java.time.LocalDate;

public class Diseases implements Serializable {

    //this is the autoincrmental id from database
    private int id;

    //this is the id of the hive to which belongs.
    private int id_beehive;

    private String disease;

    private String treatment;

    private LocalDate startingDate;

    private LocalDate endingDate;

    public Diseases() {
    }

    public Diseases(int id, int id_beehive, String disease, String treatment, LocalDate startingDate, LocalDate endingDate) {
        this.id = id;
        this.id_beehive = id_beehive;
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

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }

    public LocalDate getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(LocalDate endingDate) {
        this.endingDate = endingDate;
    }
}
