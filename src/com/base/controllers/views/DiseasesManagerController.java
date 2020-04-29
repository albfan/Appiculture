package com.base.controllers.views;

import com.base.controllers.DBmanager;
import com.base.models.Beehives;
import com.base.models.Diseases;
import com.base.models.structure.BaseController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DiseasesManagerController extends BaseController implements Initializable {

    @FXML
    private Button btnModify;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnClose;

    @FXML
    private Button btnAdd;

    @FXML
    private TableView<Diseases> tvDiseases;

    private ObservableList<Diseases>diseasesList;
    private Beehives selectedBeehive;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshTableView();
    }

    @FXML
    public void openFormDisease(ActionEvent actionEvent){

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/base/views/FormDisease.fxml"));
        try {
            Parent root = fxmlLoader.load();
            FormDiseaseController fd = fxmlLoader.getController();
            Stage stage = new Stage();
            fd.setActualStage(stage);
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.sizeToScene();
            fd.setSelectedBeehive(selectedBeehive);

            if( ((Button)actionEvent.getSource()).getId().equalsIgnoreCase("btnModify") ) {
                //this is to check if user had multiple selection on apiaries list. Only 1 allowed to be modified.
                ObservableList<Diseases> modList = tvDiseases.getSelectionModel().getSelectedItems();
                if (modList.size() > 1) {
                    alert.setContentText("Seleccione solo una enfermedad.");
                    alert.show();
                } else if (modList.size() == 1) {
                    fd.setSelectedDisease(modList.get(0));
                    stage.showAndWait();
                } else {
                    alert.setContentText("Seleccione una enfermedad para modificarla.");
                    alert.show();
                }
            }else{
                stage.showAndWait();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        refreshTableView();
    }

    private void refreshTableView(){

        diseasesList= DBmanager.getINSTANCE().getDiseases(selectedBeehive);
        tvDiseases.setItems(diseasesList);

    }

    public void setSelectedBeehive(Beehives selectedBeehive) {

        this.selectedBeehive = selectedBeehive;

    }
}
