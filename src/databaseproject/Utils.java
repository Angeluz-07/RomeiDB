/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseproject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author User
 */
public class Utils {
    public static boolean thereAreEmptyFields(ArrayList<String> fields,Text actiontarget){
        for(String field:fields){
            if(field.isEmpty()){ 
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("Por favor ingrese todos los campos");            
                return true;
            }
        }
        actiontarget.setText("");
        return false;
    }
    public static boolean bothFieldsEqual(String s1,String s2,Text actiontarget){
        if(s1.equals(s2)){
            actiontarget.setText("");
            return true;
        }else{
            actiontarget.setFill(Color.FIREBRICK);
            actiontarget.setText("Las contraseñas no coinciden");            
            return false;
        }   
    }
    
    public static void showErrorDialog(String msg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(RomeiDB.window);        
        //s.getIcons().add(new Image("images/logo_full3.png"));
        //s.getIcons().add(new Image("databasproject/icon.png"));        
        alert.setTitle("");
        alert.setHeaderText(null);
        alert.setContentText(msg);      
        alert.showAndWait();       
    }
    
    public static void showInfoDialog(String msg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(RomeiDB.window);
        //alert.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        //alert.initOwner(alert.getDialogPane().getScene().getWindow());
        //Stage s= (Stage) alert.getDialogPane().getScene().getWindow();
        //s.getIcons().add(new Image("/icon.png"));        
        alert.setTitle("");
        alert.setHeaderText(null);
        alert.setContentText(msg);      
        alert.showAndWait();       
    }
    
    public static String setDateFormat(String date){
            /* i get an date string as "14/08/2017" */            
            DateFormat to= new SimpleDateFormat("yyyy-MM-dd");//wanted format
            DateFormat from=new SimpleDateFormat("dd/MM/yyyy");//current format            
            String dateFormatted=null;
            try{
                dateFormatted=to.format(from.parse(date));    
            }catch(ParseException e){                
                System.out.println(e);
            }
            return dateFormatted;
    }
}
