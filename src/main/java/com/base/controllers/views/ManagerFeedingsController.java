package com.base.controllers.views;

import com.base.controllers.DBmanager;
import com.base.models.Apiaries;
import com.base.models.Beehives;
import com.base.models.Diseases;
import com.base.models.Feedings;
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

public class ManagerFeedingsController extends BaseController implements Initializable {

    @FXML
    private Button btnClose;

    @FXML
    private TableView<Feedings> tvFeedings;

    @FXML
    private Button btnModify;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnAdd;

    private Beehives selectedBeehive = null;
    private ObservableList<Feedings> feedingsList = null;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tvFeedings.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        refreshTableView();

    }

    public void setSelectedBeehive(Beehives beehive) {

        this.selectedBeehive = beehive;
        refreshTableView();

    }

    private void refreshTableView() {

        feedingsList = DBmanager.getINSTANCE().getFeedings(selectedBeehive);
        tvFeedings.setItems(feedingsList);

    }

    @FXML
    public void openFormFeedings(ActionEvent actionEvent) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/base/views/FormFeedings.fxml"));
        try {
            Parent root = fxmlLoader.load();
            FormFeedingsController ff = fxmlLoader.getController();
            Stage stage = new Stage();
            ff.setActualStage(stage);
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.sizeToScene();
            ff.setSelectedBeehive(selectedBeehive);

            if (((Button) actionEvent.getSource()).getId().equalsIgnoreCase("btnModify")) {
                //this is to check if user had multiple selection on apiaries list. Only 1 allowed to be modified.
                ObservableList<Feedings> modList = tvFeedings.getSelectionModel().getSelectedItems();
                if (modList.size() > 1) {
                    alert.setContentText("Seleccione solo una alimentación.");
                    alert.show();
                } else if (modList.size() == 1) {
                    ff.setSelectedFeeding(modList.get(0));
                    stage.showAndWait();
                } else {
                    alert.setContentText("Seleccione una alimentación para modificarla.");
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

    @FXML
    public void deleteFeedings(ActionEvent actionEvent){
        if (tvFeedings.getSelectionModel().getSelectedItems().size() > 0) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle(null);
            confirmation.setHeaderText(null);
            confirmation.setContentText("¿Está seguro de borrar las alimentaciones seleccionadas?");
            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.get() == ButtonType.OK) {
                DBmanager.getINSTANCE().deleteFeedingsInDB(tvFeedings.getSelectionModel().getSelectedItems());
                refreshTableView();
            } else {
                // ... user chose CANCEL or closed the dialog
            }
        }
    }


}
