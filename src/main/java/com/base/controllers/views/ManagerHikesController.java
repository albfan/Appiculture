package com.base.controllers.views;

import com.base.controllers.DBmanager;
import com.base.models.Beehives;
import com.base.models.Hikes;
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
    private ObservableList<Hikes> hikesList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tvHikes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }

    public void setSelectedBeehive(Beehives selectedBeehive) {

        this.selectedBeehive = selectedBeehive;
        refreshTableView();

    }

    private void refreshTableView(){

        hikesList= DBmanager.getINSTANCE().getHikes(selectedBeehive);
        tvHikes.setItems(hikesList);

    }

    @FXML
    public void deleteHikes(ActionEvent actionEvent) {

        if (tvHikes.getSelectionModel().getSelectedItems().size() > 0) {

            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle(null);
            confirmation.setHeaderText(null);
            confirmation.setContentText("¿Está seguro de borrar las alzas seleccionadas?");
            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.get() == ButtonType.OK) {

                DBmanager.getINSTANCE().deleteHikesInDB(tvHikes.getSelectionModel().getSelectedItems());
                refreshTableView();

            } else {
                // ... user chose CANCEL or closed the dialog
            }
        }

    }

    @FXML
    public void openFormHikes(ActionEvent actionEvent){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/FormHike.fxml"));
        try {
            Parent root = fxmlLoader.load();
            FormHikeController f = fxmlLoader.getController();
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
                ObservableList<Hikes> modList = tvHikes.getSelectionModel().getSelectedItems();
                if (modList.size() > 1) {
                    alert.setContentText("Seleccione solo una alza.");
                    alert.show();
                } else if (modList.size() == 1) {
                    f.setSelectedHike(modList.get(0));
                    stage.showAndWait();
                } else {
                    alert.setContentText("Seleccione una alza para modificarla.");
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
