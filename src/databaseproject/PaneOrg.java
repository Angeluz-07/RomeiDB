/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseproject;

import static databaseproject.RomeiDB.window;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author User
 */
public class PaneOrg {
    private BorderPane root;
    //private VBox menu;
    static TabPane tabPane;
    static Tab tabExit;
    static Tab tabLogOut;
    private Login login;
    
    public PaneOrg(){
        root=new BorderPane();
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
        TabReport tabReport=new TabReport("Reporte");
        TabAddUser tabAddUser=new TabAddUser("Agregar Usuario");
        tabPane.getTabs().addAll(tabRegister,tabAddNewProduct,tabReport,tabAddUser);        
        tabExit= new Tab("Salir");   
        tabExit.setOnSelectionChanged(e->window.close());
        
        tabLogOut=new Tab("Cerrar Sesion");
        
        tabLogOut.setOnSelectionChanged(e->{
            Login log=new Login();
            RomeiDB.sceneLogIn = new Scene(log.getLoginPane(), 820, 600);
            RomeiDB.sceneLogIn.getStylesheets().add(getClass().getResource("Style.css").toExternalFo‌​rm());                               
            window.setScene(RomeiDB.sceneLogIn);
        });
                
        tabPane.getTabs().addAll(tabLogOut,tabExit);        
        
        root.setCenter(tabPane);                        
        root.setMinSize(800, 800);
    } 
    
    public BorderPane getRoot() {
        return root;
    }
    
}
