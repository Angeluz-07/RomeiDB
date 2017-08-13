/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseproject;
import java.time.LocalDate;
import java.util.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.*;

/**
 *
 * @author User
 */
public class TabRegister extends Tab {
    VBox container;
    DatePicker datePicker=new DatePicker();
    static TableView<Register> registerTable=new TableView(RegisterTableUtil.getRegisterList());   
    static Label totalValLabel;
    Button newRegB;
    Button saveRegB;
    
    public TabRegister(String text){
        this.setText(text);   
        init();
    }
    private void init(){        
        datePicker.setEditable(false);        
        datePicker.setValue(LocalDate.now());       
        registerTable.setMinWidth(620);
        registerTable.setEditable(true);
        
        registerTable.getColumns().addAll(RegisterTableUtil.getAddedOrRemovedStockColumn(),
                                          RegisterTableUtil.getProductColumn(),
                                          RegisterTableUtil.getInitialStockColumn(),
                                          RegisterTableUtil.getFinalStockColumn(),
                                          RegisterTableUtil.getQuantitySoldColumn(),
                                          RegisterTableUtil.getCashSaleColumn());
        
        container =new VBox();                
        container.getChildren().addAll(datePicker,registerTable);                
        
        HBox resultContainer=new HBox();                
        resultContainer.setAlignment(Pos.CENTER_RIGHT);
        Label totalLabel=new Label("Total : ");       
        totalValLabel=new Label("      ");
        
        resultContainer.getChildren().addAll(totalLabel,totalValLabel);
        
        HBox buttonsContainer=new HBox(); 
        buttonsContainer.setAlignment(Pos.CENTER);
        buttonsContainer.setPadding(new Insets(10,10,10,10));       
        buttonsContainer.setSpacing(10);
        newRegB= new Button("Nuevo Registro");      
        newRegB.setOnAction(e->{    
            registerTable.setItems(RegisterTableUtil.getRegisterList());            
        });
        saveRegB= new Button("Guardar");        
        
        buttonsContainer.getChildren().addAll(newRegB,saveRegB);
        
        container.getChildren().addAll(resultContainer,buttonsContainer);        
        this.setContent(container);
    }

    public static TableView<Register> getRegisterTable() {
        return registerTable;
    }

}
