package com.base.controllers.views;

import com.base.controllers.DBmanager;
import com.base.models.Beehives;
import com.base.models.Productions;
import com.base.models.structure.BaseController;
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
import java.util.Optional;
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
    private ObservableList<Productions> ProductionsList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tvProductions.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }

    public void setSelectedBeehive(Beehives selectedBeehive) {

        this.selectedBeehive = selectedBeehive;
        refreshTableView();

    }

    private void refreshTableView(){

        ProductionsList=DBmanager.getINSTANCE().getProductions(selectedBeehive);
        tvProductions.setItems(ProductionsList);

    }

    @FXML
    public void deleteProductions(ActionEvent actionEvent){

        if (tvProductions.getSelectionModel().getSelectedItems().size() > 0) {

            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle(null);
            confirmation.setHeaderText(null);
            confirmation.setContentText("¿Está seguro de borrar las producciones seleccionadas?");
            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.get() == ButtonType.OK) {

                DBmanager.getINSTANCE().deleteProductionsInDB(tvProductions.getSelectionModel().getSelectedItems());
                refreshTableView();

            } else {
                // ... user chose CANCEL or closed the dialog
            }
        }

    }

    @FXML
    public void openFormProductions(ActionEvent actionEvent){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/FormProductions.fxml"));
        try {
            Parent root = fxmlLoader.load();
            FormProductionsController f = fxmlLoader.getController();
            Stage stage = new Stage();
            f.setActualStage(stage);
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.sizeToScene();
            f.setSelectedBeehive(selectedBeehive);

            if (((Button) actionEvent.getSource()).getId().equalsIgnoreCase("btnModify")) {
                //this is to check if user had multiple selection on apiaries list. Only 1 allowed to be modified.
                ObservableList<Productions> modList = tvProductions.getSelectionModel().getSelectedItems();
                if (modList.size() > 1) {
                    alert.setContentText("Seleccione solo una reina.");
                    alert.show();
                } else if (modList.size() == 1) {
                    f.setSelectedProduction(modList.get(0));
                    stage.showAndWait();
                } else {
                    alert.setContentText("Seleccione una reina para modificarla.");
                    alert.show();
                }
            } else {
                stage.showAndWait();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        refreshTableView();

    }




}
