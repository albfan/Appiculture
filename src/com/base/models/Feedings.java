package com.base.models;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * This class is used to control what kind of food,when and quantity you use in your hives
 */
public class Feedings implements Serializable {

    //this is the autoincrmental id from database
    private int id;

    //this is the id of the hive to which belongs.
    private int id_beehive;

    //this is the date of when the feedings have been made.
    private LocalDate date;

    //this is the quantity in solid food.
    private double solid_quant;

    //this is the quantity of the liquid food.
    private double liquid_quant;

    public Feedings() {
    }

    public Feedings(int id, int id_beehive, LocalDate date, double solid_quant, double liquid_quant) {
        this.id = id;
        this.id_beehive = id_beehive;
        this.date = date;
        this.solid_quant = solid_quant;
        this.liquid_quant = liquid_quant;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getSolid_quant() {
        return solid_quant;
    }

    public void setSolid_quant(double solid_quant) {
        this.solid_quant = solid_quant;
    }

    public double getLiquid_quant() {
        return liquid_quant;
    }

    public void setLiquid_quant(double liquid_quant) {
        this.liquid_quant = liquid_quant;
    }
}
