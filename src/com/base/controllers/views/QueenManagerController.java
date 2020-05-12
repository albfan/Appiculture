package com.base.controllers.views;

import com.base.models.structure.BaseController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class QueenManagerController extends BaseController implements Initializable {

    @FXML
    private TableView<?> tvQueens;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnMod;

    @FXML
    private Button btnDel;

    @FXML
    private Button btnClose;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
