package com.romei;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


// 
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
//

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        // scene = new Scene(loadFXML("primary"), 640, 480);
        // stage.setScene(scene);

        window=primaryStage;                               
        window.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));

        Login login=new Login();
        sceneLogIn = new Scene(login.getLoginPane(), 820, 600);
        
        sceneLogIn.getStylesheets().add(getClass().getResource("Style.css").toExternalFo‌​rm());       
        //sceneApp.getStylesheets().add(getClass().getResource("Style.css").toExternalFo‌​rm());                   
        window.setTitle("Comercial Romei - DB Management");
        window.setScene(sceneLogIn);
        window.show();
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}