package com.base.controllers.views;

import com.base.controllers.DBmanager;
import com.base.models.Apiaries;
import com.base.models.Beehives;
import com.base.models.Feedings;
import com.base.models.structure.BaseController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

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

    private Beehives selectedBeehive = null;
    private ObservableList<Feedings> feedingsList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tvFeedings.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }

    public void setSelectedBeehive(Beehives beehive) {

        this.selectedBeehive = beehive;
        refreshTableView();

    }

    private void refreshTableView() {

        feedingsList = DBmanager.getINSTANCE().getFeedings(selectedBeehive);

    }


}
