package com.base.controllers;

import com.base.controllers.views.MainController;
import com.base.models.Alarms;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.text.DecimalFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Timer;
import java.util.TimerTask;


public class OperationManager {

    private ObservableList<String> beehiveTypes;
    private ObservableList<String> diseaseTypes;
    private ObservableList<String> hikeTypes;
    private ObservableList<String> hoursList;
    private ObservableList<String> minutesAndSecondsList;
    private ObservableList<String> yesOrNoList;


    private static OperationManager INSTANCE = null;

    //Constructor------------------------------------------
    private OperationManager() {

        createLists();

    }

    //Singleton Method-------------------------------------
    public static OperationManager getInstance() {

        if (INSTANCE == null)
            INSTANCE = new OperationManager();
        return INSTANCE;

    }

    //Methods-----------------------------------------------

    private void createLists() {

        beehiveTypes = FXCollections.observableArrayList();
        beehiveTypes.addAll("Langstroth", "Dadant", "Layens", "Otro");

        diseaseTypes = FXCollections.observableArrayList();
        diseaseTypes.addAll("Varroosis", "Acarapisosis", "Loque americana", "Loque europea"
                , "Escarabajo (Aethina tumida)", "Ácaro Tropilaelaps", "IAPV", "Otro");

        hikeTypes = FXCollections.observableArrayList();
        hikeTypes.addAll("Alza", "Media alza");

        hoursList = FXCollections.observableArrayList();
        for (int i = 0; i <= 23; i++) {
            hoursList.add(Integer.toString(i));
        }

        minutesAndSecondsList = FXCollections.observableArrayList();
        for (int i = 0; i <= 59; i++) {
            minutesAndSecondsList.add(Integer.toString(i));
        }

        yesOrNoList = FXCollections.observableArrayList();
        yesOrNoList.addAll("si", "no");


    }

    /**
     * given a long representing a date in milliseconds, returns a float number that represents the number of years.
     *
     * @param millis
     * @return float years
     */
    public float millisToYears(Long millis) {

        DecimalFormat df = new DecimalFormat("0.00");
        float years = (float) ((((millis / 1000) / 60) / 60) / 24) / 365;
        float result = (float) Math.round(years * 10) / 10;
        return result;
    }

    /**
     * This method returns a localdatetime given a Localdate, string containing the hours and a string containing
     * the minutes.
     *
     * @param date
     * @param hours
     * @param minutes
     * @return localdatetime ldt
     */
    public LocalDateTime getLocalDateFromNodesValues(LocalDate date, String hours, String minutes) {

        LocalTime ltime = LocalTime.of(Integer.parseInt(hours), Integer.parseInt(minutes));
        LocalDateTime ldt = LocalDateTime.of(date, ltime);
        return ldt;

    }

    /**
     * Given a LocalDatetime, returns an unix epocj millisecond time in string.
     *
     * @param date
     * @return string epochMilli
     */
    public String getDateTimeStringFormat(LocalDateTime date) {

        return Long.toString(date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

    }

    /**
     * Given a localdatetime, return a string with the date and time properly formated: yyyy-MM-dd HH:mm
     *
     * @param ldt
     * @return string localdatetime formate
     */
    public String getFormatedLocalDateTime(LocalDateTime ldt) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd  HH:mm");
        return ldt.format(formatter);
    }

    public ObservableList<String> getBeehiveTypes() {
        return beehiveTypes;
    }

    public ObservableList<String> getDiseaseTypes() {
        return diseaseTypes;
    }

    public ObservableList<String> getHikeTypes() {
        return hikeTypes;
    }

    public ObservableList<String> getHoursList() {
        return hoursList;
    }

    public ObservableList<String> getMinutesAndSecondsList() {
        return minutesAndSecondsList;
    }

    public ObservableList<String> getYesOrNoList() {
        return yesOrNoList;
    }


}
