package com.base.controllers.views;

import com.base.controllers.DBmanager;
import com.base.models.Apiaries;
import com.base.models.structure.BaseController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class FormBeehivesController extends BaseController implements Initializable {

    @FXML
    private TextField tfHiveNum;

    @FXML
    private ComboBox<Apiaries> cbHiveApiary;

    @FXML
    private DatePicker dcHive;

    @FXML
    private ComboBox<?> cbHiveType;

    @FXML
    private CheckBox cbFavorite;

    @FXML
    private Button btnNewApiary;

    @FXML
    private Button btnOk;

    @FXML
    private Button btnCancel;

    private ObservableList<Apiaries> apiariesList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        refreshApiariesComboBox();

    }

    private void refreshApiariesComboBox() {

        apiariesList = DBmanager.getINSTANCE().getApiariesFromDB();
        //if list is not empty
        if (apiariesList.size() > 0) {
            cbHiveApiary.setItems(apiariesList);
            //if nothing is selected
            if (null == cbHiveApiary.getSelectionModel().getSelectedItem()) {
                cbHiveApiary.getSelectionModel().select(0);
            }
        }
    }

    //this method check on the database if the beehives number is already used in that apiary
    private boolean verifyNumberIsUsed(){
        //todo hacer este método y el que está abajo de validar


        return false;
    }

    @FXML
    @Override
    public void validate() {

        String s="";
        //we check if apiary is not null
        if(null==cbHiveApiary.getSelectionModel().getSelectedItem()){
            s=s+"-Debe elegir un apiario.\n";
        }else{
            //we check if the number is already used in that apiary
            if(verifyNumberIsUsed() ){
                s=s+"-Este numero ya existe en este apiario." +
                        "Elija otro número o cambie de apiario.\n";
            }else{


            }
        }

    }
}
