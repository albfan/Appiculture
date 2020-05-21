package com.base.controllers.views;

import com.base.models.Alarms;
import com.base.models.structure.BaseController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class FormAlarmController extends BaseController implements Initializable {

    @FXML
    private Button btnValidate;

    @FXML
    private TextField tfName;

    @FXML
    private DatePicker dpDate;

    @FXML
    private TextArea taMessage;

    private Alarms selectedAlarm = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setSelectedAlarm(Alarms selectedAlarm) {
        this.selectedAlarm = selectedAlarm;
        System.out.println(selectedAlarm.getDate().toString());
        dpDate.setValue(selectedAlarm.getDate().toLocalDate());
        tfName.setText(selectedAlarm.getName());
        taMessage.setText(selectedAlarm.getText());
    }
}
