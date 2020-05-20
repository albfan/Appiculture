package com.base.models;

import java.io.Serializable;
import java.time.LocalDateTime;

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

    public void setDate(LocalDateTime date) {
        this.date = date;
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
