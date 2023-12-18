package com.romei;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MysqlRepository implements IDBRepository {

  static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
  static final String DB_URL = "jdbc:mysql://localhost/romeidb";
  static final String USER = "root";
  static final String PASS = "root_password";

  static int userID;

  /*
   * return true if the field exist on db.
   * THE NUMBER OF ELEMENTS IN fields SHOULD
   * BE THE SAME AS THAT REQUIRED IN THE QUERY.
   */
  public List<Object> dataIsInDB(List<Object> fields, String query, String place) {
    Connection conn = null;
    PreparedStatement pstm = null;
    ResultSet rs = null;
    List<Object> result = new ArrayList<>();
    try {
      // -------------
      // ** PARTE 1 **
      // -------------
      // levanto el driver
      Class.forName(JDBC_DRIVER);
      // establezco la conexion
      System.out.printf("Connecting to database in %s...\n", place);
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      System.out.println(conn.getClientInfo());
      System.out.println(conn.isValid(5));
      System.out.println(conn.getCatalog());
      System.out.println(conn.getSchema());
      // -------------
      // ** PARTE 2 **
      // -------------
      // defino un query
      String sql = query;
      System.out.println(sql);
      // preparo la sentencia que voy a ejecutar
      System.out.printf("Cooking the statement in %s...\n", place);

      pstm = conn.prepareStatement(sql);

      for (int i = 1; i <= fields.size(); i++) {
        System.out.println(i + " " + fields.get(i - 1));
        pstm.setObject(i, fields.get(i - 1));
      }
      // ejecuto la sentencia y obtengo los resultados en rs
      rs = pstm.executeQuery();

      int columnCount = rs.getMetaData().getColumnCount();
      // if there is next it means exist on db
      while (rs.next()) {
        int i = 1;
        while (i <= columnCount) {
          result.add(rs.getObject(i++));
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    } finally {
      // -------------
      // ** PARTE 3 **
      // -------------
      // cierro todos los recursos en orden inverso al que
      // fueron adquiridos
      try {
        if (rs != null)
          rs.close();
        if (pstm != null)
          pstm.close();
        if (conn != null)
          conn.close();
      } catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException(e);
      } // end finally try
    } // end try
    System.out.printf("Serving the statement in %s GoodBye!\n", place);
    return result;
  }

  public boolean queryWithData(List<Object> data, String query, String place) {
    Connection conn = null;
    PreparedStatement pstm = null;
    // ResultSet rs = null;
    boolean success = true;
    try {
      // -------------
      // ** PARTE 1 **
      // -------------
      // levanto el driver
      Class.forName(JDBC_DRIVER);
      // establezco la conexion
      System.out.printf("Connecting to database in %s...\n", place);
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      // -------------
      // ** PARTE 2 **
      // -------------
      // defino un query

      String sql = query;
      // preparo la sentencia que voy a ejecutar
      System.out.printf("Cooking the statement in %s...\n", place);
      pstm = conn.prepareStatement(sql);
      for (int i = 1; i <= data.size(); i++) {
        // System.out.println(i+" "+fields.get(i-1));
        pstm.setObject(i, data.get(i - 1));
      }
      // ejecuto la sentencia y en este caso no obtengo resultados
      int rs = pstm.executeUpdate();
      if (rs != 1)
        success = false;
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    } finally {
      // -------------
      // ** PARTE 3 **
      // -------------
      // cierro todos los recursos en orden inverso al que
      // fueron adquiridos
      try {
        if (pstm != null)
          pstm.close();
        if (conn != null)
          conn.close();
      } catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException(e);
      } // end finally try
    } // end try
    System.out.printf("Serving the statement in %s GoodBye!\n", place);
    return success;
  }

  public List<Map<String, Object>> getItemsWithQuery(String query, List<String> properties) {
    Connection conn = null;
    PreparedStatement pstm = null;
    ResultSet rs = null;

    List<Map<String, Object>> result = new ArrayList<>();
    try {
      // -------------
      // ** PARTE 1 **
      // -------------
      // levanto el driver
      Class.forName(JDBC_DRIVER);
      // establezco la conexion
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      // -------------
      // ** PARTE 2 **
      // -------------
      // defino un query
      /*
       * it gives nombre, producto, stockAlInicio
       * trough a store procedure, previously created
       */
      String sql = query;

      // preparo la sentencia que voy a ejecutar
      System.out.println("Cooking the statement...");
      pstm = conn.prepareStatement(sql);

      // ejecuto la sentencia y obtengo los resultados en rs
      rs = pstm.executeQuery();
      // itero los resultados
      Map<String, Object> tempMap;
      while (rs.next()) {
        tempMap = new HashMap<>();
        for (String p : properties) {
          Object obj = rs.getObject(p);
          tempMap.put(p, obj);
        }
        result.add(tempMap);

        // for p in pr
        // properties.forEach(null);
        // IntStream.range(0,properties.size()).forEach(i -> g);
        // // Retrieve data row by row
        // int productID = rs.getInt("ProductId");
        // String productName = rs.getString("ProductName");
        // int productPriceID = rs.getInt("ProductPriceId");
        // double price = rs.getDouble("Price");
        // int supplierID = rs.getInt("SupplierID");
        // String contactName = rs.getString("ContactName");

        // // Create objects with data obtained
        // supplierTemp = new Supplier(supplierID, contactName);
        // productTemp = new Product(productName, price, productPriceID, productID,
        // supplierTemp);

        // products.add(productTemp);
      }

    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    } finally {
      // -------------
      // ** PARTE 3 **
      // -------------
      // cierro todos los recursos en
      // orden inverso al que
      // fueron adquiridos
      try {
        if (rs != null)
          rs.close();
        if (pstm != null)
          pstm.close();
        if (conn != null)
          conn.close();
      } catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException(e);
      } // end finally try
    } // end try
    // System.out.println("Goodbye!");
    return result;
    // return products;
  }
}
