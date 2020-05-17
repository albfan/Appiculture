package com.base.controllers.views;

import com.base.models.structure.BaseController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import java.net.URL;
import java.util.ResourceBundle;

public class FormHikeController extends BaseController implements Initializable {

    @FXML
    private DatePicker dpPlacement;

    @FXML
    private ComboBox<?> cbApiary;

    @FXML
    private ComboBox<?> cbBeehive;

    @FXML
    private ComboBox<?> cbType;

    @FXML
    private DatePicker cpWithdrawal;

    @FXML
    private Button btnValidate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
