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
        tfBreedFrames.setText("0");
        tfHoney.setText("0.0");
        tfRoyals.setText("0");
        tfPollen.setText("0.0");
        tfRoyalJelly.setText("0.0");
        tfWax.setText("0.0");

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

                if (null == datePicker.getValue()) {
            s = s + "Debe introducir la fecha de la producción.\n";
        }

        if (s.equals("")) {

            try {

                Productions production = new Productions();
                production.setId_beehive((selectedBeehive.getNumber()));
                production.setId_apiary(selectedBeehive.getId_apiary());
                production.setDate(Date.valueOf(datePicker.getValue()));
                production.setBreed_frames_quant(Integer.parseInt(tfBreedFrames.getText()));
                production.setHoney_quant(Double.parseDouble(tfHoney.getText()));
                production.setRoyals_quant(Integer.parseInt(tfRoyals.getText()));
                production.setPollen_quant(Double.parseDouble(tfPollen.getText()));
                production.setWax_quant(Double.parseDouble(tfWax.getText()));
                production.setRoyalJelly_quant(Double.parseDouble(tfRoyalJelly.getText()));

                //if we create a new queen or we modify
                if (null == selectedProduction) {

                        DBmanager.getINSTANCE().insertProductionInDB(production);
                        actualStage.close();


                } else {

                    DBmanager.getINSTANCE().updateProductionInDB(production,selectedProduction);
                    actualStage.close();

                }


            } catch (Exception e) {
                e.printStackTrace();
                s = s + "Error. Recuerde que debe añadir la fechas en el formato correcto\n" +
                        "y que debe introducir solo números en los campos de texto.\n" +
                        "Los decimales deben ir separadas por puntos.";
                alert.setContentText(s);
                alert.showAndWait();
            }

        } else {
            alert.setContentText(s);
            alert.showAndWait();
        }




    }
}
