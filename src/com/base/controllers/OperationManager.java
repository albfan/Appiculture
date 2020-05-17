package com.base.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.text.DecimalFormat;


public class OperationManager {

    private ObservableList<String> hiveTypes;
    private ObservableList<String> diseaseTypes;
    private ObservableList<String> hikesTypes;

    private static OperationManager INSTANCE=null;

    //Constructor------------------------------------------
    private OperationManager(){

        createLists();

    }
    //Singleton Method-------------------------------------
    public static OperationManager getInstance(){

        if(INSTANCE==null)
            INSTANCE=new OperationManager();
        return INSTANCE;

    }

    //Methods-----------------------------------------------

    private void createLists(){

        hiveTypes= FXCollections.observableArrayList();
        hiveTypes.addAll("Langstroth","Dadant", "Layens","Otro");

        diseaseTypes=FXCollections.observableArrayList();
        diseaseTypes.addAll("Varroosis","Acarapisosis","Loque americana","Loque europea"
                ,"Escarabajo (Aethina tumida)","Ácaro Tropilaelaps","IAPV","Otro");

        hiveTypes=FXCollections.observableArrayList();
        hiveTypes.addAll("Alza, Media alza");

    }

    public float millisToYears(Long millis){

        DecimalFormat df=new DecimalFormat("0.00");
        float years=(float)( ( ( (millis/1000)/60 )/60 )/24 )/365 ;
        float result =(float)Math.round(years *10)/10;
        return result;
    }


    public ObservableList<String> getHiveTypes() {
        return hiveTypes;
    }

    public ObservableList<String> getDiseaseTypes() {
        return diseaseTypes;
    }

    public ObservableList<String> getHikesTypes() {
        return hikesTypes;
    }
}
