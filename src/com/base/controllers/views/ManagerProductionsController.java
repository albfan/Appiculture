package com.base.controllers.views;

import com.base.controllers.DBmanager;
import com.base.models.Beehives;
import com.base.models.Productions;
import com.base.models.structure.BaseController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagerProductionsController extends BaseController implements Initializable {

    @FXML
    private TableView<Productions> tvProductions;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnModify;

    @FXML
    private Button btnDel;

    @FXML
    private Button btnClose;
    private Beehives selectedBeehive;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setSelectedBeehive(Beehives selectedBeehive) {
        this.selectedBeehive = selectedBeehive;
        //refreshTableView();
    }



}
