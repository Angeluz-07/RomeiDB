/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseproject;

import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 *
 * @author User
 */
public class PaneOrg {
    private BorderPane root;
    //private VBox menu;
    private TabPane tabPane;
    
    public PaneOrg(){
        root=new BorderPane();
        //menu=new VBox();
        
        /*
        Button register=new Button("Registro");
        Button addNewProduct=new Button("Nuevo Producto");
        Button report=new Button("Reporte");
        Button exit=new Button("Salir");
        */        
        //menu.getChildren().addAll(register,addNewProduct,report,exit);
        tabPane=new TabPane();               
        //this block to close tabs
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setSide(Side.LEFT);              
        //tabPane.setTabMinWidth(10);
        //tabPane.setTabMinHeight(200);
        //tabPane.setRotateGraphic(false);        
        //tabPane.getStyleClass().remove(TabPane.STYLE_CLASS_FLOATING);                
        
        
        //Creating tabs
        TabRegister tabRegister=new TabRegister("Registro");              
        TabAddNewProduct tabAddNewProduct=new TabAddNewProduct("Nuevo Producto");                               
        
        Tab report=new Tab("Reporte");                    
        tabPane.getTabs().addAll(tabRegister,tabAddNewProduct,report);        
        root.setCenter(tabPane);
        
        root.setMinSize(200, 200);
        //root.setLeft(menu);
    }

    public BorderPane getRoot() {
        return root;
    }
    
}
