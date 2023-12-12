/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.romei;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class RomeiDB extends Application {    
    static Stage window;
    static Scene sceneLogIn,sceneApp;
    
    @Override
    public void start(Stage primaryStage) {       
        window=primaryStage;                               
        //window.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        //PaneOrg app=new PaneOrg(); 
        Login login=new Login();
        sceneLogIn = new Scene(login.getLoginPane(), 1000, 800);                      
     
        sceneLogIn.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());       
        //sceneApp.getStylesheets().add(getClass().getResource("Style.css").toExternalFo‌​rm());                   
        window.setTitle("Comercial Romei - DB Management");
        window.setScene(sceneLogIn);
        window.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
