package com.base.controllers.views;

import com.base.controllers.DBmanager;
import com.base.controllers.OperationManager;
import com.base.models.Beehives;
import com.base.models.Diseases;
import com.base.models.structure.BaseController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;


public class FormDiseaseController extends BaseController implements Initializable {

    @FXML
    private ComboBox<String> cbDisease;

    @FXML
    private TextField tfTreatment;

    @FXML
    private DatePicker dpStartDate;

    @FXML
    private DatePicker dpEndDate;

    @FXML
    private Button btnValidate;

    @FXML
    private Button btnCancel;

    private Beehives selectedBeehive = null;
    private Diseases selectedDisease = null;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        cbDisease.setItems(OperationManager.getInstance().getDiseaseTypes());
        cbDisease.getSelectionModel().selectFirst();

    }

    public void setSelectedDisease(Diseases selectedDisease) {

        this.selectedDisease = selectedDisease;
        cbDisease.getSelectionModel().select(selectedDisease.getDisease());
        tfTreatment.setText(selectedDisease.getTreatment());
        dpStartDate.setValue(selectedDisease.getStartingDate().toLocalDate());
        dpEndDate.setValue(selectedDisease.getEndingDate().toLocalDate());
    }

    public void setSelectedBeehive(Beehives selectedBeehive) {
        this.selectedBeehive = selectedBeehive;
    }

    @FXML
    public void validate(ActionEvent actionEvent) {

        Diseases disease= new Diseases();

        disease.setId_beehive(selectedBeehive.getNumber());
        disease.setId_apiary(selectedBeehive.getId_apiary());
        disease.setDisease(cbDisease.getSelectionModel().getSelectedItem());
        disease.setTreatment(tfTreatment.getText());
        disease.setStartingDate(Date.valueOf(dpStartDate.getValue()));
        disease.setEndingDate(Date.valueOf(dpEndDate.getValue()));

        DBmanager.getINSTANCE().insertDiseaseInDB(disease);

        actualStage.close();
    }

}
