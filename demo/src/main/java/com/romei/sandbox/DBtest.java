package com.romei.sandbox;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class DBtest {
    static String url = "jdbc:mysql://localhost:3306/sakila"; 
    static String driver = "com.mysql.jdbc.Driver";  
    static String userName = "root"; 
    static String password = ""; 

    public static void main(String[] args) {            
        String url="jdbc:mysql://localhost/mysql";                               // Setup Connection URL
        String userid="root";                                                   // Default username is root
        String password="";                                                     // Default Password is empty string
        
        try
        {
            Scanner sc = new Scanner(System.in);                                // For reading console input
            
            System.out.println("\n Enter name : ");
            String name = sc.next();
            
            String query = " insert into dummy values('"+name+"')";             // Create query string to insert name into table
            
            Class.forName("com.mysql.jdbc.Driver");                             // Load Driver class
            
            Connection con = DriverManager.getConnection(url,userid,password);  // Connect to Database using credentials
            
            Statement stmt = con.createStatement();                             // Create statement
            
            stmt.executeUpdate(query);                                          // Execute Query
            
            con.close();                                                        // Close the open connection
            
            System.out.println("\n Data Inserted !");
        }
        catch(Exception e)
        {
            System.out.println("\n Error : "+e);
        }   
            
        /*
        // Load Driver class        
        // use try-with-resources to connect to and query the database
        try (
            // Connect to Database using credentials
            Connection connection = DriverManager.getConnection(url, userid,password);            
            // create statement
            Statement statement = connection.createStatement();
            //execute query
            ResultSet resultSet = statement.executeQuery(SELECT_QUERY))
        {
            // Default Password is empty string
            Class.forName(driver);   
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            System.out.printf("Authors Table of Books Database:%n%n");

            // display the names of the columns in the ResultSet
            for (int i = 1; i <= numberOfColumns; i++)
                System.out.printf("%-8s\t",metaData.getColumnName(i));

            System.out.println();

            while (resultSet.next()){
                for (int i = 1; i <= numberOfColumns; i++)
                    System.out.printf("%-8s\t", resultSet.getObject(i));
                    System.out.println();
            }   
            //close the connection
            connection.close();
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }catch(ClassNotFoundException e){
            System.out.println(e);
        }*/
    }
    
}
