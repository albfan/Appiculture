package com.base.models;

import java.io.Serializable;
import java.time.LocalDate;

public class Hikes implements Serializable {

    //this is the autoincrmental id from database
    private int id;

    //this is the id of the hive to which belongs.
    private int id_beehive;

    private String type;

    private LocalDate placement_date;

    private LocalDate withdrawal_date;

    public Hikes() {
    }

    public Hikes(int id, int id_beehive, String type, LocalDate placement_date, LocalDate withdrawal_date) {
        this.id = id;
        this.id_beehive = id_beehive;
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

    public LocalDate getPlacement_date() {
        return placement_date;
    }

    public void setPlacement_date(LocalDate placement_date) {
        this.placement_date = placement_date;
    }

    public LocalDate getWithdrawal_date() {
        return withdrawal_date;
    }

    public void setWithdrawal_date(LocalDate withdrawal_date) {
        this.withdrawal_date = withdrawal_date;
    }
}
