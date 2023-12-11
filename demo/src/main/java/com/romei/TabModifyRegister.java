/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseproject;
import static databaseproject.Utils.setDateFormat;
import static databaseproject.Utils.showErrorDialog;
import static databaseproject.Utils.showInfoDialog;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 *
 * @author User
 */
public class TabModifyRegister extends Tab {
    VBox container;
    static DatePicker datePicker=new DatePicker();
    static TableView<Register> registerTable;   
    static Label totalValLabel;
    Button newRegB;
    Button updateRegB;
        
    static int userID; 
    static ArrayList<Object> data;
    
    public TabModifyRegister(String text){
        Label l = new Label(text);
        l.setRotate(90);
        StackPane stp = new StackPane(new Group(l));
        stp.setRotate(90);
        this.setGraphic(stp); 
        init();
    }
    private void init(){        
        registerTable=new TableView(RegisterTableUtil.getRegToUpdateReg()); 
        registerTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        datePicker.setEditable(false);        
        datePicker.setValue(LocalDate.now());       
        //registerTable.setMinWidth(620);
        registerTable.setEditable(true);
        
        registerTable.getColumns().addAll(RegisterTableUtil.getDateColumn(),
                                          RegisterTableUtil.getProductColumn(),
                                          RegisterTableUtil.getInitialStockColumn(),
                                          RegisterTableUtil.getFinalStockColumn(),
                                          RegisterTableUtil.getQuantitySoldColumn(),
                                          RegisterTableUtil.getCashSaleColumn());
        
        container =new VBox();                        
        container.getChildren().addAll(registerTable);                
        
        HBox resultContainer=new HBox();                
        resultContainer.setAlignment(Pos.CENTER_RIGHT);
        Label totalLabel=new Label("Total : ");       
        totalValLabel=new Label("      ");
        
        resultContainer.getChildren().addAll(totalLabel,totalValLabel);
        
        HBox buttonsContainer=new HBox(); 
        buttonsContainer.setAlignment(Pos.CENTER);
        buttonsContainer.setPadding(new Insets(10,10,10,10));       
        buttonsContainer.setSpacing(10);
        newRegB= new Button("Refrescar Tabla");      
        newRegB.setOnAction(e->{    
            registerTable.setItems(RegisterTableUtil.getRegToUpdateReg());            
        });
        updateRegB= new Button("Actualizar");        
        updateRegB.setOnAction(e->{
            ObservableList<Register> registers=registerTable.getItems();                                      
            Register r;
            boolean flag=true;            
            for(int i=0;i<registers.size();i++){
                data=new ArrayList();                    
                r=registers.get(i);
                Collections.addAll(data,r.getInitialStock(),
                                        r.getFinalStock(),
                                        r.getQuantitySold(),
                                        r.getCashSale(),
                                        r.getRegisterId(),
                                        r.getAddedOrRemovedStock());                
                String query="call updateRegister(?,?,?,?,?)";
                flag=MySqlUtil.queryWithData(data.subList(0, 5),query,"TabRegister to update registers");
                if(r.getAddedOrRemovedStock()!=0){          
                    String query2="";
                    query2+="call insertAORS(?)"; 
                    flag=MySqlUtil.queryWithData(data.subList(6, 7), query2, "TabRegister to insert AORStock");
                }
                if(!flag){
                  showErrorDialog("Un error ocurrio durante la transaccion");                    
                  break;
                }
            }
            if(flag) showInfoDialog("La operacion se realizo con exito!");      
            else    showErrorDialog("Un error ocurrio durante la transaccion");                    
        });
        
        buttonsContainer.getChildren().addAll(newRegB,updateRegB);
        
        container.getChildren().addAll(buttonsContainer);        
        this.setContent(container);
    }

    public static TableView<Register> getRegisterTable() {
        return registerTable;
    }

}
