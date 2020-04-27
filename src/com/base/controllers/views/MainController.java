package com.base.controllers.views;


import com.base.controllers.DBmanager;
import com.base.models.Apiaries;
import com.base.models.Beehives;
import com.base.models.structure.BaseController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
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
    private ListView<Apiaries> lvApiaries;
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
    private Button btnDeseases;
    @FXML
    private Button btnDelHive;
    @FXML
    private Button btnShowAllHives;

    //this is the apiary actually selected in listview
    Apiaries currentApiarySelected = null;
    //this is the beehive actually selected in tableview
    Beehives currentBeehiveSelected = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initialApiaryConfig();
        initialBeehivesConfig();

    }

    //Apiaries methods =======================================================

    /**
     * this method configure the selection mode from apiaries list view and also select the first item
     * at initialization.
     */
    private void initialApiaryConfig() {

        lvApiaries.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        setListenersForApiaryList();
        refreshApiariesListView();
        ObservableList<Apiaries> list = DBmanager.getINSTANCE().getApiariesFromDB();
        if (list.size() > 0) {

            lvApiaries.getSelectionModel().select(list.get(0));
            currentApiarySelected = list.get(0);

        }

    }

    private void refreshApiariesListView() {

        lvApiaries.setItems(DBmanager.getINSTANCE().getApiariesFromDB());

    }

    private void setListenersForApiaryList() {//todo - revisar más adelante este método para que se active solo al seleccionar un apiario.

//        lvApiaries.onMouseClickedProperty().addListener((observable, oldValue, newValue) -> {
//            currentApiarySelected= lvApiaries.getSelectionModel().getSelectedItem();
//            refreshHivesTableView();
//        });

        lvApiaries.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Apiaries>() {
            @Override
            public void changed(ObservableValue<? extends Apiaries> observable, Apiaries oldValue, Apiaries newValue) {

                if (newValue != null) {
                    currentApiarySelected = newValue;
                    refreshHivesTableView();
                }
            }
        });
    }

    @FXML
    public void deleteApiaries() {

        if (lvApiaries.getSelectionModel().getSelectedItems().size() > 0) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle(null);
            confirmation.setHeaderText(null);
            confirmation.setContentText("¿Está seguro de borrar los apiarios seleccionados?");
            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.get() == ButtonType.OK) {
                DBmanager.getINSTANCE().deleteApiariesInDB(lvApiaries.getSelectionModel().getSelectedItems());
                refreshApiariesListView();
            } else {
                // ... user chose CANCEL or closed the dialog
            }
        }

    }

    /**
     * This method opens a form for creating a new Apiary or to modify an existing one
     *
     * @param actionEvent
     */
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
                ObservableList<Apiaries> modList = lvApiaries.getSelectionModel().getSelectedItems();
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

    private void initialBeehivesConfig() {

        tvBeehives.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        setListenersForBeehivesTableView();
        refreshHivesTableView();
        ObservableList<Apiaries> list = DBmanager.getINSTANCE().getApiariesFromDB();
        if (list.size() > 0) {

            lvApiaries.getSelectionModel().select(list.get(0));
            currentApiarySelected = list.get(0);

        }

    }

    private void setListenersForBeehivesTableView() {

//        lvApiaries.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Apiaries>() {
//            @Override
//            public void changed(ObservableValue<? extends Apiaries> observable, Apiaries oldValue, Apiaries newValue) {
//
//                if(newValue!=null) {
//                    currentApiarySelected = newValue;
//                }
//                refreshHivesTableView();
//
//            }
//        });

        tvBeehives.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Beehives>() {
            @Override
            public void changed(ObservableValue<? extends Beehives> observable, Beehives oldValue, Beehives newValue) {

                if (newValue != null) {
                    currentBeehiveSelected = newValue;
                }
            }
        });

    }

    public void refreshHivesTableView() {

        //since multiselection is enabled for delete option we need to use a list to check if
        //only one item is selected
        ObservableList<Apiaries> templist = lvApiaries.getSelectionModel().getSelectedItems();
        //we only refresh the table when we select 1 apiary
        if (templist.size() <= 1) {

            tvBeehives.setItems(DBmanager.getINSTANCE().getHivesFromDB(currentApiarySelected));
        }
    }

    @FXML
    public void deleteHives() {

        if (tvBeehives.getSelectionModel().getSelectedItems().size() > 0) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle(null);
            confirmation.setHeaderText(null);
            confirmation.setContentText("¿Está seguro de borrar las colmenas seleccionadas?");
            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.get() == ButtonType.OK) {
                DBmanager.getINSTANCE().deleteApiariesInDB(lvApiaries.getSelectionModel().getSelectedItems());
                DBmanager.getINSTANCE().deleteBeehivesInDB(tvBeehives.getSelectionModel().getSelectedItems());
                refreshHivesTableView();
            } else {
                // ... user chose CANCEL or closed the dialog
            }
        }

    }

    /**
     * Opens a new from window to create or modify a beehive
     *
     * @param actionEvent
     */
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
            fb.setApiary(currentApiarySelected);


            //this part is used when we modify a beehive instead creating a new one
            if (((Button) actionEvent.getSource()).getId().equalsIgnoreCase("btnModHive")) {

                //this is to check if user had multiple selection on beehives tableview. Only 1 allowed to be modified.
                ObservableList<Beehives> modList = tvBeehives.getSelectionModel().getSelectedItems();
                if (modList.size() > 1) {
                    alert.setContentText("Solo puede modificar las colmenas de una en una");
                    alert.show();
                } else if (modList.size() == 1) {
                    fb.setBeehive(currentBeehiveSelected);
                    stage.showAndWait();
                }
            } else {
                stage.showAndWait();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        refreshHivesTableView();
    }

    @FXML
    public void openFormDiseases(ActionEvent actionEvent) {


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/base/views/FormDiseases.fxml"));
        try {
            Parent root = fxmlLoader.load();
            FormDiseasesController fd = fxmlLoader.getController();
            Stage stage = new Stage();
            fd.setActualStage(stage);
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.sizeToScene();

            //this part is used when we will modify an apiary instead creating a new one
            if (((Button) actionEvent.getSource()).getId().equalsIgnoreCase("btnDeseases")) {

                //this is to check if user had multiple selection on apiaries list. Only 1 allowed to be modified.
                ObservableList<Apiaries> modList = lvApiaries.getSelectionModel().getSelectedItems();
                if (modList.size() > 1) {
                    alert.setContentText("Seleccione una sola colmena.");
                    alert.show();
                } else if (modList.size() == 1) {
                    //fd.setApiary(modList.get(0));
                    stage.showAndWait();
                }
            } else {
                stage.showAndWait();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
