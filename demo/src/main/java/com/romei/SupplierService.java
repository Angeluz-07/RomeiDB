package com.romei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 *
 * @author User
 */
public class SupplierService {
    IDBRepository repository = new MysqlRepository();


    public boolean suppliertExists(String supplierName) {
        String query2 = "select ContactName,Phone from Suppliers where ContactName=? and inDB=1";
        List<Object> fields = new ArrayList<>();
        Collections.addAll(fields, supplierName);
        return !this.repository.dataIsInDB(fields, query2, "check if exist supplier").isEmpty();

    }

    public boolean suppliertExists(String supplierName, String phone) {
        String query2 = "select ContactName,phone from Suppliers where ContactName=? and Phone=? and inDB=1";//todo: fix
        List<Object> fields = new ArrayList<>();
        Collections.addAll(fields, supplierName, phone);
        return !this.repository.dataIsInDB(fields, query2, "check if exist supplier").isEmpty();

    }
    public boolean addSupplier(String contactName, String phone){

        String query2 = "";
        query2 += "insert into Suppliers(ContactName,Phone)";
        query2 += "values(?,?) ";

        List<Object> fields = new ArrayList<>();
        Collections.addAll(fields, contactName, phone);
        return this.repository.queryWithData(fields, query2,  "TabAddUser to insert supplier");
    }

    public boolean removeSupplier(Integer supplierId) {
        // logical deletion
        String query3 = "call setStateSupplier(0,?)";//todo: fix
        List<Object> data = new ArrayList<>();
        Collections.addAll(data, supplierId);
        return this.repository.queryWithData(data, query3, "TabAddSupplier to delete");
    }

    public boolean updateSupplier(String supplierName, String phone, Integer supplierId) {
        String query3 = "call updateSupplier(?,?,?)";//todo: fix
        List<Object> data = new ArrayList<>();
        Collections.addAll(data, supplierName, phone, supplierId);
        return this.repository.queryWithData(data, query3, "TabAdduser to update");
    }

    public List<Supplier> getSuppliers() {
        String sql = "select ";
        sql += " SupplierID,ContactName,Phone ";
        sql += " from Suppliers ";
        sql += " where inDB=1";

        List<String> properties = Arrays.asList(
                "SupplierID",
                "ContactName",
                "Phone");
        List<Map<String, Object>> items = this.repository.getItemsWithQuery(sql, properties);
        List<Supplier> result = new ArrayList<>();
        for (Map<String, Object> m : items) {
            int supplierID = (int) m.get("SupplierID");
            String ContactName = (String) m.get("ContactName");
            String phone = (String) m.get("Phone");
            
            Supplier s = new Supplier(supplierID, ContactName, phone);
            result.add(s);
        }
        return result;

    }
}