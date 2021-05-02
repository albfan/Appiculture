package com.base.models;

import java.io.Serializable;
import java.sql.Date;

public class Cores implements Serializable {

    private int id;
    private int id_apiary;
    private Date date;
    private int breeding_frames;
    private String notes;

    public Cores() {
    }

    public Cores(int id, int id_apiary, Date date, int breeding_frames, String notes) {
        this.id = id;
        this.id_apiary = id_apiary;
        this.date = date;
        this.breeding_frames = breeding_frames;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_apiary() {
        return id_apiary;
    }

    public void setId_apiary(int id_apiary) {
        this.id_apiary = id_apiary;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getBreeding_frames() {
        return breeding_frames;
    }

    public void setBreeding_frames(int breeding_frames) {
        this.breeding_frames = breeding_frames;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
