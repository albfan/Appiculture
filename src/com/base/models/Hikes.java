package com.base.models;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

public class Hikes implements Serializable {

    //this is the autoincrmental id from database
    private int id;

    //this is the id of the hive to which belongs.
    private int id_beehive;

    private int id_apiary;

    private String type;

    private Date placement_date;

    private Date withdrawal_date;

    public Hikes() {
    }

    public Hikes(int id, int id_beehive, int id_apiary, String type, Date placement_date, Date withdrawal_date) {
        this.id = id;
        this.id_beehive = id_beehive;
        this.id_apiary = id_apiary;
        this.type = type;
        this.placement_date = placement_date;
        this.withdrawal_date = withdrawal_date;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId_apiary() {
        return id_apiary;
    }

    public void setId_apiary(int id_apiary) {
        this.id_apiary = id_apiary;
    }

    public Date getPlacement_date() {
        return placement_date;
    }

    public void setPlacement_date(Date placement_date) {
        this.placement_date = placement_date;
    }

    public Date getWithdrawal_date() {
        return withdrawal_date;
    }

    public void setWithdrawal_date(Date withdrawal_date) {
        this.withdrawal_date = withdrawal_date;
    }
}
