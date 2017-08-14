/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseproject;

import static databaseproject.TabRegister.registerTable;
import java.time.LocalDate;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;


public class TabReport extends Tab {
    VBox container;      
    static TableView<Register> reportTable=new TableView(RegisterTableUtil.getRegListToReport());       
    //generate report Button
    Button genReportB;
       
    HBox saleRateContainer;
    Label saleRateTag;
    Label saleRateLabel;
    
    HBox productContainer;
    Label productTag;
    ComboBox<Product> productComboBox;
    
    HBox dateContainer;
    Label dateTag;
    Label dashTag;
    DatePicker dateIni=new DatePicker();
    DatePicker dateFin=new DatePicker();
    
    public TabReport(String text){
        this.setText(text);   
        init();
    }
    private void init(){
        //datePicker.setValue(LocalDate.now());
        //datePicker.setEditable(false);
        container =new VBox(); 
        
        reportTable.setMinWidth(620);        
        reportTable.getColumns().addAll(RegisterTableUtil.getDateColumn(),
                                        RegisterTableUtil.getQuantitySoldColumn(),
                                        RegisterTableUtil.getCashSaleColumn());
        
                             
        saleRateContainer=new HBox();        
        saleRateTag=new Label("Tasa de venta : ");
        saleRateLabel=new Label("   ");
        saleRateContainer.getChildren().addAll(saleRateTag,saleRateLabel);
        saleRateContainer.setAlignment(Pos.CENTER);
        
        
        productContainer=new HBox();
        productTag=new Label("Producto : ");
        productComboBox=new ComboBox();
        productComboBox.setPromptText("Elija un producto");
        //the next line should be replaced with a db access
        productComboBox.getItems().addAll(new Product("Pantalon",10),
                                          new Product("Short",6),
                                          new Product("Pantalon",13));
        productContainer.getChildren().addAll(productTag,productComboBox);
        productContainer.setAlignment(Pos.CENTER);
      
        dateContainer=new HBox();
        dateTag=new Label("Entre : ");
        dashTag=new Label(" - ");
        dateIni.setValue(LocalDate.now());
        dateFin.setValue(LocalDate.now());
        dateContainer.getChildren().addAll(dateTag,dateIni,dashTag,dateFin);
        dateContainer.setAlignment(Pos.CENTER);
        
        container.getChildren().addAll(reportTable,saleRateContainer,productContainer,dateContainer);                
                
        HBox buttonsContainer=new HBox(); 
        buttonsContainer.setAlignment(Pos.CENTER);
        buttonsContainer.setPadding(new Insets(10,10,10,10));               
        buttonsContainer.setSpacing(10);
        
        genReportB= new Button("Nuevo Reporte");      
                      
        buttonsContainer.getChildren().addAll(genReportB);
        
        container.getChildren().addAll(buttonsContainer);        
        this.setContent(container);       
    }
}
