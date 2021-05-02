package com.base.controllers.views;

import com.base.controllers.DBmanager;
import com.base.models.Beehives;
import com.base.models.Feedings;
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

public class FormFeedingsController extends BaseController implements Initializable {

    @FXML
    private DatePicker dpFeedDate;

    @FXML
    private TextField tfSolidQuant;

    @FXML
    private TextField tfLiquidQuant;

    @FXML
    private TextField tfFoodType;

    @FXML
    private Button btnValidate;

    @FXML
    private Button btnClose;

    private Beehives selectedBeehive = null;
    private Feedings selectedFeeding = null;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureInputs();
        dpFeedDate.setValue(LocalDate.now());
    }

    public void setSelectedBeehive(Beehives selectedBeehive) {
        this.selectedBeehive = selectedBeehive;
    }

    public void setSelectedFeeding(Feedings selectedFeeding) {
        this.selectedFeeding = selectedFeeding;
        dpFeedDate.setValue(selectedFeeding.getDate().toLocalDate());
        tfSolidQuant.setText(Double.toString(selectedFeeding.getSolid_quant()));
        tfLiquidQuant.setText(Double.toString(selectedFeeding.getLiquid_quant()));
        tfFoodType.setText(selectedFeeding.getFeeding_used());
    }

    public void configureInputs() {
        tfSolidQuant.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d)?")) {
                tfSolidQuant.setText(newValue.replaceAll("[^\\d\\.]", ""));
            }
        });
        tfLiquidQuant.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d)?")) {
                tfLiquidQuant.setText(newValue.replaceAll("[^\\d\\.]", ""));
            }
        });
    }


    @FXML
    private void validate(ActionEvent actionEvent) {//todo -hacer pruebas de validaciones.validar fecha

        //Validations--------------
        String s = "";

        double solid = 0;
        double liquid = 0;

        try {
            if (tfSolidQuant.getText().equals("")) {
                solid = 0;
            } else {
                solid = Double.parseDouble(tfSolidQuant.getText());
            }

            if (tfLiquidQuant.getText().equals("")) {
                liquid = 0;
            } else {
                liquid = Double.parseDouble(tfLiquidQuant.getText());
            }

        } catch (NumberFormatException e) {
            s = s + "Debe introducir las cantidades en numeros y usar solo un punto para decimales. ej: 3.4\n";
        }


        if (s.equals("")) {

            Feedings actualFeeding = new Feedings();

            actualFeeding.setId_beehive(selectedBeehive.getNumber());
            actualFeeding.setId_Apiary(selectedBeehive.getId_apiary());
            actualFeeding.setDate(Date.valueOf(dpFeedDate.getValue()));
            actualFeeding.setSolid_quant(solid);
            actualFeeding.setLiquid_quant(liquid);
            actualFeeding.setFeeding_used(tfFoodType.getText());

            if(null==selectedFeeding){
                DBmanager.getINSTANCE().insertFeedingInDB(actualFeeding);
            }else{
                actualFeeding.setId(selectedFeeding.getId());
                DBmanager.getINSTANCE().updateFeedingInDB(actualFeeding);
            }


        } else {
            alert.setContentText(s);
            alert.show();
        }

        actualStage.close();


    }

}
