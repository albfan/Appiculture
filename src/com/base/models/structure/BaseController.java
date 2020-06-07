package com.base.models.structure;

import com.base.models.Cores;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
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
    public void closeWindow() {
        if (null != actualStage) {
            actualStage.close();
        }
    }

    @FXML
    public void validate(){

    }


}
