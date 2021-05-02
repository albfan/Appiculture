package com.base.models;

import java.io.Serializable;
import java.time.LocalDate;

public class Swarms implements Serializable {

    //this is the autoincrmental id from database
    private int id;

    //this is the id of the hive to which belongs.
    private int id_beehive;

    //this is the date when the beekeeper decide to create an artificial swarm to multiply his beehives.
    private LocalDate date;

    //this is the quantity of breed frames you have in your artificial swarm
    private int breed_frames;

    public Swarms() {
    }

    public Swarms(int id, int id_beehive, LocalDate date, int breed_frames) {
        this.id = id;
        this.id_beehive = id_beehive;
        this.date = date;
        this.breed_frames = breed_frames;
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

    public int getBreed_frames() {
        return breed_frames;
    }

    public void setBreed_frames(int breed_frames) {
        this.breed_frames = breed_frames;
    }
}
