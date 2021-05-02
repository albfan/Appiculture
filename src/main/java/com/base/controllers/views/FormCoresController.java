package com.base.controllers.views;

import com.base.controllers.DBmanager;
import com.base.models.Apiaries;
import com.base.models.Beehives;
import com.base.models.Cores;
import com.base.models.Hikes;
import com.base.models.structure.BaseController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class FormCoresController extends BaseController implements Initializable {

    @FXML
    private ComboBox<Apiaries> cbApiaries;

    @FXML
    private DatePicker dpDate;

    @FXML
    private TextField tfBreedFrames;

    @FXML
    private TextArea taNotes;

    private Apiaries selectedApiary=null;
    private Cores selectedCore=null;
    private ObservableList<Apiaries> apiariesList= FXCollections.observableArrayList();

    //TODO - pendiente verificar en toda la aplicación las validaciónes de cuando introducen datos en formulareios.

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        apiariesList= DBmanager.getINSTANCE().getApiariesFromDB();
        if (apiariesList.size() > 0) {
            cbApiaries.setItems(apiariesList);
            //if nothing is selected
            if (null == cbApiaries.getSelectionModel().getSelectedItem()) {
                cbApiaries.getSelectionModel().select(0);
            }
        }
        dpDate.setValue(LocalDate.now());

    }

    public void setApiary(Apiaries apiary) {

        selectedApiary=apiary;
        cbApiaries.getSelectionModel().select(apiary);

    }

    public void setCore(Cores core) {

        selectedCore = core;
        cbApiaries.getSelectionModel().select(selectedApiary);
        dpDate.setValue(core.getDate().toLocalDate());
        tfBreedFrames.setText(String.valueOf(core.getBreeding_frames()));
        taNotes.setText(core.getNotes());

    }

    @FXML
    public void validate(ActionEvent actionEvent) {

        String s = "";

        if (null == cbApiaries.getValue()) {
            s = s + "Debe seleccionar un apiario\n";
                    }
        if (null == dpDate.getValue()) {
            s = s + "Debe introducir la fecha de creación del núcleo.\n";
        }
        if (tfBreedFrames.getText().equals("")) {
            s = s + "Debe introducir la cantidad de cuadros de cría.\n";
        }



        if (s.equals("")) {

            try {

                Cores core = new Cores();
                core.setId_apiary(cbApiaries.getValue().getId());
                core.setDate(Date.valueOf(dpDate.getValue()));
                core.setBreeding_frames(Integer.parseInt(tfBreedFrames.getText()));
                core.setNotes(taNotes.getText());

                //if we create a new hike or we modify
                if (null == selectedCore) {

                    DBmanager.getINSTANCE().insertCoresInDB(core);
                    actualStage.close();


                } else {

                    DBmanager.getINSTANCE().updateCoresInDB(core, selectedCore);
                    actualStage.close();

                }


            } catch (Exception e) {
                e.printStackTrace();
                s = s + "Error. Recuerde que debe añadir las fechas en el formato correcto\n" +
                        "y que rellenar con numeros enteros la cantidad de cuadros de cría.";
                alert.setContentText(s);
                alert.showAndWait();
            }

        } else {
            alert.setContentText(s);
            alert.showAndWait();
        }


    }
}
