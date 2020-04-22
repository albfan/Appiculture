package com.base.controllers.views;

import com.base.controllers.DBmanager;
import com.base.controllers.OperationManager;
import com.base.models.Apiaries;
import com.base.models.Beehives;
import com.base.models.structure.BaseController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class FormBeehivesController extends BaseController implements Initializable {

    @FXML
    private TextField tfHiveNum;

    @FXML
    private ComboBox<Apiaries> cbHiveApiary;

    @FXML
    private DatePicker dpHive;

    @FXML
    private ComboBox<String> cbHiveType;

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
        loadHiveTypesComboBox();
        configureInputs();

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

    /**
     * Receive the selected apiary from the main window and select it by default on the form apiary combobox
     * @param ap - the apiary from the listview in the main window controller
     */
    public void setApiary(Apiaries ap){

        cbHiveApiary.getSelectionModel().select(ap);
    }

    private void configureInputs(){
        tfHiveNum.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tfHiveNum.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        cbHiveApiary.requestFocus();

    }

    @FXML
    private void newApiaryForm(ActionEvent actionEvent) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/base/views/FormApiary.fxml"));
        try {

            Parent root = fxmlLoader.load();
            FormApiaryController fa = fxmlLoader.getController();
            Stage stage = new Stage();
            fa.setActualStage(stage);
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.sizeToScene();
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }

        refreshApiariesComboBox();

    }

    private void loadHiveTypesComboBox(){
        cbHiveType.setItems(OperationManager.getInstance().getHiveTypes());
        cbHiveType.getSelectionModel().selectFirst();
    }

    //this method check on the database if the beehives number is already used in that apiary
    private boolean verifyNumberIsUsed() {//todo hacer este método y el que está abajo de validar

        return DBmanager.getINSTANCE().beehiveExist(Integer.parseInt(tfHiveNum.getText()),cbHiveApiary.getSelectionModel().getSelectedItem().getId());
    }

    @FXML
    @Override
    public void validate() {//todo hacer pruebas de validaciones

        Apiaries apiary= cbHiveApiary.getSelectionModel().getSelectedItem();
        int number = Integer.parseInt(tfHiveNum.getText());
        //this date is to verify the datepicker value
        LocalDate localDate=dpHive.getValue();
        //this date is for the beehive
        Date date = null;
        String type = null;
        boolean favorite = false;
        String s = "";

        //we check if apiary is not null
        if (null == apiary ) {
            s = s + "-Debe elegir un apiario.\n";
        } else {
            //we check if the number is already used in that apiary
            if (verifyNumberIsUsed()) {
                s = s + "-Este número ya existe en este apiario." +
                        "Elija otro número o cambie de apiario.\n";
            } else {

                //we verify the something is selected in the datepicker. by default set to current date
                if ( null==localDate){
                    date= new Date(System.currentTimeMillis());
                }else{
                    date = Date.valueOf(dpHive.getValue());
                }

                type=cbHiveType.getValue();

                favorite=cbFavorite.isSelected();

            }
        }

        if(!s.equals("")){

            alert.setContentText(s);
            alert.show();

        }else{

            Beehives beehive= new Beehives();
            beehive.setId_apiary(apiary.getId());
            beehive.setNumber(number);
            beehive.setDate(date);
            beehive.setType(type);
            beehive.setFavorite(favorite);

            DBmanager.getINSTANCE().insertBeehiveInDB(beehive);
            actualStage.close();
        }
    }

    public void setBeehive(Beehives beehive) {//todo - no permite modificar si ya existe. y como estamos modificando, ya existe.

        cbHiveApiary.setValue(DBmanager.getINSTANCE().getApiary(beehive.getId_apiary()));
        tfHiveNum.setText(""+beehive.getNumber());
        dpHive.setValue(beehive.getDate().toLocalDate());
        cbHiveType.getSelectionModel().select(beehive.getType());

    }


}
