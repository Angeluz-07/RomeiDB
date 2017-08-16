/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author User
 */
public class MySqlUtil {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost/romeidb";   
    static final String USER = "root";
    static final String PASS = "";
   
   public static ArrayList<Register> getInfoLastRegister(){
    Connection conn = null;
    PreparedStatement pstm = null;
    ResultSet rs = null;    
    
    /*Variables to create the objects*/              
    ArrayList<Register> registers=new ArrayList();    
    Register registerTemp;
    Product productTemp;
    try{
      // -------------
      // ** PARTE 1 **
      // -------------
      // levanto el driver
      Class.forName(JDBC_DRIVER);      
      // establezco la conexion
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);     
      
      // -------------
      // ** PARTE 2 **
      // -------------
      //defino un query      
      /* 
        it gives nombre, producto, stockAlInicio
        trough a store procedure, previously created
      */
      String sql="call getInfoDayBefore()";      
      
      // preparo la sentencia que voy a ejecutar
      System.out.println("Cooking the statement...");
      pstm = conn.prepareStatement(sql);
      
      // ejecuto la sentencia y obtengo los resultados en rs      
      rs = pstm.executeQuery();
      
      // itero los resultados              
      while(rs.next()){
        //Retrieve data row by row        
        String nameOfProduct=rs.getString("Nombre");
        double price=rs.getDouble("Precio");
        int initialStock=rs.getInt("StockAlInicio");
        
        //Create objects with data obtained
        //initialize objects
        registerTemp=new Register();        
        productTemp=new Product();
         
        //set the values
         productTemp.setName(nameOfProduct);
         productTemp.setPrice(price);
         registerTemp.setInitialStock(initialStock);         
         
         registerTemp.setProduct(productTemp);         
         registers.add(registerTemp);         
      }        
   }catch(Exception e){
      e.printStackTrace();     
      throw new RuntimeException(e);
   }finally{
      // -------------
      // ** PARTE 3 **
      // -------------
      // cierro todos los recursos en orden inverso al que
      // fueron adquiridos
      try{
        if( rs!=null ) rs.close();
        if( pstm!=null ) pstm.close();     
        if(conn!=null) conn.close();
      }catch(Exception e){ 
        e.printStackTrace();
        throw new RuntimeException(e);
      }//end finally try
   }//end try   
   System.out.println("Goodbye!"); 
   return registers;
   }
   
   public static boolean checkLogin(String userName,String password){
    Connection conn = null;
    PreparedStatement pstm = null;
    ResultSet rs = null;    
   
    //if exist user, the var below is true, else false
    boolean exist;
    try{
      // -------------
      // ** PARTE 1 **
      // -------------
      // levanto el driver
      Class.forName(JDBC_DRIVER);      
      // establezco la conexion
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);     
      
      // -------------
      // ** PARTE 2 **
      // -------------
      //defino un query      
    
      String sql="select nombre,password from usuario where nombre=? and password=?";           
      // preparo la sentencia que voy a ejecutar
      System.out.println("Cooking the statement...");
      pstm = conn.prepareStatement(sql);
      pstm.setString(1,userName);
      pstm.setString(2,password);
      // ejecuto la sentencia y obtengo los resultados en rs      
      rs = pstm.executeQuery();
      
      // itero los resultados              
      if(rs.next()){
          System.out.println(rs.getString("nombre")+" "+rs.getString("password"));
          exist=true;
      }else{
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText(null);
        alert.setContentText("El usuario no esta registrado");      
        alert.showAndWait();
        exist= false;
      }
             
   }catch(Exception e){
      e.printStackTrace();     
      throw new RuntimeException(e);
   }finally{
      // -------------
      // ** PARTE 3 **
      // -------------
      // cierro todos los recursos en orden inverso al que
      // fueron adquiridos
      try{
        if( rs!=null ) rs.close();
        if( pstm!=null ) pstm.close();     
        if(conn!=null) conn.close();
      }catch(Exception e){ 
        e.printStackTrace();
        throw new RuntimeException(e);
      }//end finally try
   }//end try   
   System.out.println("Goodbye!"); 
   return exist;
   }
}

