package com.base.controllers.views;

import com.base.models.Apiaries;
import com.base.models.Cores;
import com.base.models.structure.BaseController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class FormCoresController extends BaseController implements Initializable {

    @FXML
    private ComboBox<Apiaries> cbApiaries;

    @FXML
    private DatePicker dpDate;

    @FXML
    private TextField tfBreedFrames;

    @FXML
    private TextArea taNotes;

    private Apiaries selectedApiary=null;
    private Cores selectedCore=null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setApiary(Apiaries apiary) {

        selectedApiary=apiary;

    }

    public void setCore(Cores core) {

        selectedCore = core;

    }
}
