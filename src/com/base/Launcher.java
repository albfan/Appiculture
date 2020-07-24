package com.base;


import com.base.controllers.DBmanager;
import com.base.controllers.views.MainController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * this class launch the app
 */
public class Launcher extends Application
{
    public static void main( String[] args )
    {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {

        DBmanager.getINSTANCE().openConnection();

        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("views/Main.fxml")); //recuperas todos los datos del fxml
        Parent root = fxmlLoader.load(); // guardas el contenedor padre del fxml
        MainController mainWindowController = fxmlLoader.getController();//creas una instancia de tu controlador para pasarle el stage
        Scene scene = new Scene(root);

        stage.setScene(scene);
        //stage.setMaximized(true);
        stage.sizeToScene();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {

                mainWindowController.terminateAlarmService();
                DBmanager.getINSTANCE().closeConnection();


            }
        });
        mainWindowController.setActualStage(stage);
        stage.getIcons().add(new Image("file:resources/images/bee.png"));
        stage.setTitle("APPiculture");
        stage.show();
    }
}
