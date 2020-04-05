package com.base.models;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * This class is for controling how old is your queen and if she still alive.
 */
public class Queens implements Serializable {

    //this is the autoincrmental id from database
    private int id;

    //this is the id of the hive to which belongs.
    private int id_beehive;

    private LocalDate birthdate;

    private LocalDate death_date;

    public Queens() {
    }

    public Queens(int id, int id_beehive, LocalDate birthdate, LocalDate death_date) {
        this.id = id;
        this.id_beehive = id_beehive;
        this.birthdate = birthdate;
        this.death_date = death_date;
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

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public LocalDate getDeath_date() {
        return death_date;
    }

    public void setDeath_date(LocalDate death_date) {
        this.death_date = death_date;
    }
}
