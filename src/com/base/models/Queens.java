package com.base.models;

import com.base.controllers.OperationManager;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

/**
 * This class is for controling how old is your queen and if she still alive.
 */
public class Queens implements Serializable {

    //this is the autoincrmental id from database
    private int id;

    //this is the id of the hive to which belongs.
    private int id_beehive;

    private int id_apiary;

    private Date birthdate = null;

    private Date death_date = null;

    //this age is automaticcly calculated with the birthadte and deathdate
    private float ageInYears = 0;

    public Queens() {
    }

    public Queens(int id, int id_beehive, int id_apiary, Date birthdate, Date death_date) {
        this.id = id;
        this.id_beehive = id_beehive;
        this.id_apiary = id_apiary;
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

    public int getId_apiary() {
        return id_apiary;
    }

    public void setId_apiary(int id_apiary) {
        this.id_apiary = id_apiary;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Date getDeath_date() {
        return death_date;
    }

    public void setDeath_date(Date death_date) {
        this.death_date = death_date;
    }

    public float getAgeInYears() {

        Date actualdate = Date.valueOf(LocalDate.now());
        LocalDate localDate;
        Long millis = 0L;

        if (birthdate != null && death_date == null) {

            millis = actualdate.getTime() - birthdate.getTime();
            ageInYears = OperationManager.getInstance().millisToYears(millis);

        } else if (birthdate != null && death_date != null) {

            millis =death_date.getTime() - birthdate.getTime();
            ageInYears = OperationManager.getInstance().millisToYears(millis);

        }

        if (ageInYears < 0) {

            ageInYears = 0;

        }

        return ageInYears;
    }
}
