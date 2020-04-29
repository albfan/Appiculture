package com.base.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
                ,"Escarabajo (Aethina tumida)","Ácaro Tropilaelaps","Otro");

    }


    public ObservableList<String> getHiveTypes() {
        return hiveTypes;
    }

    public ObservableList<String> getDiseaseTypes() {
        return diseaseTypes;
    }
}
