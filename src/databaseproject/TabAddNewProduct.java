/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseproject;

import java.time.LocalDate;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;

/**
 *
 * @author User
 */
public class TabAddNewProduct extends Tab {
    DatePicker date=new DatePicker();
    
    public TabAddNewProduct(String text){
        this.setText(text);   
        init();
    }
    private void init(){
        date.setValue(LocalDate.now());
        GridPane grid =new GridPane();
        date.setEditable(false);
        grid.addRow(0, date);
        this.setContent(grid);
    }
}
