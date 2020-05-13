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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;


import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
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
        cbApiary.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Apiaries>() {
            @Override
            public void changed(ObservableValue<? extends Apiaries> observable, Apiaries oldValue, Apiaries newValue) {

                beehivesList = DBmanager.getINSTANCE().getHivesFromDB(newValue);
                cbBeehive.setItems(beehivesList);

            }
        });
        dpBirthDate.setValue(LocalDate.now());


    }

    public void setSelectedBeehive(Beehives beehive) {

        selectedBeehive = beehive;
        cbApiary.getSelectionModel().select(DBmanager.getINSTANCE().getApiary(beehive.getId_apiary()));
        cbBeehive.getSelectionModel().select(beehive);

    }

    public void setSelectedQueen(Queens queen) {
        selectedQueen = queen;
        cbApiary.getSelectionModel().select(DBmanager.getINSTANCE().getApiary(queen.getId_apiary()));
        cbBeehive.getSelectionModel().select(selectedBeehive);
        dpBirthDate.setValue(queen.getBirthdate().toLocalDate());
        if(null!=queen.getDeath_date()){
            dpDeathDate.setValue(queen.getDeath_date().toLocalDate());
        }
    }


    @FXML
    public void validate(ActionEvent actionEvent) {

        String s = "";

        if (null == cbApiary.getValue()) {
            s = s + "Debe seleccionar un apiario.\n";
        }
        if (null == cbBeehive.getValue()) {
            s = s + "Debe seleccionar una colmena.\n";
        }
        if (null == dpBirthDate.getValue()) {
            s = s + "Debe introducir la fecha de nacimiento de la reina.\n";
        }

        if (s.equals("")) {

            try {

                Queens queen = new Queens();
                queen.setId_apiary(cbApiary.getValue().getId());
                queen.setId_beehive((cbBeehive.getValue().getNumber()));
                queen.setBirthdate(Date.valueOf(dpBirthDate.getValue()));
                if (null != dpDeathDate.getValue()) {
                    queen.setDeath_date(Date.valueOf(dpDeathDate.getValue()));
                }

                //if we create a new queen or we modify
                if (null == selectedQueen) {

                    if (DBmanager.getINSTANCE().checkIfQueenExist(queen)) {

                        alert.setContentText("Esta reina ya existe con esa fecha de nacimiento.");
                        alert.showAndWait();
                    } else {

                        DBmanager.getINSTANCE().insertQueenInDB(queen);
                        actualStage.close();
                    }

                } else {

                    DBmanager.getINSTANCE().updateQueenInDB(queen,selectedQueen);
                    actualStage.close();

                }


            } catch (Exception e) {
                e.printStackTrace();
                s = s + "Error. Recuerde que debe añadir las fechas en el formato correcto\n" +
                        "y que debe seleccionar correctamente una colmena y apiario";
                alert.setContentText(s);
                alert.showAndWait();
            }

        } else {
            alert.setContentText(s);
            alert.showAndWait();
        }


        //todo seguir a partir de aquí. poner validaciones y terminar este método.

    }


}
