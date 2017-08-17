/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 *
 * @author User
 */
public class Login {
    static GridPane loginPane;
    static Text actiontarget;
    static TextField passInput;
    static TextField nameInput;
    static Button loginButton;
    
    public Login(){
        //GridPane with 10px padding around edge
        loginPane = new GridPane();
        loginPane.setPadding(new Insets(10, 10, 10, 10));
        loginPane.setVgap(8);
        loginPane.setHgap(10);

        //Name Label - constrains use (child, column, row)
        Label nameLabel = new Label("Usuario:");     
        GridPane.setConstraints(nameLabel, 0, 1);

        //Name Input
        nameInput = new TextField();
        GridPane.setConstraints(nameInput, 1, 1);

        //Password Label
        Label passLabel = new Label("Password:");
        GridPane.setConstraints(passLabel, 0, 2);

        //Password Input
        passInput = new TextField();
        passInput.setPromptText("contraseña");
        GridPane.setConstraints(passInput, 1, 2);

        //Login
        loginButton = new Button("Log In");               
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(loginButton);
        GridPane.setConstraints(hbBtn, 1, 4);        
        
        loginButton.setOnAction(e->{            
            //boolean userIsValid=true;
            boolean userIsValid=Login.validateLogin(Login.nameInput.getText(),Login.passInput.getText());
            if(userIsValid){                              
                PaneOrg a=new PaneOrg();
                RomeiDB.sceneApp=new Scene(a.getRoot(),820,600);
                RomeiDB.sceneApp.getStylesheets().add(getClass().getResource("Style.css").toExternalFo‌​rm());               
                RomeiDB.window.setScene(RomeiDB.sceneApp);                
            }
        });
               
        actiontarget = new Text();
        GridPane.setConstraints(actiontarget, 1, 6);
        
        //Add everything to grid
        loginPane.getChildren().addAll(nameLabel, nameInput, passLabel, passInput, hbBtn,actiontarget);       
        loginPane.setAlignment(Pos.CENTER);
        System.out.println(loginPane.getParent());
    }
    public static boolean validateLogin(String nameInput,String passInput){
        
        //if filled the two fields...
        if(!nameInput.isEmpty()&&!passInput.isEmpty()){                         
            actiontarget.setText("");
            //the method below returns a boolean, I just return it again
            return MySqlUtil.checkLogin(nameInput, passInput);                                             
        }else{
            actiontarget.setFill(Color.FIREBRICK);
            actiontarget.setText("Ingrese todos los campos");
            return false;
        }   
    }
    public GridPane getLoginPane() {
        return loginPane;
    }

    public static Button getLoginButton() {
        return loginButton;
    }
    
    
}
