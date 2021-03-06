/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseproject;

import static databaseproject.Utils.showErrorDialog;
import static databaseproject.Utils.showInfoDialog;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
   
    static int userID;
     //get register list to register table
   public static ArrayList<Register> getRegToReg(){
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
      String sql="call getCurrentStock()";      
      
      // preparo la sentencia que voy a ejecutar
      System.out.println("Cooking the statement...");
      pstm = conn.prepareStatement(sql);
      
      // ejecuto la sentencia y obtengo los resultados en rs      
      rs = pstm.executeQuery();
      
      // itero los resultados              
      while(rs.next()){
        //Retrieve data row by row  
        String productName=rs.getString("ProductName");
        double price=rs.getDouble("Price");
        /* Remember, from db you retrive the current stock
           from the stored proced getCurrentStock(). That
           value now is gonna be your initial stock 
        */
        int initialStock=rs.getInt("CurrentStock");
        
        //get ProductPriceID to save later in register
        int productPriceID=rs.getInt("ProductPriceID");
        
        //Create objects with data obtained
        //initialize objects
        registerTemp=new Register();        
        productTemp=new Product();
         
        //set the values
         productTemp.setName(productName);
         productTemp.setPrice(price);
         productTemp.setProductPriceID(productPriceID);
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
   
   //get registers to update
   public static ArrayList<Register> getRegToUpdateReg(){
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
      String sql="select * from Registers";      
      
      // preparo la sentencia que voy a ejecutar
      System.out.println("Cooking the statement...");
      pstm = conn.prepareStatement(sql);
      // ejecuto la sentencia y obtengo los resultados en rs      
      rs = pstm.executeQuery();
      
      // itero los resultados              
      while(rs.next()){
        //Retrieve data row by row  
        int registerId=rs.getInt("RegisterID");
        int userId=rs.getInt("UserID");
        String registerDate=rs.getString("RegisterDate");
        int productPriceId=rs.getInt("ProductPriceID");
        int initialStock=rs.getInt("InitialStock");
        int finalStock=rs.getInt("FinalStock");
        int quantitySold=rs.getInt("QuantitySold");
        double cashSale=rs.getDouble("CashSale");
        
        //Create objects with data obtained
        //initialize objects
        registerTemp=new Register();        
        productTemp=new Product();      
        registerTemp.setRegisterId(registerId);
        registerTemp.setUserId(userId);
        registerTemp.setDate(registerDate);
        for(Product p: MySqlUtil.getCurrentProducts()){
            if(p.getProductPriceID()==productPriceId){
                registerTemp.setProduct(new Product(p.getName(),p.getPrice()));              
                break;
            }
        }
        registerTemp.setProductPriceId(productPriceId);
        registerTemp.setInitialStock(initialStock);
        registerTemp.setFinalStock(finalStock);
        registerTemp.setQuantitySold(quantitySold);
        registerTemp.setCashSale(cashSale);
                       
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
   
   
   
   
   //get registers to report
   public static ArrayList<Register> getRegToReport(String di,String df, Product p){
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
      
      String sql="call getregisterinfoinrange(?,?,?)";      
      
      // preparo la sentencia que voy a ejecutar
      System.out.println("Cooking the statement...");
      pstm = conn.prepareStatement(sql);
      pstm.setInt(1, p.getProductPriceID());
      pstm.setDate(2, java.sql.Date.valueOf(di));
      pstm.setDate(3,java.sql.Date.valueOf(df));
      // ejecuto la sentencia y obtengo los resultados en rs      
      rs = pstm.executeQuery();
      
      // itero los resultados              
      while(rs.next()){
        //Retrieve data row by row        
        String date=rs.getString("RegisterDate");
        int quantitySold=rs.getInt("QuantitySold");        
        double cashSale=rs.getDouble("CashSale");       
        //initialize objects
        registerTemp=new Register(date,quantitySold,cashSale);                              
        registers.add(registerTemp);         
      }      
    if(registers.isEmpty()){
        showInfoDialog("No existe esa informacion acerca del producto");
        return registers;
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
      
   public static ArrayList<Product> getCurrentProducts(){
    Connection conn = null;
    PreparedStatement pstm = null;
    ResultSet rs = null;    
    
    /*Variables to create the objects*/              
    ArrayList<Product> products=new ArrayList();      
    Product productTemp;
    Supplier supplierTemp;
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
      String sql="call getCurrentProducts()";      
      
      // preparo la sentencia que voy a ejecutar
      System.out.println("Cooking the statement...");
      pstm = conn.prepareStatement(sql);
      
      // ejecuto la sentencia y obtengo los resultados en rs      
      rs = pstm.executeQuery();
      
      // itero los resultados              
      while(rs.next()){
        //Retrieve data row by row        
        int productID=rs.getInt("ProductId");
        String productName=rs.getString("ProductName");
        int productPriceID=rs.getInt("ProductPriceId");        
        double price=rs.getDouble("Price");
        int supplierID=rs.getInt("SupplierID");
        String contactName=rs.getString("ContactName");
        
        //Create objects with data obtained      
        supplierTemp=new Supplier(supplierID,contactName);        
        productTemp=new Product(productName,price,productPriceID,productID,supplierTemp);       
        
        products.add(productTemp);
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
   return products;
   }
   
   public static ArrayList<Supplier> getCurrentSuppliers(){
    Connection conn = null;
    PreparedStatement pstm = null;
    ResultSet rs = null;    
    
    /*Variables to create the objects*/              
    ArrayList<Supplier> suppliers=new ArrayList();          
    Supplier supplierTemp;
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
      String sql="select ";
      sql+=" SupplierID,ContactName,Phone ";
      sql+=" from Suppliers ";
      sql+=" where inDB=1";      
      
      // preparo la sentencia que voy a ejecutar
      System.out.println("Cooking the statement...");
      pstm = conn.prepareStatement(sql);
      
      // ejecuto la sentencia y obtengo los resultados en rs      
      rs = pstm.executeQuery();
      
      // itero los resultados              
      while(rs.next()){
        //Retrieve data row by row               
        int supplierID=rs.getInt("SupplierID");
        String contactName=rs.getString("ContactName");
        String phone=rs.getString("Phone");
        
        //Create objects with data obtained      
        supplierTemp=new Supplier(supplierID,contactName,phone);                
        suppliers.add(supplierTemp);
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
   return suppliers;
   }
   
    public static ArrayList<User> getCurrentUsers(){
    Connection conn = null;
    PreparedStatement pstm = null;
    ResultSet rs = null;    
    
    /*Variables to create the objects*/              
    ArrayList<User> users=new ArrayList();          
    User userTemp;
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
      
      String sql="select ";
      sql+=" UserID,FirstName,LastName,UserName,Password  ";
      sql+=" from Users ";
      sql+=" where inDB=1";      
      
      // preparo la sentencia que voy a ejecutar
      System.out.println("Cooking the statement...");
      pstm = conn.prepareStatement(sql);
      
      // ejecuto la sentencia y obtengo los resultados en rs      
      rs = pstm.executeQuery();
      
      // itero los resultados              
      while(rs.next()){
        //Retrieve data row by row               
        int uID=rs.getInt("UserID");
        String firstName=rs.getString("firstName");
        String lastName=rs.getString("LastName");
        String userName=rs.getString("UserName");
        String password=rs.getString("Password");
        //Create objects with data obtained      
        userTemp=new User(uID,firstName,lastName,userName,password);                
        users.add(userTemp);
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
    return users;
   }
   
   
   /*
     return true if the field exist on db. 
     THE NUMBER OF ELEMENTS IN fields SHOULD
     BE THE SAME AS THAT REQUIRED IN THE QUERY.   
   */
   public static List<Object> dataIsInDB(List<Object> fields,String query, String place){
    Connection conn = null;
    PreparedStatement pstm = null;
    ResultSet rs = null;       
    List<Object> result=new ArrayList();
    try{
      // -------------
      // ** PARTE 1 **
      // -------------
      // levanto el driver
      Class.forName(JDBC_DRIVER);            
      // establezco la conexion
      System.out.printf("Connecting to database in %s...\n",place);
      conn = DriverManager.getConnection(DB_URL,USER,PASS);     
      
      // -------------
      // ** PARTE 2 **
      // -------------
      //defino un query          
      String sql=query;           
      // preparo la sentencia que voy a ejecutar
      System.out.printf("Cooking the statement in %s...\n",place);
      pstm = conn.prepareStatement(sql);
      
      for(int i=1;i<=fields.size();i++){
          //System.out.println(i+" "+fields.get(i-1));
          pstm.setObject(i,fields.get(i-1));       
      }           
      // ejecuto la sentencia y obtengo los resultados en rs      
      rs = pstm.executeQuery();  
      
      int columnCount=rs.getMetaData().getColumnCount();
      //if there is next it means exist on db      
      while(rs.next()){ 
          int i = 1;
          while(i <= columnCount) {
            result.add(rs.getObject(i++));
          }        
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
   System.out.printf("Serving the statement in %s GoodBye!\n",place);        
   return result;
   }    
    
    public static boolean queryWithData(List<Object> data,String query,String place){
    Connection conn = null;
    PreparedStatement pstm = null;
    //ResultSet rs = null;           
    boolean success=true;
    try{
      // -------------
      // ** PARTE 1 **
      // -------------
      // levanto el driver
      Class.forName(JDBC_DRIVER);      
      // establezco la conexion
      System.out.printf("Connecting to database in %s...\n",place);
      conn = DriverManager.getConnection(DB_URL,USER,PASS);     
      
      // -------------
      // ** PARTE 2 **
      // -------------
      //defino un query          
     
      String sql=query;
      // preparo la sentencia que voy a ejecutar
      System.out.printf("Cooking the statement in %s...\n",place);
      pstm = conn.prepareStatement(sql);
      for(int i=1;i<=data.size();i++){
          //System.out.println(i+" "+fields.get(i-1));
          pstm.setObject(i,data.get(i-1));       
      }                 
      // ejecuto la sentencia y en este caso no obtengo resultados              
      int rs = pstm.executeUpdate();             
      if(rs!=1) success=false;      
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
        if( pstm!=null ) pstm.close();     
        if(conn!=null) conn.close();
      }catch(Exception e){ 
        e.printStackTrace();
        throw new RuntimeException(e);
      }//end finally try
   }//end try   
    System.out.printf("Serving the statement in %s GoodBye!\n",place);        
    return success;
   }    
}
