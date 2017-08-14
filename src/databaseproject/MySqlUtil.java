/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class MySqlUtil {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost/romeidb";   
    static final String USER = "root";
    static final String PASS = "";
   
   public static ArrayList<Register> getInfoDayBefore(String dateBefore){
    Connection conn = null;
    Statement stmt = null;
    ArrayList<Register> registers=new ArrayList();
    try{
      //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");
      //STEP 3: Open a connection
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);     
      //STEP 4: Execute a query
      System.out.println("Creating statement...");
      stmt = conn.createStatement();
      String sql;
      
      /* it gives nombre, producto, stockAlInicio
         trough a store procedure, previously created
      */
      sql="call getInfoDayBefore('" + dateBefore + "')" ;
      ResultSet rs = stmt.executeQuery(sql);
      
      //STEP 5: Extract data from result set      
      Register regTemp;
      Product prodTemp;
      while(rs.next()){
         //Retrieve by column name
         regTemp=new Register();        
         prodTemp=new Product();
         
         prodTemp.setName(rs.getString("Nombre"));
         prodTemp.setPrice(rs.getDouble("Precio"));
         
         regTemp.setProduct(prodTemp);
         
         regTemp.setInitialStock(rs.getInt("StockAlInicio"));
         registers.add(regTemp);
         /*
         String nameOfProduct=rs.getString("Nombre");
         double price=rs.getDouble("Precio");
         int initialStock=rs.getInt("StockAlInicio");         
         
         //Display values
         System.out.print("Name: " + nameOfProduct);
         System.out.print(", Price: " + price);
         System.out.println(", InitialStock: " + initialStock);
         */
      }
      //STEP 6: Clean-up environment
      rs.close();
      stmt.close();      
      conn.close();      
   }catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
      e.printStackTrace();
   }finally{
      //finally block used to close resources
      try{
         if(stmt!=null) stmt.close();
      }catch(SQLException se2){}// nothing we can do
      try{
         if(conn!=null) conn.close();
      }catch(SQLException se){ se.printStackTrace();}//end finally try
   }//end try   
   System.out.println("Goodbye!"); 
   return registers;
   }
}

