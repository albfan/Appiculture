package com.base.controllers.views;


import com.base.controllers.DBmanager;
import com.base.models.Apiaries;
import com.base.models.Beehives;
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

public class MainController extends BaseController implements Initializable {

    //Apiaries nodes ----------------
    @FXML
    private Button btnAddApiary;
    @FXML
    private Button btnRmvApiary;
    @FXML
    private ListView<Apiaries> apiariesListView;
    @FXML
    private Button btnModApiary;
    //Beehives nodes----------------
    @FXML
    private TableView<Beehives> tvBeehives;
    @FXML
    private Button btnAddHive;
    @FXML
    private Button btnModHive;
    @FXML
    private Button btnDelHive;
    @FXML
    private Button btnShowAllHives;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshApiariesListView();
        refreshHivesTableView();


    }

    //Apiaries methods =======================================================
    private void refreshApiariesListView() {

        apiariesListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        apiariesListView.setItems(DBmanager.getINSTANCE().getApiariesFromDB());

    }

    private void setListenersForApiaryList(){
        apiariesListView.getSelectionModel(). //todo - terminar este metodo
    }

    @FXML
    public void deleteApiaries() {

        if (apiariesListView.getSelectionModel().getSelectedItems().size() > 0) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle(null);
            confirmation.setHeaderText(null);
            confirmation.setContentText("¿Está seguro de borrar los apiarios seleccionados?");
            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.get() == ButtonType.OK) {
                DBmanager.getINSTANCE().deleteApiariesInDB(apiariesListView.getSelectionModel().getSelectedItems());
                refreshApiariesListView();
            } else {
                // ... user chose CANCEL or closed the dialog
            }
        }

    }

    @FXML
    public void openFormApiary(ActionEvent actionEvent) {

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
            if (((Button) actionEvent.getSource()).getId().equalsIgnoreCase("btnModApiary")) {

                //this is to check if user had multiple selection on apiaries list. Only 1 allowed to be modified.
                ObservableList<Apiaries> modList = apiariesListView.getSelectionModel().getSelectedItems();
                if (modList.size() > 1) {
                    alert.setContentText("Solo puede modificar los apiarios de uno en uno");
                    alert.show();
                } else if (modList.size() == 1) {
                    fa.setApiary(modList.get(0));
                    stage.showAndWait();
                }
            } else {
                stage.showAndWait();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        refreshApiariesListView();
    }

    //Beehives methods =================================================================================

    public void refreshHivesTableView() {

        //since multiselection is enabled for delete option we need to use a list.
        ObservableList<Apiaries> templist = apiariesListView.getSelectionModel().getSelectedItems();
        //we only refresh the table when we select 1 apiary
        if (templist.size() == 1) {
            Apiaries ap = templist.get(0);
            tvBeehives.setItems(DBmanager.getINSTANCE().getHivesFromDB(ap));
        }
    }

    @FXML
    public void deleteHives() {

    }

    @FXML
    public void openFormHives(ActionEvent actionEvent) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/base/views/FormBeehives.fxml"));
        try {
            Parent root = fxmlLoader.load();
            FormBeehivesController fb = fxmlLoader.getController();
            Stage stage = new Stage();
            fb.setActualStage(stage);
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.sizeToScene();

//            //this part is used when we will modify an apiary instead creating a new one
//            if( ((Button)actionEvent.getSource()).getId().equalsIgnoreCase("btnModApiary")){
//
//                //this is to check if user had multiple selection on apiaries list. Only 1 allowed to be modified.
//                ObservableList<Apiaries> modList=apiariesListView.getSelectionModel().getSelectedItems();
//                if (modList.size()> 1){
//                    alert.setContentText("Solo puede modificar los apiarios de uno en uno");
//                    alert.show();
//                }else if(modList.size()== 1){
//                    fb.setApiary(modList.get(0));
//                    stage.showAndWait();
//                }
//            }else{
//                stage.showAndWait();
//            }

            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
        refreshHivesTableView();
    }

}
