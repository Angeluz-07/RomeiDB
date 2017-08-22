/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseproject;

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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author User
 */
public class TabModifySupplier extends Tab {
    static GridPane addSupplierPane;
    static Text title= new Text("Ingrese los datos");
    static VBox container;
    
    static Text actiontarget;
    static TextField contactName;
    static TextField phone;  
    
    static Button saveButton;    
    static Button deleteButton;  
    
    static ArrayList<Object> fields;
    static ArrayList<Object> data;
    
    ComboBox<Supplier> supplierComboBox;
    static ArrayList<Supplier> suppliers=new ArrayList();
     
    public TabModifySupplier(String text){          
        Label l = new Label(text);
        l.setRotate(90);
        StackPane stp = new StackPane(new Group(l));
        stp.setRotate(90);
        this.setGraphic(stp);
        //this.setText(text);   
        init();
    }
    private void init(){        
        //GridPane with 10px padding around edge
        container = new VBox();       
        
        HBox hbTitle=new HBox();
        hbTitle.getChildren().add(title);
        hbTitle.setAlignment(Pos.CENTER);
        container.getChildren().add(hbTitle);
        
        addSupplierPane = new GridPane();
        //addUserPane.setGridLinesVisible(true);
        addSupplierPane.setPadding(new Insets(10, 10, 10, 10));
        addSupplierPane.setVgap(10);
        addSupplierPane.setHgap(10);

        supplierComboBox=new ComboBox();
        supplierComboBox.setPromptText("Elija un proveedor");
        //the next line should be replaced with a db access
      
        supplierComboBox.setOnShowing(e->{            
            ObservableList<Supplier> suppliersToComboBox=FXCollections.observableArrayList();
            suppliers=MySqlUtil.getCurrentSuppliers();
            suppliersToComboBox.setAll(suppliers);
            supplierComboBox.setItems(suppliersToComboBox);                   
        }); 
        
        supplierComboBox.setOnHidden(e->{
            Supplier chosenSupplier=supplierComboBox.getValue();
            contactName.setText(chosenSupplier.getContactName());
            phone.setText(chosenSupplier.getPhone());
        }); 
        GridPane.setConstraints(supplierComboBox, 3, 1);
        
        
        
        //ContactName Label - constrains use (child, column, row)
        Label contactNameLabel = new Label("Nombre de Contacto:");     
        GridPane.setConstraints(contactNameLabel, 0, 1);
        //firstName Input
        contactName = new TextField();
        GridPane.setConstraints(contactName, 1, 1);     
        
        //phone Label - constrains use (child, column, row)
        Label phoneLabel = new Label("Telefono:");     
        GridPane.setConstraints(phoneLabel, 0, 2);
        //lastName Input
        phone = new TextField();
        GridPane.setConstraints(phone, 1, 2);
        
        deleteButton = new Button("Borrar"); 
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(deleteButton); 
        deleteButton.setOnAction(e->{
            fields=new ArrayList();
            Collections.addAll(fields,contactName.getText(),
                                      phone.getText());
            if(!thereAreEmptyFields(fields,actiontarget)){
                String query1="select ContactName,phone from Suppliers where ContactName=? and Phone=? and inDB=1";                                             
                if(!dataIsInDB(fields.subList(0, 2),query1,"TabAddSupplier.java").isEmpty()){                                 
                    data=new ArrayList();                    
                    Collections.addAll(data,supplierComboBox.getValue().getSupplierID());                                                                               
                    String query2="";
                    query2+="call setStateSupplier(0,?)";
                    if(MySqlUtil.queryWithData(data,query2,"TabAddSupplier on delete")){
                        showInfoDialog("La operacion se realizo con exito!");      
                    }else{
                        showErrorDialog("Un error ocurrio durante la transaccion.");
                    }
                }else{                        
                   showErrorDialog("El Proveedor no existe");
                }                    
            }
        });
            
        
        //save
        saveButton = new Button("Guardar");   
        hbBtn.getChildren().add(saveButton);
        GridPane.setConstraints(hbBtn, 1, 3);        
        saveButton.setOnAction(e->{
            fields=new ArrayList();
            Collections.addAll(fields,contactName.getText(),
                                      phone.getText());
            if(!thereAreEmptyFields(fields,actiontarget)){
                String query1="select ContactName,phone from Suppliers where ContactName=? and Phone=? and inDB=1";                                             
                if(dataIsInDB(fields.subList(0, 2),query1,"TabAddSupplier.java").isEmpty()){                                 
                    data=new ArrayList();                    
                    Collections.addAll(data,contactName.getText(),
                                            phone.getText(),
                                            supplierComboBox.getValue().getSupplierID());                                        
                    String query2="";
                    query2+="call updateSupplier(?,?,?)";
                    if(MySqlUtil.queryWithData(data,query2,"TabAddSupplier on update")){
                        showInfoDialog("La operacion se realizo con exito!");      
                    }else{
                        showErrorDialog("Un error ocurrio durante la transaccion.");
                    }
                }else{                        
                   showErrorDialog("El Proveedor ya esta registrado");
                }                    
            }                                        
        });
               
        actiontarget = new Text();
        GridPane.setConstraints(actiontarget, 1, 4);
                             
        //Add everything to grid
        addSupplierPane.getChildren().addAll(contactNameLabel,phoneLabel);
        addSupplierPane.getChildren().addAll(contactName,phone);
        addSupplierPane.getChildren().addAll(hbBtn,actiontarget);    
        addSupplierPane.getChildren().add(supplierComboBox);
        addSupplierPane.setAlignment(Pos.CENTER);
               
        container.getChildren().add(addSupplierPane);
        this.setContent(container);       
    }
}
