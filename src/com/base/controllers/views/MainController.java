package com.base.controllers.views;


import com.base.controllers.DBmanager;
import com.base.models.Apiaries;
import com.base.models.structure.BaseController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends BaseController implements Initializable {

    //Apiaries variables ----------------
    @FXML
    private Button btnAddApiary;
    @FXML
    private Button btnRmvApiary;
    @FXML
    private ListView<Apiaries> apiariesListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshApiariesListView();


    }
    //Apiraires methods =======================================================
    private void refreshApiariesListView(){

        apiariesListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        apiariesListView.setItems(DBmanager.getINSTANCE().getApiariesFromDB());

    }

    @FXML
    public void deleteApiariesListView(){
        DBmanager.getINSTANCE().deleteApiariesInDB(apiariesListView.getSelectionModel().getSelectedItems());
        refreshApiariesListView();
    }

    @FXML
    public void openFormApiary(ActionEvent actionEvent){//todo Arreglar este evento. No lo quiere coger el fxml

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

            //this part is used when we will modify an apiary instead creating a new one
            if( ((Button)actionEvent.getSource()).getId().equalsIgnoreCase("btnModApiary")){

                //this is to check if user had multiple selection on apiaries list. Only 1 allowed to be modified.
                ObservableList<Apiaries> modList=apiariesListView.getSelectionModel().getSelectedItems();
                if (modList.size()!= 1){
                    alert.setContentText("Debe seleccionar solo 1 apiario");
                }else{
                    fa.setApiary(modList.get(0));
                }
            }
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
        refreshApiariesListView();
    }
}
