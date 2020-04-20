package com.base.controllers;

import com.base.models.Apiaries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.time.LocalDate;

public class OperationManager {

    ObservableList<String> hiveTypes;

    private static OperationManager INSTANCE=null;

    //Constructor------------------------------------------
    private OperationManager(){

        createHiveTypesList();

    }
    //Singleton Method-------------------------------------
    public static OperationManager getInstance(){

        if(INSTANCE==null)
            INSTANCE=new OperationManager();
        return INSTANCE;

    }

    //Methods-----------------------------------------------

    private void createHiveTypesList(){

        hiveTypes= FXCollections.observableArrayList();
        hiveTypes.addAll("Langstroth","Dadant", "Layens","Otro");

    }

    public ObservableList<String> getHiveTypes() {
        return hiveTypes;
    }
}
