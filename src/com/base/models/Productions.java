package com.base.models;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

public class Productions implements Serializable {

    //this is the autoincrmental id from database
    private int id;

    //this is the id of the hive to which belongs.
    private int id_beehive;

    //this is the date when the beekeeper take the production out of the hive
    private Date date;

    //this is the breeding frames quantity that have produced the hive
    private int breed_frames_quant;

    private double honey_quant;

    //this is the quantities of new queens that have been produced
    private int royals_quant;

    private double pollen_quant;

    private double wax_quant;

    private double royalJelly_quant;

    private int id_apiary;

    public Productions() {
    }

    public Productions(int id, int id_beehive, Date date, int breed_frames_quant,
                       double honey_quant, int royals_quant, double pollen_quant,
                       double wax_quant, double royalJelly_quant, int id_apiary) {
        this.id = id;
        this.id_beehive = id_beehive;
        this.date = date;
        this.breed_frames_quant = breed_frames_quant;
        this.honey_quant = honey_quant;
        this.royals_quant = royals_quant;
        this.pollen_quant = pollen_quant;
        this.wax_quant = wax_quant;
        this.royalJelly_quant = royalJelly_quant;
        this.id_apiary = id_apiary;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId_apiary() {
        return id_apiary;
    }

    public void setId_apiary(int id_apiary) {
        this.id_apiary = id_apiary;
    }

    public int getBreed_frames_quant() {
        return breed_frames_quant;
    }

    public void setBreed_frames_quant(int breed_frames_quant) {
        this.breed_frames_quant = breed_frames_quant;
    }

    public double getHoney_quant() {
        return honey_quant;
    }

    public void setHoney_quant(double honey_quant) {
        this.honey_quant = honey_quant;
    }

    public int getRoyals_quant() {
        return royals_quant;
    }

    public void setRoyals_quant(int royals_quant) {
        this.royals_quant = royals_quant;
    }

    public double getPollen_quant() {
        return pollen_quant;
    }

    public void setPollen_quant(double pollen_quant) {
        this.pollen_quant = pollen_quant;
    }

    public double getWax_quant() {
        return wax_quant;
    }

    public void setWax_quant(double wax_quant) {
        this.wax_quant = wax_quant;
    }

    public double getRoyalJelly_quant() {
        return royalJelly_quant;
    }

    public void setRoyalJelly_quant(double royalJelly_quant) {
        this.royalJelly_quant = royalJelly_quant;
    }
}
