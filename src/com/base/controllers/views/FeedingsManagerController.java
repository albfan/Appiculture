package com.base.controllers.views;

import com.base.models.structure.BaseController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class FeedingsManagerController extends BaseController implements Initializable {

    @FXML
    private Button btnClose;

    @FXML
    private TableView<?> tvFeedings;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private DatePicker dpFeedDate;

    @FXML
    private Label tfLiquidQuant;

    @FXML
    private TextField tfSolidQuant;

    @FXML
    private TextField tfFoodType;

    @FXML
    private Button btnAdd;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
