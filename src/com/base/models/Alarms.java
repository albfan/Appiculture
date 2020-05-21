package com.base.models;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;


public class Alarms implements Serializable {

    private int id;

    private LocalDateTime date;

    private String name;

    private String text;

    public Alarms() {
    }

    public Alarms(int id, LocalDateTime date, String name, String text) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getDateStringFormat(){

        return Long.toString(date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setDate(String stringDate){

        date= Instant.ofEpochMilli(Long.parseLong(stringDate)).atZone(ZoneId.systemDefault()).toLocalDateTime();

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
