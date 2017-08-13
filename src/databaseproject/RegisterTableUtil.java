/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

/*
    I SHOULD LOAD DB INFO HERE
*/

public class RegisterTableUtil {
    
    /* Returns an observable list of registers */
    public static ObservableList<Register> getRegisterList() {       
        ObservableList<Register> registers=FXCollections.observableArrayList();        
        
        //exists a register object to be loaded, by each product.
        
        //now i create the register just with the initial stock
        Register r1 = new Register(new Product("Pantalon",10),20);
        Register r2 = new Register(new Product("Pantalon",13),30);
        Register r3 = new Register(new Product("Short",6),15);               
        registers.addAll(r1,r2,r3);        
        return registers;
    }
    
    /*
        The TableColumn<S, T> class is a generic class. The S parameter is the items type, which is of the same
        type as the parameter of the TableView.For
        example, an instance of the TableColumn<Person, Integer> may be used to represent a column to display
        the ID of a Person, which is of int type; an instance of the TableColumn<Person, String> may be used to
        represent a column to display the first name of a person, which is of String type. The following snippet of
        code creates a TableColumn with First Name as its header text:
        TableColumn<Person, String> fNameCol = new TableColumn<>("First Name");
    */
    
    /* Returns AddedOrRemovedStock TableColumn */
    public static  TableColumn<Register, Integer> getAddedOrRemovedStockColumn() {
        TableColumn<Register, Integer> addedOrRemovedStockCol = new TableColumn<>("Editar Stock");                
        /*below the parameter is the name of the
          attribute in the class Register*/
        addedOrRemovedStockCol.setCellValueFactory(new PropertyValueFactory<>("addedOrRemovedStock"));
        //make editable with text field
        addedOrRemovedStockCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));              

        addedOrRemovedStockCol.setId("addedOrRemovedStockColID");
        //updateCell is an event of CellEditEvent type
        addedOrRemovedStockCol.setOnEditCommit(updateCell->updateRegisterTable(updateCell));        
        return  addedOrRemovedStockCol;
    }
                
    /* Returns Product TableColumn */
    public static TableColumn<Register, Product> getProductColumn() {
        TableColumn<Register, Product> productCol = new TableColumn<>("Producto");
        productCol.setCellValueFactory(new PropertyValueFactory<>("product"));
        //not editable
        productCol.setEditable(false);        
        return productCol;
    }
    
    /* Returns Initial Stock TableColumn */
    public static TableColumn<Register, Integer> getInitialStockColumn() {
        TableColumn<Register, Integer> initialStockCol = new TableColumn<>("Stock Inicial");
        initialStockCol.setCellValueFactory(new PropertyValueFactory<>("initialStock"));
        //not editable
        initialStockCol.setEditable(false);
        
        return initialStockCol;
    }

    /* Returns Final Stock TableColumn */
    public static TableColumn<Register, Integer> getFinalStockColumn() {
        TableColumn<Register, Integer> finalStockCol = new TableColumn<>("Stock Final");
        finalStockCol.setCellValueFactory(new PropertyValueFactory<>("finalStock"));
        //string converter
        finalStockCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));               
        
        finalStockCol.setId("finalStockColID");
        //updateCell is an event of CellEditEvent type        
        finalStockCol.setOnEditCommit(updateCell->updateRegisterTable(updateCell));
        return finalStockCol;
    }
    
    /* Returns Quantity Sold TableColumn */
    public static TableColumn<Register, Integer> getQuantitySoldColumn() {
        TableColumn<Register, Integer> quantitySoldCol =new TableColumn<>("Cantidad Vendida");
        quantitySoldCol.setCellValueFactory(new PropertyValueFactory<>("quantitySold"));
        //not editable
        quantitySoldCol.setEditable(false);
        
        return quantitySoldCol;
    }
    /* Returns Cash Sale TableColumn */
    public static TableColumn<Register, Double> getCashSaleColumn() {
        TableColumn<Register, Double> cashSaleCol =new TableColumn<>("Venta($)");
        cashSaleCol.setCellValueFactory(new PropertyValueFactory<>("cashSale"));
        //not editable
        cashSaleCol.setEditable(false);
        
        return cashSaleCol;
    }
        
    
    private static void updateRegisterTable(CellEditEvent updateCell){
        //this line gets the register Object asociated with cell selected by the user                                                                       
        Register r=(Register)updateCell.getTableView().getSelectionModel().getSelectedItem();                                   
        //get the column the event ocurred and get its id
        switch(updateCell.getTableColumn().getId()){
            case "addedOrRemovedStockColID":
                r.setAddedOrRemovedStock((Integer)updateCell.getNewValue());
                break;
            case "finalStockColID":
                r.setFinalStock((Integer)updateCell.getNewValue());
                break;
        }
        r.computeQuantitySold();
        r.computeCashSales();
        
        /*get all the Register Objects to compute 
          the sum of cash sales */
        ObservableList<Register> registers=updateCell.getTableView().getItems();        
        System.out.println(sumCashSales(registers));                        

        TabRegister.totalValLabel.setText(String.valueOf(sumCashSales(registers)));                        
        
        updateCell.getTableView().refresh();
        System.out.println(r.toString());        
    }    
    
    public static double sumCashSales(ObservableList<Register> registers){
     double s=0;
     for(Register r: registers){
        s+=r.getCashSale();
     }
     return s;
    }
}

