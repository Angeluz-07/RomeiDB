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
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

/*
    I SHOULD Save to DB from HERE
*/

public class AddProductTableUtil {
    
    /* Returns an observable list. for now just one Product(empty) */
    public static ObservableList<Product> getRegisterList() {       
        ObservableList<Product> products=FXCollections.observableArrayList();        
        Product p = new Product();
        products.add(p);        
        return products;
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
    
    /* Returns NameofProduct TableColumn */
    public static  TableColumn<Product, String> getNameOfProductColumn() {
        TableColumn<Product, String> nameOfProductCol = new TableColumn<>("Nombre del Producto");                
        /*below the parameter is the name of the
          attribute in the class Product*/
        nameOfProductCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        //make editable with textfield
        nameOfProductCol.setCellFactory(TextFieldTableCell.forTableColumn());              
        nameOfProductCol.setId("nameOfProductColID");
        
        //updateCell is an event of CellEditEvent type     
        nameOfProductCol.setOnEditCommit(updateCell->updateAddProductTable(updateCell));
        return  nameOfProductCol;
    }
       
    
    /* Returns Price TableColumn */
    public static  TableColumn<Product, Double> getPriceColumn() {
        TableColumn<Product, Double> priceCol = new TableColumn<>("Precio");                
        /*below the parameter is the name of the
          attribute in the class Product*/
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        //make editable with textfield
        priceCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));              
        
        priceCol.setId("priceColID");
        //updateCell is an event of CellEditEvent type     
        priceCol.setOnEditCommit(updateCell->updateAddProductTable(updateCell));                
        return  priceCol;
    }
    
     /* Returns Price TableColumn */
    public static  TableColumn<Product, Integer> getQuantityColumn() {
        TableColumn<Product, Integer> quantityCol = new TableColumn<>("Cantidad");                
        /*below the parameter is the name of the
          attribute in the class Product*/
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        
        //make editable with textfield
        quantityCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));              
        
        quantityCol.setId("quantityColID");
        //updateCell is an event of CellEditEvent type
        quantityCol.setOnEditCommit(updateCell->updateAddProductTable(updateCell));             
        return  quantityCol;
    }
    
    private static void updateAddProductTable(CellEditEvent updateCell){
        //this line gets the Product Object asociated with cell selected by the user                                                                       
        Product p=(Product)(updateCell.getTableView().getSelectionModel().getSelectedItem());                                   
        switch(updateCell.getTableColumn().getId()){
            case "nameOfProductColID":
                p.setName((String)updateCell.getNewValue());
                break;   
            case "priceColID":
                p.setPrice((Double)updateCell.getNewValue());
                break;
            case "quantityColID":
                p.setQuantity((Integer)updateCell.getNewValue());
                break;
        }
        updateCell.getTableView().refresh();
        System.out.println(p.toString());
    } 
}

