package com.base.controllers.views;

import com.base.controllers.DBmanager;
import com.base.models.Beehives;
import com.base.models.Productions;
import com.base.models.Queens;
import com.base.models.structure.BaseController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;



public class FormProductionsController extends BaseController implements Initializable {

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField tfBreedFrames;

    @FXML
    private TextField tfHoney;

    @FXML
    private TextField tfRoyals;

    @FXML
    private TextField tfPollen;

    @FXML
    private TextField tfRoyalJelly;

    @FXML
    private TextField tfWax;

    @FXML
    private Button btnValidate;

    @FXML
    private Button btnCancel;

    private Beehives selectedBeehive;
    private Productions selectedProduction;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        datePicker.setValue(LocalDate.now());

    }

    public void setSelectedBeehive(Beehives selectedBeehive) {

        this.selectedBeehive = selectedBeehive;

    }

    public void setSelectedProduction(Productions production) {

        selectedProduction = production;
        datePicker.setValue(production.getDate().toLocalDate());
        tfBreedFrames.setText(String.valueOf(production.getBreed_frames_quant()));
        tfHoney.setText(String.valueOf(production.getHoney_quant()));
        tfRoyals.setText(String.valueOf(production.getRoyals_quant()));
        tfPollen.setText(String.valueOf(production.getPollen_quant()));
        tfRoyalJelly.setText(String.valueOf(production.getRoyalJelly_quant()));
        tfWax.setText(String.valueOf(production.getWax_quant()));

    }

    @FXML
    public void validate(ActionEvent actionEvent) {

        String s = "";

//        if (null == cbApiary.getValue()) {
//            s = s + "Debe seleccionar un apiario.\n";
//        }
//        if (null == cbBeehive.getValue()) {
//            s = s + "Debe seleccionar una colmena.\n";
//        }
//        if (null == dpBirthDate.getValue()) {
//            s = s + "Debe introducir la fecha de nacimiento de la reina.\n";
//        }
//
//        if (s.equals("")) {
//
//            try {
//
//                Queens queen = new Queens();
//                queen.setId_apiary(cbApiary.getValue().getId());
//                queen.setId_beehive((cbBeehive.getValue().getNumber()));
//                queen.setBirthdate(Date.valueOf(dpBirthDate.getValue()));
//                if (null != dpDeathDate.getValue()) {
//                    queen.setDeath_date(Date.valueOf(dpDeathDate.getValue()));
//                }
//
//                //if we create a new queen or we modify
//                if (null == selectedQueen) {
//
//                    if (DBmanager.getINSTANCE().checkIfQueenExist(queen)) {
//
//                        alert.setContentText("Esta reina ya existe con esa fecha de nacimiento.");
//                        alert.showAndWait();
//                    } else {
//
//                        DBmanager.getINSTANCE().insertQueenInDB(queen);
//                        actualStage.close();
//                    }
//
//                } else {
//
//                    DBmanager.getINSTANCE().updateQueenInDB(queen,selectedQueen);
//                    actualStage.close();
//
//                }
//
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                s = s + "Error. Recuerde que debe añadir las fechas en el formato correcto\n" +
//                        "y que debe seleccionar correctamente una colmena y apiario";
//                alert.setContentText(s);
//                alert.showAndWait();
//            }
//
//        } else {
//            alert.setContentText(s);
//            alert.showAndWait();
//        }




    }
}
