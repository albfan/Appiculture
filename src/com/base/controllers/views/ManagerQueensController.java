package com.base.controllers.views;

import com.base.controllers.DBmanager;
import com.base.models.Apiaries;
import com.base.models.Beehives;
import com.base.models.Diseases;
import com.base.models.Queens;
import com.base.models.structure.BaseController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManagerQueensController extends BaseController implements Initializable {

    @FXML
    private TableView<Queens> tvQueens;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnMod;

    @FXML
    private Button btnDel;

    @FXML
    private Button btnClose;

    private Beehives selectedBeehive = null;
    private ObservableList<Queens> QueensList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tvQueens.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }

    @FXML
    public void openFormQueens(ActionEvent actionEvent){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/base/views/FormQueens.fxml"));
        try {
            Parent root = fxmlLoader.load();
            FormQueensController f = fxmlLoader.getController();
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
                ObservableList<Queens> modList = tvQueens.getSelectionModel().getSelectedItems();
                if (modList.size() > 1) {
                    alert.setContentText("Seleccione solo una reina.");
                    alert.show();
                } else if (modList.size() == 1) {
                    f.setSelectedQueen(modList.get(0));
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

    private void refreshTableView() {

        QueensList = DBmanager.getINSTANCE().getQueens(selectedBeehive);
        tvQueens.setItems(QueensList);

    }

    public void setSelectedBeehive(Beehives selectedBeehive) {
        this.selectedBeehive = selectedBeehive;
        refreshTableView();
    }

    @FXML
    public void deleteQueens(ActionEvent actionEvent) {

    }
}
