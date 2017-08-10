/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseproject;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author User
 */
public class RomeiDB extends Application {
    
    @Override
    public void start(Stage primaryStage) {       
        PaneOrg app=new PaneOrg();
        Scene scene = new Scene(app.getRoot(), 900, 500);
        scene.getStylesheets().add(getClass().getResource("Style.css").toExternalFo‌​rm());       
        primaryStage.setTitle("Comercial Romei - DB Management");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
