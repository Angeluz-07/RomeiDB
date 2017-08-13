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
public class TabReport extends Tab {
    VBox container;      
    static TableView<Register> reportTable=new TableView(RegisterTableUtil.getRegListToReport());       
    //generate report Button
    Button genReportB;
    //DatePicker datePicker=new DatePicker();        
    public TabReport(String text){
        this.setText(text);   
        init();
    }
    private void init(){
        //datePicker.setValue(LocalDate.now());
        //datePicker.setEditable(false);
                
        reportTable.setMinWidth(620);
        
        reportTable.getColumns().addAll(RegisterTableUtil.getDateColumn(),
                                        RegisterTableUtil.getQuantitySoldColumn(),
                                        RegisterTableUtil.getCashSaleColumn());
        
        container =new VBox();                
        container.getChildren().addAll(reportTable);                
                
        HBox buttonsContainer=new HBox(); 
        buttonsContainer.setAlignment(Pos.CENTER);
        buttonsContainer.setPadding(new Insets(10,10,10,10));               
        buttonsContainer.setSpacing(10);
        
        genReportB= new Button("Nuevo Producto");      
                      
        buttonsContainer.getChildren().addAll(genReportB);
        
        container.getChildren().addAll(buttonsContainer);        
        this.setContent(container);       
    }
}
