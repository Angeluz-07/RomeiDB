package com.romei.sandbox;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author User
 */
public class DBtest1 {
     // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   //static final String DB_URL = "jdbc:mysql://localhost/northwind";
   static final String DB_URL = "jdbc:mysql://localhost/romeidb";


   //  Database credentials
   static final String USER = "root";
   static final String PASS = "";
   
   public static void main(String[] args) {
   Connection conn = null;
   Statement stmt = null;
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
      //it gives nombre, producto, stockAlInicio
      sql="call getInfoDayBefore('2017-08-13')";
      //sql = "SELECT id, first_name, city FROM Employees";      
      
      ResultSet rs = stmt.executeQuery(sql);

      //STEP 5: Extract data from result set
      while(rs.next()){
         //Retrieve by column name        
         String nameOfProduct=rs.getString("Nombre");
         double price=rs.getDouble("Precio");
         int initialStock=rs.getInt("StockAlInicio");
         //int id  = rs.getInt("id");
         //String first_name = rs.getString("first_name");
         //String city = rs.getString("city");
         
         //Display values
         System.out.print("Name: " + nameOfProduct);
         System.out.print(", Price: " + price);
         System.out.println(", InitialStock: " + initialStock);
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
   }
}
