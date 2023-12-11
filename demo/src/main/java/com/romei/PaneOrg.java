/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseproject;

import static databaseproject.RomeiDB.window;
import javafx.geometry.Side;
import javafx.scene.Group;
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
import javafx.scene.layout.StackPane;
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
        //root.setStyle("-fx-background-color: linear-gradient(to bottom left,#cc5333,#23074d)");
        tabPane=new TabPane();               
        
        //this block to close tabs
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setSide(Side.LEFT);
        tabPane.setRotateGraphic(true);
        
        //tabPane.setTabMinWidth(10);
        //tabPane.setTabMinHeight(200);
        //tabPane.setRotateGraphic(false);        
        //tabPane.getStyleClass().remove(TabPane.STYLE_CLASS_FLOATING);                        
        
        //Creating tabs
        TabRegister tabRegister=new TabRegister("Registro");                    
        TabAddProduct tabAddNewProduct=new TabAddProduct("Agregar Producto");                               
        TabReport tabReport=new TabReport("Reporte Ventas");
        TabAddUser tabAddUser=new TabAddUser("Agregar Usuario");
        TabAddSupplier tabAddSupplier=new TabAddSupplier("Agregar Proveedor");
        TabModifyProduct tabModifyProduct=new TabModifyProduct("Modificar producto");
        TabModifySupplier tabModifySupplier=new TabModifySupplier("Modificar proveedor");
        TabModifyUser tabModifyUser=new TabModifyUser("Modificar Usuario");
        TabModifyRegister tabModifyRegister=new TabModifyRegister("Modificar Registro");
                tabPane.getTabs().addAll(tabRegister,
                                 tabReport,
                                 tabAddNewProduct, 
                                 tabModifyProduct,
                                 tabAddUser,
                                 tabModifyUser,
                                 tabAddSupplier,
                                 tabModifySupplier);        
        tabExit= new Tab();   
        Label l = new Label("Exit");
        l.setRotate(90);
        StackPane stp = new StackPane(new Group(l));
        stp.setRotate(90);
        tabExit.setGraphic(stp);
        
        tabLogOut=new Tab();        
        Label l2 = new Label("Cerrar Sesion");
        l2.setRotate(90);
        StackPane stp2 = new StackPane(new Group(l2));
        stp2.setRotate(90);
        tabLogOut.setGraphic(stp2);
        tabExit.setOnSelectionChanged(e->window.close());
        
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
