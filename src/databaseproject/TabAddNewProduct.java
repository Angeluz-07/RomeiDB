/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseproject;

import static databaseproject.TabRegister.registerTable;
import java.time.LocalDate;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author User
 */
public class TabAddNewProduct extends Tab {
    VBox container;
    DatePicker datePicker=new DatePicker();
    static TableView<Product> addProductTable=new TableView(AddProductTableUtil.getRegisterList());       
    //new Product button and save product
    Button newProdB;
    Button saveProdB;
        
    public TabAddNewProduct(String text){
        this.setText(text);   
        init();
    }
    private void init(){
        datePicker.setValue(LocalDate.now());
        datePicker.setEditable(false);
                
        addProductTable.setMinWidth(620);
        addProductTable.setEditable(true);
        
        addProductTable.getColumns().addAll(AddProductTableUtil.getNameOfProductColumn(),
                                            AddProductTableUtil.getPriceColumn(),
                                            AddProductTableUtil.getQuantityColumn());
        
        container =new VBox();                
        container.getChildren().addAll(datePicker,addProductTable);                
                
        HBox buttonsContainer=new HBox(); 
        buttonsContainer.setAlignment(Pos.CENTER);
        buttonsContainer.setPadding(new Insets(10,10,10,10));               
        buttonsContainer.setSpacing(10);
        
        newProdB= new Button("Nuevo Producto");      
        newProdB.setOnAction(e->{    
            addProductTable.setItems(AddProductTableUtil.getRegisterList());            
        });
        saveProdB= new Button("Guardar");        
        
        buttonsContainer.getChildren().addAll(newProdB,saveProdB);
        
        container.getChildren().addAll(buttonsContainer);        
        this.setContent(container);       
    }
}
