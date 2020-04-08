package com.base.controllers;

import com.base.models.Apiaries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.time.LocalDate;

public class OperationManager {

    ObservableList<Apiaries> apiariesList;

    private static OperationManager INSTANCE=null;

    //Constructor------------------------------------------
    private OperationManager(){

        apiariesList= FXCollections.observableArrayList();

    }
    //Singleton Method-------------------------------------
    public static OperationManager getInstance(){

        if(INSTANCE==null)
            INSTANCE=new OperationManager();
        return INSTANCE;

    }

    //Methods-----------------------------------------------



}
