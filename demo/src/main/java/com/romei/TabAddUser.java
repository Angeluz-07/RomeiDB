/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseproject;

import static databaseproject.Utils.bothFieldsEqual;
import static databaseproject.Utils.showErrorDialog;
import static databaseproject.Utils.showInfoDialog;
import static databaseproject.Utils.thereAreEmptyFields;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import javafx.geometry.*;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import static databaseproject.MySqlUtil.dataIsInDB;

/**
 *
 * @author User
 */
public class TabAddUser extends Tab {
    static GridPane addUserPane;
    static Text title= new Text("Ingrese los datos");
    static VBox container;
    
    static Text actiontarget;
    static TextField firstName;
    static TextField lastName;
    static TextField userName;    
    
    static TextField passInput;
    static TextField confirmPassInput;  
    
    static Button saveButton;
                
    static ArrayList<Object> fields;
    static ArrayList<Object> data;
    
    public TabAddUser(String text){
        Label l = new Label(text);
        l.setRotate(90);
        StackPane stp = new StackPane(new Group(l));
        stp.setRotate(90);
        this.setGraphic(stp);  
        init();
    }
    private void init(){        
        //GridPane with 10px padding around edge
        container = new VBox();       
        
        HBox hbTitle=new HBox();
        hbTitle.getChildren().add(title);
        hbTitle.setAlignment(Pos.CENTER);
        container.getChildren().add(hbTitle);
        
        addUserPane = new GridPane();
        //addUserPane.setGridLinesVisible(true);
        addUserPane.setPadding(new Insets(10, 10, 10, 10));
        addUserPane.setVgap(10);
        addUserPane.setHgap(10);

        //firstName Label - constrains use (child, column, row)
        Label firstNameLabel = new Label("Nombre:");     
        GridPane.setConstraints(firstNameLabel, 0, 1);
        //firstName Input
        firstName = new TextField();
        GridPane.setConstraints(firstName, 1, 1);     
        
        //lastName Label - constrains use (child, column, row)
        Label lastNameLabel = new Label("Apellido:");     
        GridPane.setConstraints(lastNameLabel, 0, 2);
        //lastName Input
        lastName = new TextField();
        GridPane.setConstraints(lastName, 1, 2);
        
        //userName Label - constrains use (child, column, row)
        Label userNameLabel = new Label("Nombre de Usuario:");     
        GridPane.setConstraints(userNameLabel, 0, 3);
        //userName Input
        userName = new TextField();
        GridPane.setConstraints(userName, 1, 3);

        //Password Label
        Label passLabel = new Label("Nueva Contraseña:");
        GridPane.setConstraints(passLabel, 0, 4);
        //Password Input
        passInput = new TextField();
        passInput.setPromptText("contraseña");
        GridPane.setConstraints(passInput, 1, 4);
        
        //confirm Password Label
        Label confirmPassLabel = new Label("Confirmar Contraseña:");
        GridPane.setConstraints(confirmPassLabel, 0, 5);
        //confirm Password Input
        confirmPassInput = new TextField();
        confirmPassInput.setPromptText("repita contraseña");
        GridPane.setConstraints(confirmPassInput, 1, 5);
                

        //save
        saveButton = new Button("Guardar");               
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(saveButton);
        GridPane.setConstraints(hbBtn, 1, 6);        
        saveButton.setOnAction(e->{
            fields=new ArrayList();
            Collections.addAll(fields,firstName.getText(),
                                      lastName.getText(),
                                      userName.getText(),
                                      passInput.getText(),
                                      confirmPassInput.getText());
            if(!thereAreEmptyFields(fields,actiontarget)){
                if(bothFieldsEqual(passInput.getText(),confirmPassInput.getText(),actiontarget)){                     
                    String query1="select FirstName,LastName,UserName from users where FirstName=? and LastName=? and UserName=? and inDB=1";                                             
                    if(dataIsInDB(fields.subList(0, 3),query1,"TabAddUsser.java to check if already exist user").isEmpty()){                                 
                        data=new ArrayList();                        
                        Collections.addAll(data,firstName.getText(),
                                                lastName.getText(),
                                                userName.getText(),                             
                                                passInput.getText());
                        String query2="";
                        query2+="insert into Users (FirstName,LastName,UserName,Password)";    
                        query2+="values(?,?,?,?) ";
                        if(MySqlUtil.queryWithData(data,query2,"TabAddUser to insert user")){
                            showInfoDialog("La operacion se realizo con exito!");      
                        }else{
                            showErrorDialog("Un error ocurrio durante la transaccion");
                        }                            
                    }else{                        
                       showErrorDialog("El usuario ya esta registrado");
                    }    
                }
            }
        });
               
        actiontarget = new Text();
        GridPane.setConstraints(actiontarget, 1, 8);
                             
        //Add everything to grid
        addUserPane.getChildren().addAll(firstNameLabel,lastNameLabel,userNameLabel);
        addUserPane.getChildren().addAll(firstName,lastName,userName);
        addUserPane.getChildren().addAll(passLabel,confirmPassLabel);
        addUserPane.getChildren().addAll(passInput,confirmPassInput,hbBtn,actiontarget);    
        addUserPane.setAlignment(Pos.CENTER);
               
        container.getChildren().add(addUserPane);
        this.setContent(container);       
    }
}
