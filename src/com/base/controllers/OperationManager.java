package com.base.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Calendar;

public class OperationManager {

    ObservableList<String> hiveTypes;
    ObservableList<String> diseaseTypes;

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

    }

    public float millisToYears(Long millis){

        float years=( ( ( (millis/1000)/60 )/60 )/24 )/365 ;

        return years;
    }


    public ObservableList<String> getHiveTypes() {
        return hiveTypes;
    }

    public ObservableList<String> getDiseaseTypes() {
        return diseaseTypes;
    }
}
