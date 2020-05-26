package com.base.models;

import com.base.controllers.OperationManager;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;


public class Alarms implements Serializable { //todo revisar y refactorizar esta clase

    private int id;

    private LocalDateTime date;

    private String name;

    private String text;

    private String formatedDate;

    private Boolean finished;
    /**
     * this variable is to parse the boolean value of finished to a readable string. Actually used in the alarms tableview.
     */
    private String finishedString;

    public Alarms() {
    }

    public Alarms(int id, LocalDateTime date, String name, String text, Boolean finished) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.text = text;
        this.finished = finished;
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

        return OperationManager.getInstance().getDateTimeStringFormat(date);

    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * This method is designed to work specially with SQLite since it doesnt accept directly DATE tipes.
     * @param stringDate
     */
    public void setDate(String stringDate){

        date= Instant.ofEpochMilli(Long.parseLong(stringDate)).atZone(ZoneId.systemDefault()).toLocalDateTime();

    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    /**
     * this getter is specially designed to work with SQLite since boolean doesnt exist in this db.
     * Instead SQLIte use integers. 0 for false, 1 for true.
     * @return 0 false or 1 true
     */
    public int getFinishedIntegerFormat() {
        if( finished){
            return 1;
        }else{
            return 0;
        }
    }

    /**
     * This method set the boolean value finished to false if param = 0 or true if param = 1.
     * @param finished
     */
    public void setFinished(int finished) {

        if (finished==0){
            this.finished=false;
        }else if(finished==1){
            this.finished=true;
        }

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

    /**
     * this methos returns a String containing "si" or "no" depending if the boolean is true or false.
     * @return
     */
    public String getFinishedString() {

        if (finished){
            return "si";
        }else{
            return "no";
        }
    }

    @Override
    public String toString() {
        return name;
    }

    public String getFormatedDate() {
        formatedDate = OperationManager.getInstance().getFormatedLocalDateTime(date);
        return formatedDate;
    }
}
