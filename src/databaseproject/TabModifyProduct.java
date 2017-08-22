/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseproject;

import static databaseproject.TabRegister.registerTable;
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
public class TabModifyProduct extends Tab {    
    static GridPane addProductPane;
    static Text title= new Text("Ingrese los datos");
    static VBox container;
    
    static Text actiontarget;
    static TextField productName;
    static TextField price;
    static TextField supplierName;    
    
    ComboBox<Product> productComboBox;
    static Button saveButton;
    static Button deleteButton;    
    
    static ArrayList<Object> fields;
    static ArrayList<Object> data;
    
    static ArrayList<Product> products=new ArrayList();
                
    public TabModifyProduct(String text){
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
        //container.setAlignment(Pos.TOP_CENTER);
        HBox hbTitle=new HBox();
        hbTitle.getChildren().add(title);
        hbTitle.setAlignment(Pos.CENTER);
        container.getChildren().add(hbTitle);
        
        addProductPane = new GridPane();
        addProductPane.setAlignment(Pos.CENTER);
        //addProductPane.setGridLinesVisible(true);
        addProductPane.setPadding(new Insets(10, 10, 10, 10));
        addProductPane.setVgap(10);
        addProductPane.setHgap(10);

        productComboBox=new ComboBox();
        productComboBox.setPromptText("Elija un producto");
        //the next line should be replaced with a db access
        productComboBox.getItems().addAll(MySqlUtil.getCurrentProducts());
        productComboBox.setOnShowing(e->{            
            ObservableList<Product> productsToComboBox=FXCollections.observableArrayList();
            products=MySqlUtil.getCurrentProducts();
            productsToComboBox.setAll(products);
            productComboBox.setItems(productsToComboBox);        
        }); 
        
        productComboBox.setOnHidden(e->{
            Product chosenProduct=productComboBox.getValue();
            productName.setText(chosenProduct.getName());
            price.setText(Double.toString(chosenProduct.getPrice()));
            supplierName.setText(chosenProduct.getSupplier().getContactName());
        }); 
        GridPane.setConstraints(productComboBox, 3, 1);
        
        //productName Label - constrains use (child, column, row)
        Label productNameLabel = new Label("Nombre del Producto:");     
        GridPane.setConstraints(productNameLabel, 0, 1);
        //productName Input
        productName = new TextField();
        GridPane.setConstraints(productName, 1, 1);     
        
        //price Label - constrains use (child, column, row)
        Label priceLabel = new Label("Precio:");     
        GridPane.setConstraints(priceLabel, 0, 2);
        //price Input
        price = new TextField();
        GridPane.setConstraints(price, 1, 2);
        
        //supplierName Label - constrains use (child, column, row)
        Label supplierNameLabel = new Label("Nombre de Proveedor:");     
        GridPane.setConstraints(supplierNameLabel, 0, 3);
        //userName Input
        supplierName = new TextField();
        GridPane.setConstraints(supplierName, 1, 3);

        deleteButton = new Button("Borrar"); 
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(deleteButton); 
        deleteButton.setOnAction(e->{
            fields=new ArrayList();
            Collections.addAll(fields,productName.getText(),
                                      price.getText(),
                                      supplierName.getText());
            
            if(!thereAreEmptyFields(fields,actiontarget)){
                String query1="call getProductWithPrice(?,?)";
                if(!dataIsInDB(fields.subList(0, 2),query1,"check if the product doesn't exit").isEmpty()){
                    String query2="select ContactName,phone from Suppliers where ContactName=?";                                             
                    if(!dataIsInDB(fields.subList(2, 3),query2,"check if exist supplier").isEmpty()){                                 
                        data=new ArrayList();
                        Collections.addAll(data,productComboBox.getValue().getProductPriceID(),
                                                productComboBox.getValue().getProductID());                 
                        String query3="";
                        query3+="call setStateProduct(0,?,?)"; 
                        if(MySqlUtil.queryWithData(data,query3,"TabAddProduct to delete")){
                            showInfoDialog("La operacion se realizo con exito!");      
                        }else{
                            showErrorDialog("Un error ocurrio durante la transaccion");
                        }
                    }else{                        
                       showErrorDialog("El Proveedor no esta registrado");
                    } 
                }else{                    
                  showErrorDialog("El producto no existe.");
                }
            }    
        });
        //save
        saveButton = new Button("Guardar");                              
        hbBtn.getChildren().add(saveButton);
        GridPane.setConstraints(hbBtn, 1, 4);        
        saveButton.setOnAction(e->{
            
            fields=new ArrayList();
            Collections.addAll(fields,productName.getText(),
                                      price.getText(),
                                      supplierName.getText());
            
            if(!thereAreEmptyFields(fields,actiontarget)){
                String query1="call getProductWithPrice(?,?)";
                if(dataIsInDB(fields.subList(0, 2),query1,"check if exist the product").isEmpty()){
                    String query2="select ContactName,phone from Suppliers where ContactName=?";                                             
                    if(!dataIsInDB(fields.subList(2, 3),query2,"check if exist supplier").isEmpty()){                                                                                  
                        data=new ArrayList();
                        Collections.addAll(data,supplierName.getText(),
                                                productName.getText(),
                                                Double.parseDouble(price.getText()),
                                                productComboBox.getValue().getProductID(),
                                                productComboBox.getValue().getProductPriceID());                 
                        String query3="";
                        query3+="call updateProduct(?,?,?,?,?)"; 
                        if(MySqlUtil.queryWithData(data,query3,"TabAddProduct to update")){
                            showInfoDialog("La operacion se realizo con exito!");      
                        }else{
                            showErrorDialog("Un error ocurrio durante la transaccion");
                        }
                    }else{                        
                       showErrorDialog("El Proveedor no esta registrado");
                    } 
                }else{                    
                  showErrorDialog("El producto ya existe.");
                }
            }                                
        });
                    
        
        actiontarget = new Text();
        GridPane.setConstraints(actiontarget, 1, 5);
                             
        //Add everything to grid
        addProductPane.getChildren().addAll(productNameLabel,priceLabel,supplierNameLabel);
        addProductPane.getChildren().addAll(productName,price,supplierName);
        addProductPane.getChildren().addAll(hbBtn,actiontarget); 
        addProductPane.getChildren().add(productComboBox);
        addProductPane.setAlignment(Pos.CENTER);
               
        container.getChildren().add(addProductPane);
        this.setContent(container);       
    }
}
