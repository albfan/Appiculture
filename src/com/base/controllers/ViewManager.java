package com.base.controllers;

import com.base.models.Apiaries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ViewManager {

    ObservableList<Apiaries> apiariesList;

    private static  ViewManager INSTANCE=null;

    //Constructor------------------------------------------
    private ViewManager(){

        apiariesList= FXCollections.observableArrayList();

    }
    //Singleton Method-------------------------------------
    public static ViewManager getInstance(){

        if(INSTANCE==null)
            INSTANCE=new ViewManager();
        return INSTANCE;

    }

    //Methods-----------------------------------------------
    //Apiaries----------------------------------------------


}
