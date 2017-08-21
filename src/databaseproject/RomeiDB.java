/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseproject;

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
        window.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        //PaneOrg app=new PaneOrg(); 
        Login login=new Login();
        sceneLogIn = new Scene(login.getLoginPane(), 820, 600);                      
        //sceneApp=new Scene(app.getRoot(),700,600);
        // I set this handler here, because from here
        
        /*Login.loginButton.setOnAction(e->{            
            boolean userIsValid=true;
            //boolean userIsValid=Login.validateLogin(Login.nameInput.getText(),Login.passInput.getText());
            if(userIsValid){                              
                PaneOrg a=new PaneOrg();
                sceneApp=new Scene(a.getRoot(),700,600);
                window.setScene(sceneApp);                
            }
        }); */
        /*
        PaneOrg.tabLogOut.setOnSelectionChanged(e->{
            Login log=new Login();
            sceneLogIn = new Scene(log.getLoginPane(), 700, 600);
            window.setScene(sceneLogIn);
            //sceneApp=null;
            //PaneOrg.tabPane.getSelectionModel().select(0);
        });*/
        
        sceneLogIn.getStylesheets().add(getClass().getResource("Style.css").toExternalFo‌​rm());       
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
