package com.romei;

import static com.romei.MySqlUtil.dataIsInDB;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author User
 */
public class ProductService {
    IDBRepository repository = new MysqlRepository();

    public boolean productExists(String productName, String price) {
        String query1 = "call getProductWithPrice(?,?)";// todo: fix
        List<Object> fields = new ArrayList();
        Collections.addAll(fields, productName, Double.parseDouble(price));
        return !this.repository.dataIsInDB(fields, query1, "TabAddProduct to check if exist the product").isEmpty();

    }

    public boolean supplierDoesntExist(String supplierName) {
        String query2 = "select ContactName,phone from Suppliers where ContactName=? and inDB=1";
        List<Object> fields = new ArrayList();
        Collections.addAll(fields, supplierName);
        return this.repository.dataIsInDB(fields, query2, "check if exist supplier").isEmpty();

    }

    public boolean addProduct(String supplierName, String productName, String price) {
        String query3 = "call insertProduct(?,?,?)";// todo:fix
        List<Object> data = new ArrayList();
        Collections.addAll(data, supplierName, productName, Double.parseDouble(price));
        return this.repository.queryWithData(data, query3, "TabAddProduct to insert");
    }
}