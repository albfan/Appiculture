package com.base.controllers.views;

import com.base.controllers.DBmanager;
import com.base.controllers.OperationManager;
import com.base.models.Alarms;
import com.base.models.Apiaries;
import com.base.models.structure.BaseController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class FormAlarmController extends BaseController implements Initializable {

    @FXML
    private Button btnValidate;

    @FXML
    private TextField tfName;

    @FXML
    private DatePicker dpDate;

    @FXML
    private TextArea taMessage;

    @FXML
    private ComboBox<String> cbHours;

    @FXML
    private ComboBox<String> cbMinutes;

    @FXML
    private ComboBox<String> cbFinished;

    @FXML
    private Label labelFinished;


    private Alarms selectedAlarm = null;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        labelFinished.setVisible(false);
        cbFinished.setVisible(false);
        dpDate.setValue(LocalDate.now());
        cbHours.setItems(OperationManager.getInstance().getHoursList());
        cbMinutes.setItems(OperationManager.getInstance().getMinutesAndSecondsList());
        cbFinished.setItems(OperationManager.getInstance().getYesOrNoList());

    }

    public void setSelectedAlarm(Alarms selectedAlarm) {

        this.selectedAlarm = selectedAlarm;
        labelFinished.setVisible(true);
        cbFinished.setVisible(true);
        dpDate.setValue(selectedAlarm.getDate().toLocalDate());
        cbHours.setValue(Integer.toString(selectedAlarm.getDate().getHour()));
        cbMinutes.setValue(Integer.toString(selectedAlarm.getDate().getMinute()));
        tfName.setText(selectedAlarm.getName());
        taMessage.setText(selectedAlarm.getText());
        cbFinished.getSelectionModel().select(selectedAlarm.getFinishedString());

    }

    @FXML
    @Override
    public void validate() {


        String s = "";

        if ("".equals(taMessage.getText())) {
            s = s + "Debe introducir un mensaje para la alarma.\n";
        }
        if (null == dpDate.getValue()) {
            s = s + "Debe seleccionar una fecha.\n";
        }
        if (null == cbHours.getValue()) {
            s = s + "Debe seleccionar una hora para la alarma.\n";
        }
        if (null == cbMinutes.getValue()) {
            s = s + "Debe seleccionar los minutos para la alarma.\n";
        }

        if (s.equals("")) {

            try {

                Alarms alarm = new Alarms();
                alarm.setName(tfName.getText());
                alarm.setDate(OperationManager.getInstance().getLocalDateFromNodesValues(dpDate.getValue(), cbHours.getValue(), cbMinutes.getValue()));
                alarm.setText(taMessage.getText());

                if (null != cbFinished.getValue()) {

                    if (cbFinished.getValue().equals("si")) {
                        alarm.setFinished(true);
                    } else {
                        alarm.setFinished(false);
                    }

                } else {
                    alarm.setFinished(false);
                }

                //if we create a new Alarm or we modify
                if (null == selectedAlarm) {


                    DBmanager.getINSTANCE().insertAlarmsInDB(alarm);
                    actualStage.close();


                } else {

                    DBmanager.getINSTANCE().updateAlarmsInDB(alarm, selectedAlarm);
                    actualStage.close();

                }


            } catch (Exception e) {
                e.printStackTrace();
                s = s + "Error. Recuerde que debe añadir las fechas en el formato correcto\n" +
                        "y que debe seleccionar correctamente la hora, los minutos.\n" +
                        "Además debe introducir un texto en el mensaje.";
                alert.setContentText(s);
                alert.showAndWait();
            }

        } else {
            alert.setContentText(s);
            alert.showAndWait();
        }


        
    }

}
