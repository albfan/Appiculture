package com.base.controllers.views;

import com.base.controllers.DBmanager;
import com.base.controllers.OperationManager;
import com.base.models.Apiaries;
import com.base.models.Beehives;
import com.base.models.Hikes;
import com.base.models.Queens;
import com.base.models.structure.BaseController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class FormHikeController extends BaseController implements Initializable {

    @FXML
    private DatePicker dpPlacement;

    @FXML
    private ComboBox<Apiaries> cbApiary;

    @FXML
    private ComboBox<Beehives> cbBeehive;

    @FXML
    private ComboBox<String> cbType;

    @FXML
    private DatePicker dpWithdrawal;

    @FXML
    private Button btnValidate;

    private ObservableList<Beehives> beehivesList = FXCollections.observableArrayList();
    private ObservableList<Apiaries> apiariesList = FXCollections.observableArrayList();
    private Beehives selectedBeehive = null;
    private Hikes selectedHike = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        apiariesList = DBmanager.getINSTANCE().getApiariesFromDB();
        cbApiary.setItems(apiariesList);
        cbApiary.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Apiaries>() {
            @Override
            public void changed(ObservableValue<? extends Apiaries> observable, Apiaries oldValue, Apiaries newValue) {

                beehivesList = DBmanager.getINSTANCE().getBeehivesFromDB(newValue);
                cbBeehive.setItems(beehivesList);
                cbBeehive.getSelectionModel().clearSelection();
                cbBeehive.setValue(null);

            }
        });
        dpPlacement.setValue(LocalDate.now());
        cbType.setItems(OperationManager.getInstance().getHikeTypes());
        cbType.getSelectionModel().selectFirst();

    }

    public void setSelectedBeehive(Beehives beehive) {

        selectedBeehive = beehive;
        cbApiary.getSelectionModel().select(DBmanager.getINSTANCE().getApiary(beehive.getId_apiary()));
        cbBeehive.getSelectionModel().select(beehive);

    }

    public void setSelectedHike(Hikes hike) {
        selectedHike = hike;
        cbApiary.getSelectionModel().select(DBmanager.getINSTANCE().getApiary(hike.getId_apiary()));
        cbBeehive.getSelectionModel().select(selectedBeehive);
        cbType.getSelectionModel().select(selectedBeehive.getId_apiary());
        dpPlacement.setValue(hike.getPlacement_date().toLocalDate());
        if (null != hike.getWithdrawal_date()) {

            dpWithdrawal.setValue(hike.getWithdrawal_date().toLocalDate());

        }
    }

    @FXML
    public void validate(ActionEvent actionEvent) {

        String s = "";

        if (null == cbApiary.getValue()) {
            s = s + "Debe seleccionar un apiario\n";
        }
        if (null == cbBeehive.getValue()) {
            s = s + "Debe seleccionar una colmena.\n";
        }
        if (null == cbType.getValue()) {
            s = s + "Debe seleccionar el tipo de alza.\n";
        }
        if (null == dpPlacement.getValue()) {
            s = s + "Debe introducir la fecha de colocación del alza.\n";
        }

        if (s.equals("")) {

            try {

                Hikes hike = new Hikes();
                hike.setId_apiary(cbApiary.getValue().getId());
                hike.setId_beehive((cbBeehive.getValue().getNumber()));
                hike.setType(cbType.getValue());
                hike.setPlacement_date(Date.valueOf(dpPlacement.getValue()));
                if (null != dpWithdrawal.getValue()) {
                    hike.setWithdrawal_date(Date.valueOf(dpWithdrawal.getValue()));
                }

                //if we create a new hike or we modify
                if (null == selectedHike) {

                    DBmanager.getINSTANCE().insertHikeInDB(hike);
                    actualStage.close();


                } else {

                    DBmanager.getINSTANCE().updateHikesInDB(hike, selectedHike);
                    actualStage.close();

                }


            } catch (Exception e) {
                e.printStackTrace();
                s = s + "Error. Recuerde que debe añadir las fechas en el formato correcto\n" +
                        "y que debe seleccionar correctamente una colmena,un apiario y el tipo de alza.";
                alert.setContentText(s);
                alert.showAndWait();
            }

        } else {
            alert.setContentText(s);
            alert.showAndWait();
        }


    }
}
