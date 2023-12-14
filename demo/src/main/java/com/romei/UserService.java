package com.romei;

import static com.romei.MySqlUtil.dataIsInDB;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 *
 * @author User
 */
public class UserService {
    IDBRepository repository = new MysqlRepository();

    public boolean userExists(String firstName, String lastName, String userName) {
        String query1 = "select FirstName,LastName,UserName from Users where FirstName=? and LastName=? and UserName=? and inDB=1";
        List<Object> fields = new ArrayList();
        Collections.addAll(fields, firstName, lastName, userName);
        return !this.repository.dataIsInDB(fields, query1, "TabAddUsser.java to check if already exist user").isEmpty();

    }

    public boolean addUser(String firstName, String lastName, String userName, String passInput){
        String query2 = "";
        query2 += "insert into Users (FirstName,LastName,UserName,Password)";
        query2 += "values(?,?,?,?) ";
        List<Object> fields = new ArrayList();
        Collections.addAll(fields, firstName, lastName, userName, passInput);
        return this.repository.queryWithData(fields, query2,  "TabAddUser to insert user");
    }

    // public boolean removeProduct(Integer productPriceId, Integer productId) {
    //     // logical deletion
    //     String query3 = "call setStateProduct(0,?,?)";// todo: fix
    //     List<Object> data = new ArrayList();
    //     Collections.addAll(data, productPriceId, productId);
    //     return this.repository.queryWithData(data, query3, "TabAddProduct to delete");
    // }

    // public boolean updateProduct(String supplierName, String productName, Double price, Integer productId,
    //         Integer productPriceId) {
    //     String query3 = "call updateProduct(?,?,?,?,?)";// todo:fix
    //     List<Object> data = new ArrayList();
    //     Collections.addAll(data, supplierName, productName, price, productId, productPriceId);
    //     return this.repository.queryWithData(data, query3, "TabAddProduct to update");
    // }

    // public List<Product> getProducts() {
    //     String query = "call getCurrentProducts()";
    //     List<String> properties = Arrays.asList(
    //             "ProductId",
    //             "ProductName",
    //             "ProductPriceId",
    //             "Price",
    //             "SupplierID",
    //             "ContactName");
    //     List<Map<String, Object>> items = this.repository.getItemsWithQuery(query, properties);
    //     List<Product> result = new ArrayList<>();
    //     for (Map<String, Object> m : items) {
    //         int productID = (int) m.get("ProductId");
    //         String productName = (String) m.get("ProductName");
    //         int productPriceID = (int) m.get("ProductPriceId");
    //         BigDecimal priceBD = (BigDecimal) m.get("Price");
    //         double price = priceBD.doubleValue();
    //         int supplierID = (int) m.get("SupplierID");
    //         String contactName = (String) m.get("ContactName");

    //         // Create objects with data obtained
    //         Supplier supplierTemp = new Supplier(supplierID, contactName);
    //         Product p = new Product(productName, price, productPriceID, productID, supplierTemp);
    //         result.add(p);
    //     }
    //     return result;

    // }
}