package com.romei;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.IOException;


/**
 * JavaFX App
 */
public class App extends Application {

    public static Stage window;
    private static Scene scene;
    
    @Override
    public void start(Stage stage) throws IOException {
        window = stage;
    
        stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        //scene = new Scene(loadFXML("login"), 640, 480);
        scene = new Scene(loadFXML("main"), 640, 480);
        //scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
        stage.setTitle("Comercial Romei - DB Management");
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}