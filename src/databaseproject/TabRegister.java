/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseproject;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;

/**
 *
 * @author User
 */
public class TabRegister extends Tab {
    DatePicker date=new DatePicker();
    static TableView<Register> registerTable=new TableView(RegisterTableUtil.getRegisterList());
    
    public TabRegister(String text){
        this.setText(text);   
        init();
    }
    private void init(){        
        date.setEditable(false);        
        
        registerTable.setMinWidth(505);
        registerTable.setEditable(true);
        
        registerTable.getColumns().addAll(RegisterTableUtil.getAddedOrRemovedStockColumn(),
                                          RegisterTableUtil.getProductColumn(),
                                          RegisterTableUtil.getInitialStockColumn(),
                                          RegisterTableUtil.getFinalStockColumn(),
                                          RegisterTableUtil.getQuantitySoldColumn(),
                                          RegisterTableUtil.getCashSaleColumn());
        GridPane grid =new GridPane();        
        grid.addRow(0, date);        
        grid.addRow(1, registerTable);
        this.setContent(grid);
    }

    public static TableView<Register> getRegisterTable() {
        return registerTable;
    }
    
    
}
