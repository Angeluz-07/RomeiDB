/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.romei;

import static com.romei.Utils.showErrorDialog;
import static com.romei.Utils.thereAreEmptyFields;
import java.util.ArrayList;
import java.util.Collections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import static com.romei.MySqlUtil.dataIsInDB;

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
    
    static ArrayList<Object> fields;
    
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
        passInput = new PasswordField();      
        passInput.setPromptText("contraseña");
        GridPane.setConstraints(passInput, 1, 2);

        //Login
        loginButton = new Button("Log In");               
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(loginButton);
        GridPane.setConstraints(hbBtn, 1, 4);        
        
        
        loginButton.setOnAction(e->{            
            System.out.println("Hello world");
            //add all the fields to the array fields'
            fields=new ArrayList();
            Collections.addAll(fields,nameInput.getText(),passInput.getText());        
            String query="select UserID,UserName,Password from Users where UserName=? and Password=?";                                 
            if(!thereAreEmptyFields(fields,actiontarget)){
                if(!dataIsInDB(fields,query,"Login.java").isEmpty()){                 
                    System.out.println(dataIsInDB(fields,query,"Login.java").toString());
                    TabRegister.userID=(Integer)dataIsInDB(fields,query,"").get(0);
                    PaneOrg a=new PaneOrg();
                    RomeiDB.sceneApp=new Scene(a.getRoot(),820,600);
                    RomeiDB.sceneApp.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());               
                    RomeiDB.window.setScene(RomeiDB.sceneApp);                
                }else{
                    showErrorDialog("No existe el usuario.");
                }   
            }
        });
               
        actiontarget = new Text();
        GridPane.setConstraints(actiontarget, 1, 6);
        
        //Add everything to grid
        loginPane.getChildren().addAll(nameLabel, nameInput, passLabel, passInput, hbBtn,actiontarget);       
        loginPane.setAlignment(Pos.CENTER);
        //loginPane.setStyle("-fx-background-color: linear-gradient(to bottom left,#cc5333,#23074d)");   
    }
    
    public GridPane getLoginPane() {
        return loginPane;
    }

    public static Button getLoginButton() {
        return loginButton;
    }
    
    
}
