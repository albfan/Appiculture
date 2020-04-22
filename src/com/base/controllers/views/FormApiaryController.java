package com.base.controllers.views;

import com.base.controllers.DBmanager;
import com.base.models.Apiaries;
import com.base.models.structure.BaseController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class FormApiaryController extends BaseController implements Initializable {
    @FXML
    private TextField TFname;

    @FXML
    private TextField TFaddress;

    @FXML
    private Button BtnOk;

    @FXML
    private Button BtnCancel;

    /**
     * This apiary variables are in case we use this form to modify instead of creating
     */
    private Apiaries apiary=null;
    private int id=-2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    @FXML
    @Override
    public void validate(){ //todo verificar que deja modificar

        Apiaries ap= new Apiaries();
        ap.setName(TFname.getText());
        ap.setAdress(TFaddress.getText());
        //if we use this form to create a new apiary, the id will be by default -2. if its for modifying it will
        //be something diferent.
        if(!"".equals(TFname.getText()) || !"".equals(TFaddress.getText())){
            if(id!=-2){
                ap.setId(id);
                DBmanager.getINSTANCE().modifyApiaryInDB(ap);
            }else {
                DBmanager.getINSTANCE().insertApiaryInDB(ap);
            }

            actualStage.close();
        }
    }

    public Apiaries getApiary() {
        return apiary;
    }

    public void setApiary(Apiaries apiary) {
        this.apiary = apiary;
        TFname.setText(apiary.getName());
        TFaddress.setText(apiary.getAdress());
        id=apiary.getId();
    }
}
