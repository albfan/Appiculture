package com.base.controllers.views;

import com.base.models.structure.BaseController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class FormProductionsController extends BaseController implements Initializable {

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
