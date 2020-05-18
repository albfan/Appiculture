package com.base.controllers.views;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
