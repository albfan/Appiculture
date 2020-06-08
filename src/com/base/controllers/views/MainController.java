package com.base.controllers.views;


import com.base.controllers.DBmanager;
import com.base.controllers.OperationManager;
import com.base.models.Alarms;
import com.base.models.Apiaries;
import com.base.models.Beehives;
import com.base.models.Cores;
import com.base.models.structure.BaseController;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class MainController extends BaseController implements Initializable {

    //Apiaries nodes ----------------
    @FXML
    private ListView<Apiaries> lvApiaries;

    @FXML
    private Button btnAddApiary;

    @FXML
    private Button btnModApiary;

    @FXML
    private TableView<Beehives> tvBeehives;

    @FXML
    private Button btnAddHive;

    @FXML
    private Button btnModHive;

    @FXML
    private TableView<Cores> tvCores;

    @FXML
    private Button btnAddCores;

    @FXML
    private Button btnModCores;

    @FXML
    private Button btnAddAlarm;

    @FXML
    private Button btnModAlarm;

    @FXML
    private TableView<Alarms> tvAlarms;
    @FXML
    private WebView webview;

    private ObservableList<Beehives> beehivesList;
    private Timer timer;
    private TimerTask timerTask;
    private ObservableList<Alarms> alarmList;
    private ObservableList<Apiaries> selectedApiariesList;
    private ObservableList<Beehives> selectedBeehivesList;
    //this variable is to store what kind of report you want to print
    // 1 is for apiaries and 2 for beehives.
    private int typePrintReport = 0;


    //this is the apiary actually selected in listview
    Apiaries currentApiarySelected = null;
    //this is the beehive actually selected in tableview
    Beehives currentBeehiveSelected = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initialApiaryConfig();
        initialBeehivesConfig();
        initialCoresConfig();
        initialAlarmsConfig();
        checkAndStartAlarms();

    }

    //Menu methods ===========================================================

    @FXML
    public void importDB() {
        DBmanager.getINSTANCE().importDB(actualStage);
        refreshApiariesListView();
        lvApiaries.getSelectionModel().selectFirst();
        refreshBeehivesTableView();
        refreshAlarmlist();
        refreshCoresTableView();
        refreshWebview();
    }

    @FXML
    public void exportDB() {
        DBmanager.getINSTANCE().exportDB(actualStage);
    }


    //Apiaries methods =======================================================

    /**
     * this method configure the selection mode from apiaries list view and also select the first item
     * at initialization.
     */
    private void initialApiaryConfig() {

        lvApiaries.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        selectedApiariesList = FXCollections.observableArrayList();
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

                    typePrintReport=1;
                    //this is to save the selected apiary for the form windows.
                    currentApiarySelected = newValue;
                    //this is to save the selected apiaries for reports and webview.
                    selectedApiariesList = lvApiaries.getSelectionModel().getSelectedItems();
                    refreshWebview();
                    //get from database all beehives from the current selected apiary
                    setBeehivesList();
                    //refresh beehive tableview using the beehiveList recently updated.
                    refreshBeehivesTableView();
                    refreshCoresTableView();


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


                if (lvApiaries.getItems().size() < 1 || null == lvApiaries.getItems()) {
                    setBeehivesList();
                    refreshBeehivesTableView();
                } else {
                    lvApiaries.getSelectionModel().selectFirst();
                }
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

    /**
     * update the beehiveList with the newest data from database. this method is mainly called when user do a CRUD
     * query to the database.
     */
    private void setBeehivesList() {

        beehivesList = FXCollections.observableArrayList(DBmanager.getINSTANCE().getBeehivesFromDB(currentApiarySelected));

    }

    private void initialBeehivesConfig() {
        
        selectedBeehivesList= FXCollections.observableArrayList();
        tvBeehives.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        setListenersForBeehivesTableView();
        refreshBeehivesTableView();
        ObservableList<Apiaries> list = DBmanager.getINSTANCE().getApiariesFromDB();
        if (list.size() > 0) {

            lvApiaries.getSelectionModel().select(list.get(0));
            currentApiarySelected = list.get(0);

        }

    }

    /**
     * everytime you select a beehive in the table view, it got stored on the variable "currentBeehiveSelected".
     */
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

                    typePrintReport = 2;
                    currentBeehiveSelected = newValue;
                    selectedBeehivesList= tvBeehives.getSelectionModel().getSelectedItems();
                    refreshWebview();
                }
            }
        });

    }

    /**
     * refresh the table view.
     */
    public void refreshBeehivesTableView() {

            tvBeehives.setItems(beehivesList);
    }

    @FXML
    public void deleteBeehives() {

        if (tvBeehives.getSelectionModel().getSelectedItems().size() > 0) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle(null);
            confirmation.setHeaderText(null);
            confirmation.setContentText("¿Está seguro de borrar las colmenas seleccionadas?");
            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.get() == ButtonType.OK) {//todo- tenía lo de borrar apiarrios aquí. Lo he comentado pero hay que verificar si lo tenía por algo o si fué un error y se me olvido borrarlo despues de copiar y pegar.
                //DBmanager.getINSTANCE().deleteApiariesInDB(lvApiaries.getSelectionModel().getSelectedItems());
                DBmanager.getINSTANCE().deleteBeehivesInDB(tvBeehives.getSelectionModel().getSelectedItems());
                setBeehivesList();
                refreshBeehivesTableView();
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

            //this is used when we modify a beehive instead creating a new one
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
        setBeehivesList();
        refreshBeehivesTableView();
    }

    @FXML
    public void openDiseasesManager(ActionEvent actionEvent) {


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/base/views/ManagerDiseases.fxml"));
        try {
            Parent root = fxmlLoader.load();
            ManagerDiseasesController dm = fxmlLoader.getController();
            Stage stage = new Stage();
            dm.setActualStage(stage);
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.sizeToScene();

            //this is to check if user had multiple selection on beehives tableview. Only 1 allowed to be modified.
            ObservableList<Beehives> modList = tvBeehives.getSelectionModel().getSelectedItems();
            if (modList.size() > 1) {
                alert.setContentText("Seleccione solo una colmena.");
                alert.show();
            } else if (modList.size() == 1) {
                dm.setSelectedBeehive(modList.get(0));
                stage.showAndWait();
            } else {
                alert.setContentText("Seleccione una colmena.");
                alert.show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        setBeehivesList();
        refreshBeehivesTableView();
    }

    @FXML
    private void openFeedingsManager(ActionEvent actionEvent) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/base/views/ManagerFeedings.fxml"));
        try {
            Parent root = fxmlLoader.load();
            ManagerFeedingsController fm = fxmlLoader.getController();
            Stage stage = new Stage();
            fm.setActualStage(stage);
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.sizeToScene();

            //this is to check if user had multiple selection on beehives tableview. Only 1 allowed to be modified.
            ObservableList<Beehives> modList = tvBeehives.getSelectionModel().getSelectedItems();
            if (modList.size() > 1) {
                alert.setContentText("Seleccione solo una colmena.");
                alert.show();
            } else if (modList.size() == 1) {
                fm.setSelectedBeehive(modList.get(0));
                stage.showAndWait();
            } else {
                alert.setContentText("Seleccione una colmena.");
                alert.show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        refreshBeehivesTableView();
    }

    @FXML
    public void openQueensManager(ActionEvent actionEvent) {


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/base/views/ManagerQueens.fxml"));
        try {
            Parent root = fxmlLoader.load();
            ManagerQueensController qm = fxmlLoader.getController();
            Stage stage = new Stage();
            qm.setActualStage(stage);
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.sizeToScene();

            //this is to check if user had multiple selection on beehiveives tableview. Only 1 allowed to be modified.
            ObservableList<Beehives> modList = tvBeehives.getSelectionModel().getSelectedItems();
            if (modList.size() > 1) {
                alert.setContentText("Seleccione solo una colmena.");
                alert.show();
            } else if (modList.size() == 1) {
                qm.setSelectedBeehive(modList.get(0));
                stage.showAndWait();
            } else {
                alert.setContentText("Seleccione una colmena.");
                alert.show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        refreshBeehivesTableView();
    }

    @FXML
    public void openProductionsManager(ActionEvent actionEvent) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/base/views/ManagerProductions.fxml"));
        try {
            Parent root = fxmlLoader.load();
            ManagerProductionsController mp = fxmlLoader.getController();
            Stage stage = new Stage();
            mp.setActualStage(stage);
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.sizeToScene();

            //this is to check if user had multiple selection on beehiveives tableview. Only 1 allowed to be modified.
            ObservableList<Beehives> modList = tvBeehives.getSelectionModel().getSelectedItems();
            if (modList.size() > 1) {
                alert.setContentText("Seleccione solo una colmena.");
                alert.show();
            } else if (modList.size() == 1) {
                mp.setSelectedBeehive(modList.get(0));
                stage.showAndWait();
            } else {
                alert.setContentText("Seleccione una colmena.");
                alert.show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        refreshBeehivesTableView();
    }

    @FXML
    public void openHikesManager(ActionEvent actionEvent) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/base/views/ManagerHikes.fxml"));
        try {
            Parent root = fxmlLoader.load();
            ManagerHikesController mh = fxmlLoader.getController();
            Stage stage = new Stage();
            mh.setActualStage(stage);
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.sizeToScene();

            //this is to check if user had multiple selection on beehiveives tableview. Only 1 allowed to be modified.
            ObservableList<Beehives> modList = tvBeehives.getSelectionModel().getSelectedItems();
            if (modList.size() > 1) {
                alert.setContentText("Seleccione solo una colmena.");
                alert.show();
            } else if (modList.size() == 1) {
                mh.setSelectedBeehive(modList.get(0));
                stage.showAndWait();
            } else {
                alert.setContentText("Seleccione una alza.");
                alert.show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        refreshBeehivesTableView();
    }

    // CORES =============================================================================

    private void initialCoresConfig() {

        tvCores.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }

    @FXML
    public void openCoresForm(ActionEvent actionEvent) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/base/views/FormCores.fxml"));

        try {
            Parent root = fxmlLoader.load();
            FormCoresController fc = fxmlLoader.getController();
            Stage stage = new Stage();
            fc.setActualStage(stage);
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.sizeToScene();
            fc.setApiary(currentApiarySelected);


            //this is used when we modify a beehive instead creating a new one
            if (((Button) actionEvent.getSource()).getId().equalsIgnoreCase("btnModCores")) {

                //this is to check if user had multiple selection on beehives tableview. Only 1 allowed to be modified.
                ObservableList<Cores> modList = tvCores.getSelectionModel().getSelectedItems();
                if (modList.size() > 1) {
                    alert.setContentText("Solo puede modificar los nucelos de uno en uno");
                    alert.show();
                } else if (modList.size() == 1) {
                    fc.setCore(tvCores.getSelectionModel().getSelectedItem());
                    stage.showAndWait();
                }
            } else {
                stage.showAndWait();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        refreshCoresTableView();


    }

    private void refreshCoresTableView() {

            tvCores.setItems(DBmanager.getINSTANCE().getCores(currentApiarySelected));
    }

    @FXML
    public void deleteCores(ActionEvent actionEvent) {

        if (tvCores.getSelectionModel().getSelectedItems().size() > 0) {

            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle(null);
            confirmation.setHeaderText(null);
            confirmation.setContentText("¿Está seguro de borrar los nucleos seleccionados?");
            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.get() == ButtonType.OK) {

                DBmanager.getINSTANCE().deleteCoresInDB(tvCores.getSelectionModel().getSelectedItems());
                refreshCoresTableView();

            } else {
                // ... user chose CANCEL or closed the dialog
            }
        }

    }

    //ALARMS=========================================================

    private void initialAlarmsConfig() {

        tvAlarms.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        refreshAlarmListView();

    }

    /**
     * this method retreive and updated list of alarm from database and create a copied list.
     */
    private void refreshAlarmlist() {

        alarmList = FXCollections.observableArrayList(DBmanager.getINSTANCE().getAlarms());

    }

    /**
     * This method refresh the ListView of alarms
     */
    public void refreshAlarmListView() {
        refreshAlarmlist();
        tvAlarms.setItems(alarmList);

    }

    @FXML
    public void openFormAlarm(ActionEvent actionEvent) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/base/views/FormAlarm.fxml"));

        try {
            Parent root = fxmlLoader.load();
            FormAlarmController fa = fxmlLoader.getController();
            Stage stage = new Stage();
            fa.setActualStage(stage);
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.sizeToScene();

            //this is used when we modify an alarm instead creating a new one
            if (((Button) actionEvent.getSource()).getId().equalsIgnoreCase("btnModAlarm")) {

                //this is to check if user had multiple selection on beehives tableview. Only 1 allowed to be modified.
                ObservableList<Alarms> modList = tvAlarms.getSelectionModel().getSelectedItems();
                if (modList.size() > 1) {
                    alert.setContentText("Solo puede modificar las alarmas de una en una");
                    alert.show();
                } else if (modList.size() == 1) {
                    fa.setSelectedAlarm(tvAlarms.getSelectionModel().getSelectedItem());
                    stage.showAndWait();
                }
            } else {
                stage.showAndWait();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        refreshAlarmListView();


    }

    @FXML
    public void deleteAlarms(ActionEvent actionEvent) {

        if (tvAlarms.getSelectionModel().getSelectedItems().size() > 0) {

            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle(null);
            confirmation.setHeaderText(null);
            confirmation.setContentText("¿Está seguro de borrar las alarmas seleccionadas?");
            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.get() == ButtonType.OK) {

                DBmanager.getINSTANCE().deleteAlarmsInDB(tvAlarms.getSelectionModel().getSelectedItems());
                refreshAlarmListView();

            } else {
                // ... user chose CANCEL or closed the dialog
            }
        }
    }


    public void checkAndStartAlarms() {


        timer = new Timer();


        //if its not the first time that this method have been called, first we will clean the tasks
        // to update the task list. Otherwise, it will create a new timer and task.

        timerTask = new TimerTask() {
            @Override
            public void run() {

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

                        //refreshAlarmlist(); todo - borrar si esto no lo necesito

                        if (alarmList.size() > 0) {

                            for (Alarms a : alarmList) {

                                if (a.getDate().until(LocalDateTime.now(), ChronoUnit.SECONDS) > 0 && !a.getFinished()) {

                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Alarma");
                                    alert.setHeaderText(a.getName());
                                    alert.setContentText(a.getText());
                                    alert.show();
                                    DBmanager.getINSTANCE().setAlarmFinished(a);
                                    refreshAlarmListView();

                                }

                            }

                        }

                    }
                });
            }
        };

        timer.schedule(timerTask, 0, 1000);

    }

    public void terminateAlarmService() {
        timerTask.cancel(); //optional
        timer.cancel();
        timer.purge();

    }

    /**
     * this methos is to print the report that is currently showed on the webview
     */
    @FXML
    public void printReport() {

        FileChooser fileChooser = new FileChooser();
        File selectedFile;
        fileChooser.setTitle("Seleccióne ubicación");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("documento PDF","*.pdf")
        );
        fileChooser.setInitialFileName("resumen_apiarios.pdf");
        selectedFile=fileChooser.showSaveDialog(actualStage);

        if(typePrintReport==1){
            
            OperationManager.getInstance().printApiaryReport(selectedFile,"pdf",
                    selectedApiariesList.toArray(new Apiaries[selectedApiariesList.size()]));

        }else if (typePrintReport==2){
            
            OperationManager.getInstance().printBeehiveReport(selectedFile,"pdf",
                    selectedBeehivesList.toArray(new Beehives[selectedBeehivesList.size()]));
            
        }


    }

    /**
     * this method show all the information of the aipary or the beehive depending on what you selected.
     */
    private void refreshWebview() {

        webview.setZoom(1.25);
        WebEngine engine = webview.getEngine();
        String fileURL = "";
        if(typePrintReport==1){
            
            fileURL = OperationManager.getInstance().printApiaryReport(null,"html",
                    selectedApiariesList.toArray(new Apiaries[selectedApiariesList.size()]));
            
        }else if(typePrintReport==2){

            fileURL = OperationManager.getInstance().printBeehiveReport(null,"html",
                    selectedBeehivesList.toArray(new Beehives[selectedBeehivesList.size()]));
        }

        File f = new File(fileURL);

        engine.load(f.toURI().toString());

    }

    @FXML
    private void openWikiDiseases() {

        String s = "http://www.uco.es/dptos/zoologia/Apicultura/Enfermedades_abejas/pato_abejas_adultas.html";

        try {
            Desktop.getDesktop().browse(new URI(s));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void openLicenceWindown() {
        String s = "APPiculture\n" +

                "End-User License Agreement (EULA) of APPiculture\n" +
                "This End-User License Agreement (\"EULA\") is a legal agreement between you and Stephane Fabrizio Margini Bonilla\n" +
                "\n" +
                "This EULA agreement governs your acquisition and use of our APPiculture software (\"Software\") directly from Stephane Fabrizio Margini Bonilla or indirectly through a Stephane Fabrizio Margini Bonilla authorized reseller or distributor (a \"Reseller\").\n" +
                "\n" +
                "Please read this EULA agreement carefully before completing the installation process and using the APPiculture software. It provides a license to use the APPiculture software and contains warranty information and liability disclaimers.\n" +
                "\n" +
                "If you register for a free trial of the APPiculture software, this EULA agreement will also govern that trial. By clicking \"accept\" or installing and/or using the APPiculture software, you are confirming your acceptance of the Software and agreeing to become bound by the terms of this EULA agreement.\n" +
                "\n" +
                "If you are entering into this EULA agreement on behalf of a company or other legal entity, you represent that you have the authority to bind such entity and its affiliates to these terms and conditions. If you do not have such authority or if you do not agree with the terms and conditions of this EULA agreement, do not install or use the Software, and you must not accept this EULA agreement.\n" +
                "\n" +
                "This EULA agreement shall apply only to the Software supplied by Stephane Fabrizio Margini Bonilla herewith regardless of whether other software is referred to or described herein. The terms also apply to any Stephane Fabrizio Margini Bonilla updates, supplements, Internet-based services, and support services for the Software, unless other terms accompany those items on delivery. If so, those terms apply.\n" +
                "\n" +
                "License Grant\n" +
                "Stephane Fabrizio Margini Bonilla hereby grants you a personal, non-transferable, non-exclusive licence to use the APPiculture software on your devices in accordance with the terms of this EULA agreement.\n" +
                "\n" +
                "You are permitted to load the APPiculture software (for example a PC, laptop, mobile or tablet) under your control. You are responsible for ensuring your device meets the minimum requirements of the APPiculture software.\n" +
                "\n" +
                "You are not permitted to:\n" +
                "Edit, alter, modify, adapt, translate or otherwise change the whole or any part of the Software nor permit the whole or any part of the Software to be combined with or become incorporated in any other software, nor decompile, disassemble or reverse engineer the Software or attempt to do any such things\n" +
                "Reproduce, copy, resell or otherwise use the Software for any commercial purpose\n" +
                "Allow any third party to use the Software on behalf of or for the benefit of any third party\n" +
                "Use the Software in any way which breaches any applicable local, national or international law\n" +
                "use the Software for any purpose that Stephane Fabrizio Margini Bonilla considers is a breach of this EULA agreement\n" +
                "You are permitted to:\n" +
                "distribute\n" +
                "Intellectual Property and Ownership\n" +
                "Stephane Fabrizio Margini Bonilla shall at all times retain ownership of the Software as originally downloaded by you and all subsequent downloads of the Software by you. The Software (and the copyright, and other intellectual property rights of whatever nature in the Software, including any modifications made thereto) are and shall remain the property of Stephane Fabrizio Margini Bonilla.\n" +
                "\n" +
                "Stephane Fabrizio Margini Bonilla reserves the right to grant licences to use the Software to third parties.\n" +
                "\n" +
                "Termination\n" +
                "This EULA agreement is effective from the date you first use the Software and shall continue until terminated. You may terminate it at any time upon written notice to Stephane Fabrizio Margini Bonilla.\n" +
                "\n" +
                "It will also terminate immediately if you fail to comply with any term of this EULA agreement. Upon such termination, the licenses granted by this EULA agreement will immediately terminate and you agree to stop all access and use of the Software. The provisions that by their nature continue and survive will survive any termination of this EULA agreement.\n" +
                "\n" +
                "Governing Law\n" +
                "This EULA agreement, and any dispute arising out of or in connection with this EULA agreement, shall be governed by and construed in accordance with the laws of SPAIN.";
        alert.setContentText(s);
        alert.setTitle("Licencia");
        alert.getDialogPane().setMinWidth(1000);
        alert.showAndWait();

    }

    @FXML
    private void openAboutUsWindow() {
        String s = " Base software es una pequeña empresa formada actualmente por un miembro y situada en Asturias.\n" +
                "Pretende dar soluciones a uno de los sectores mas importantes de nuestra sociedad. La ganadería.\n" +
                "El software pretende ser gratuito con todas sus funciónes desbloqueadas. Pero para poder mantener\n" +
                "el desarrollo, hemos pensado en un modelo basado en la publicidad poco intrusiva que se puede desactivar\n" +
                "con un mínimo pago anual. El software no recolecta datos pribados de ningún tipo, y la publicidad está\n" +
                " gestionada directamente por Google. Este modelo puede cambiar en cualquier momento, aunque siempre \n" +
                "mantendremos nuestra política de privacidad.";

        alert.setContentText(s);
        alert.setTitle("Sobre nosotros");
        alert.getDialogPane().setMinWidth(700);
        alert.showAndWait();

    }

//    public void setIcon(){
//        actualStage.getIcons().add(new Image(getClass().getResourceAsStream("resources/images/bee.png")));
//    }

}
