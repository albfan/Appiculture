package com.base.controllers.views;

import com.base.controllers.DBmanager;
import com.base.models.Apiaries;
import com.base.models.Beehives;
import com.base.models.Queens;
import com.base.models.structure.BaseController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class FormQueensController extends BaseController implements Initializable {

    @FXML
    private DatePicker dpBirthDate;

    @FXML
    private DatePicker dpDeathDate;

    @FXML
    private Button btnValidate;

    @FXML
    private Button btnCancel;

    @FXML
    private ComboBox<Beehives> cbBeehive;

    @FXML
    private ComboBox<Apiaries> cbApiary;

    private ObservableList<Beehives> beehivesList = FXCollections.observableArrayList();
    private ObservableList<Apiaries> apiariesList = FXCollections.observableArrayList();
    private Beehives selectedBeehive = null;
    private Queens selectedQueen = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        apiariesList = DBmanager.getINSTANCE().getApiariesFromDB();
        cbApiary.setItems(apiariesList);
        //cbApiary.getSelectionModel().select(selectedBeehive.getId_apiary());
        cbApiary.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Apiaries>() {
            @Override
            public void changed(ObservableValue<? extends Apiaries> observable, Apiaries oldValue, Apiaries newValue) {

                beehivesList = DBmanager.getINSTANCE().getHivesFromDB(newValue);
                cbBeehive.setItems(beehivesList);

            }
        });


    }

    public void setSelectedBeehive(Beehives beehive) {

        selectedBeehive = beehive;
        cbApiary.getSelectionModel().select(DBmanager.getINSTANCE().getApiary(beehive.getId_apiary()));
        cbBeehive.getSelectionModel().select(beehive);

    }

    public void setSelectedQueen(Queens queen) {
        selectedQueen=queen;
    }

    private boolean checkIfQueenExist(Queens queen){
        return true;
    }

    @FXML
    public void validate(ActionEvent actionEvent){
        //todo seguir a partir de aquí. poner validaciones y terminar este método.
        //if we create a new queen or we modify
        if(null==selectedQueen){


            Queens queen= new Queens();
            queen.setId_apiary(cbApiary.getValue().getId());
            queen.setId_beehive((cbBeehive.getValue().getNumber()));



        }else{

        }
    }


}
