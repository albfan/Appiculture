package com.base.controllers.views;

import com.base.models.Beehives;
import com.base.models.Hikes;
import com.base.models.structure.BaseController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagerHikesController extends BaseController implements Initializable {

    @FXML
    private TableView<Hikes> tvHikes;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnModify;

    @FXML
    private Button btnDel;

    private Beehives selectedBeehive= null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tvHikes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }

    public void setSelectedBeehive(Beehives selectedBeehive) {

        this.selectedBeehive = selectedBeehive;
        refreshTableView();

    }

    private void refreshTableView(){

    }


}
