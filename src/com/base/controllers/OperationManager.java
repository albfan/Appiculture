package com.base.controllers;

import com.base.controllers.views.MainController;
import com.base.models.Alarms;
import com.base.models.Apiaries;
import com.base.models.Beehives;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import java.io.File;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class OperationManager {

    private ObservableList<String> beehiveTypes;
    private ObservableList<String> diseaseTypes;
    private ObservableList<String> hikeTypes;
    private ObservableList<String> hoursList;
    private ObservableList<String> minutesAndSecondsList;
    private ObservableList<String> yesOrNoList;
    private Connection connection = null;
    private Map<String, Object> reportsParams = null;


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
//Reports=====================================================================

    public String printApiaryReport(File destinationFile, String type, Apiaries... aplist) {

        String s;
        StringBuilder stb = new StringBuilder();
        for (int i = 0; i <= aplist.length - 1; i++) {

            stb.append(aplist[i].getId());
            if (i < aplist.length - 1) {
                stb.append(',');
            }

        }
        connection = DBmanager.getINSTANCE().getConnection();
        reportsParams = new HashMap<>();
        reportsParams.put("listid", stb.toString());

        try {
            //JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/informes/factura.jasper"),parametros, conexion);
            JasperPrint print = JasperFillManager.fillReport("reports/apiary.jasper", reportsParams, connection);
            if (type.equals("pdf")) {

                s = destinationFile.getPath();
                JasperExportManager.exportReportToPdfFile(print, s);
                return s;
            }
            if (type.equals("html")) {
                s = "reports/apiaries.html";
                JasperExportManager.exportReportToHtmlFile(print, s);
                return s;
            }

        } catch (JRException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String printBeehiveReport(File destinationFile, String type, Beehives... bhList) {

        String s;
        StringBuilder stb = new StringBuilder();
        for (int i = 0; i <= bhList.length - 1; i++) {

            stb.append("\'");
            stb.append(bhList[i].getId_apiary());
            stb.append("/");
            stb.append(bhList[i].getNumber());
            stb.append("\'");
            if (i < bhList.length - 1) {
                stb.append(',');
            }

        }

        connection = DBmanager.getINSTANCE().getConnection();
        reportsParams = new HashMap<>();
        reportsParams.put("listid", stb.toString());

        try {
            //JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/informes/factura.jasper"),parametros, conexion);
            JasperPrint print = JasperFillManager.fillReport("reports/beehive.jasper", reportsParams, connection);
            if (type.equals("pdf")) {

                s = destinationFile.getPath();
                JasperExportManager.exportReportToPdfFile(print, s);
                return s;
            }
            if (type.equals("html")) {
                s = "reports/beehives.html";
                JasperExportManager.exportReportToHtmlFile(print, s);
                return s;
            }

        } catch (JRException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getLicenceText(){
        return "APPiculture\n" +

                "End-User License Agreement (EULA) of APPiculture\n" +
                "This End-User License Agreement (\"EULA\") is a legal agreement between you and Stephane Fabrizio Margini Bonilla\n" +
                "\n" +
                "This EULA agreement governs your acquisition and use of our APPiculture software (\"Software\") directly from Stephane Fabrizio Margini Bonilla or indirectly through a Stephane Fabrizio Margini Bonilla authorized reseller or distributor (a \"Reseller\").\n" +
                "\n" +
                "Please read this EULA agreement carefully before completing the installation process and using the APPiculture software. It provides a license to use the APPiculture software and contains warranty information and liability disclaimers.\n" +
                "\n" +
                "If you register for a free trial of the APPiculture software, this EULA agreement will also govern that trial. By clicking \"accept\" or installing and/or using the APPiculture software, you are confirming your acceptance of the Software and agreeing to become bound by the terms of this EULA agreement.\n" +
                "\n" +
                "If you are entering into this EULA agreement on behalf of a company or other legal entity, you represent that you have the authority to bind such entity and its affiliates to these terms and conditions. If you do not have such authority or if you do not agree with the terms and conditions of this EULA agreement, do not install or use the Software, and you must not accept this EULA agreement.\n" +
                "\n" +
                "This EULA agreement shall apply only to the Software supplied by Stephane Fabrizio Margini Bonilla herewith regardless of whether other software is referred to or described herein. The terms also apply to any Stephane Fabrizio Margini Bonilla updates, supplements, Internet-based services, and support services for the Software, unless other terms accompany those items on delivery. If so, those terms apply.\n" +
                "\n" +
                "License Grant\n" +
                "Stephane Fabrizio Margini Bonilla hereby grants you a personal, non-transferable, non-exclusive licence to use the APPiculture software on your devices in accordance with the terms of this EULA agreement.\n" +
                "\n" +
                "You are permitted to load the APPiculture software (for example a PC, laptop, mobile or tablet) under your control. You are responsible for ensuring your device meets the minimum requirements of the APPiculture software.\n" +
                "\n" +
                "You are not permitted to:\n" +
                "Edit, alter, modify, adapt, translate or otherwise change the whole or any part of the Software nor permit the whole or any part of the Software to be combined with or become incorporated in any other software, nor decompile, disassemble or reverse engineer the Software or attempt to do any such things\n" +
                "Reproduce, copy, resell or otherwise use the Software for any commercial purpose\n" +
                "Allow any third party to use the Software on behalf of or for the benefit of any third party\n" +
                "Use the Software in any way which breaches any applicable local, national or international law\n" +
                "use the Software for any purpose that Stephane Fabrizio Margini Bonilla considers is a breach of this EULA agreement\n" +
                "You are permitted to:\n" +
                "distribute\n" +
                "Intellectual Property and Ownership\n" +
                "Stephane Fabrizio Margini Bonilla shall at all times retain ownership of the Software as originally downloaded by you and all subsequent downloads of the Software by you. The Software (and the copyright, and other intellectual property rights of whatever nature in the Software, including any modifications made thereto) are and shall remain the property of Stephane Fabrizio Margini Bonilla.\n" +
                "\n" +
                "Stephane Fabrizio Margini Bonilla reserves the right to grant licences to use the Software to third parties.\n" +
                "\n" +
                "Termination\n" +
                "This EULA agreement is effective from the date you first use the Software and shall continue until terminated. You may terminate it at any time upon written notice to Stephane Fabrizio Margini Bonilla.\n" +
                "\n" +
                "It will also terminate immediately if you fail to comply with any term of this EULA agreement. Upon such termination, the licenses granted by this EULA agreement will immediately terminate and you agree to stop all access and use of the Software. The provisions that by their nature continue and survive will survive any termination of this EULA agreement.\n" +
                "\n" +
                "Governing Law\n" +
                "This EULA agreement, and any dispute arising out of or in connection with this EULA agreement, shall be governed by and construed in accordance with the laws of SPAIN.";

    }

    public String getAboutUsText(){
        return " Base software es una pequeña empresa formada actualmente por un miembro y situada en Asturias.\n" +
                "Pretende dar soluciones a uno de los sectores mas importantes de nuestra sociedad. La ganadería.\n" +
                "El software pretende ser gratuito con todas sus funciónes desbloqueadas. Pero para poder mantener\n" +
                "el desarrollo, hemos pensado en un modelo basado en la publicidad poco intrusiva que se puede desactivar\n" +
                "con un mínimo pago anual. El software no recolecta datos pribados de ningún tipo, y la publicidad está\n" +
                " gestionada directamente por Google. Este modelo puede cambiar en cualquier momento, aunque siempre \n" +
                "mantendremos nuestra política de privacidad.";
    }

}


