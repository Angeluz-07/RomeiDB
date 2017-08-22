/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseproject;

import static databaseproject.TabRegister.registerTable;
import static databaseproject.Utils.setDateFormat;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.*;


public class TabReport extends Tab {
    VBox container;      
    static TableView<Register> reportTable;    
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
        Label l = new Label(text);
        l.setRotate(90);
        StackPane stp = new StackPane(new Group(l));
        stp.setRotate(90);
        this.setGraphic(stp);   
        init();
    }
    private void init(){
        reportTable=new TableView(RegisterTableUtil.getRegListToRegister());
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
        productComboBox.getItems().addAll(MySqlUtil.getCurrentProducts());
        productComboBox.setOnShowing(e->{
            ObservableList<Product> p=FXCollections.observableArrayList();
            p.setAll(MySqlUtil.getCurrentProducts());
            productComboBox.setItems(p);        
        });      
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
        
        genReportB= new Button("Generar Reporte");      
        genReportB.setOnAction(e->{
            String dateIniFormatted=setDateFormat(dateIni.getEditor().getText());            
            String dateFinFormatted=setDateFormat(dateFin.getEditor().getText());           
            
            ObservableList<Register> registers=FXCollections.observableArrayList();        
            Product p= productComboBox.getValue();
            registers.addAll(MySqlUtil.getRegToReport(dateIniFormatted,
                                                      dateFinFormatted,
                                                      p));            
            reportTable.setItems(registers);
            if(!registers.isEmpty()) reportTable.refresh();
        });
                      
        buttonsContainer.getChildren().addAll(genReportB);
        
        container.getChildren().addAll(buttonsContainer);        
        this.setContent(container);       
    }
}
