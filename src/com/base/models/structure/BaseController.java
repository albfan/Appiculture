package com.base.models.structure;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class BaseController {

    protected Stage actualStage;
    protected Alert alert;

    public BaseController(){
        alert=new Alert(Alert.AlertType.WARNING);
        alert.setTitle(null);
        alert.setHeaderText(null);
    }

    public void setActualStage(Stage s) {
        actualStage = s;
    }

    @FXML
    public void cerrarVentana() {
        if (null != actualStage) {
            actualStage.close();
        }
    }

}
