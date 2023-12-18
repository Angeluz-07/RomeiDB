package com.romei;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 *
 * @author User
 */
public class RegisterService {
    IDBRepository repository = new MysqlRepository();

    // public boolean userExists(String firstName, String lastName, String userName) {
    //     String query1 = "select FirstName,LastName,UserName from Users where FirstName=? and LastName=? and UserName=? and inDB=1";
    //     List<Object> fields = new ArrayList<>();
    //     Collections.addAll(fields, firstName, lastName, userName);
    //     return !this.repository.dataIsInDB(fields, query1, "TabAddUsser.java to check if already exist user").isEmpty();

    // }

    public boolean addRegister(int userID, String registerDate,int productPriceID, int InitialStock, int FinalStock, int QuantitySold,double CashSale){

        String query2 = "";
        query2 += "insert into Registers (UserID,RegisterDate,ProductPriceID,InitialStock,FinalStock,QuantitySold,CashSale)";
        query2 += "values(?,?,?,?,?,?,?)";
        List<Object> fields = new ArrayList<>();
        Collections.addAll(fields, userID, registerDate,productPriceID, InitialStock, FinalStock, QuantitySold, CashSale);
        return this.repository.queryWithData(fields, query2,  "Tabaddregister to insert register");
    }

    public boolean addAORStockItem(int addedOrRemovedStock){
        String query2 = "";
        query2 += "call insertAORS(?)";
        List<Object> fields = new ArrayList<>();
        Collections.addAll(fields, addedOrRemovedStock);
        return this.repository.queryWithData(fields, query2,  "Tabaddregister to insert AORStock");
    }
    

    // public boolean removeUser(Integer userId) {
    //     // logical deletion
    //     String query3 = "call setStateUser(0,?)";//todo: fix
    //     List<Object> data = new ArrayList<>();
    //     Collections.addAll(data, userId);
    //     return this.repository.queryWithData(data, query3, "TabAddUser to delete");
    // }

    // public boolean updateUser(String firstName, String lastName, String userName, String passInput,
    //         Integer userId) {
    //     String query3 = "call updateUser(?,?,?,?,?) ";//todo: fix
    //     List<Object> data = new ArrayList<>();
    //     Collections.addAll(data, firstName, lastName, userName, passInput,userId);
    //     return this.repository.queryWithData(data, query3, "TabAdduser to update");
    // }

    public List<Register> getRegisters() {
        String sql = "call getCurrentStock()";
        List<String> properties = Arrays.asList(
            "ProductName",
            "Price",
            "CurrentStock",
            "ProductPriceId");
        List<Map<String, Object>> items = this.repository.getItemsWithQuery(sql, properties);
        List<Register> result = new ArrayList<>();
        for (Map<String, Object> m : items) {            
            String productName = (String) m.get("ProductName");
            BigDecimal priceBD = (BigDecimal) m.get("Price");
            double price = priceBD.doubleValue();
            Long currentStockLong = (Long) m.get("CurrentStock");
            int currentStock = currentStockLong.intValue();
            int productPriceId = (int) m.get("ProductPriceId");

            Product p = new Product(productName, price, productPriceId);
            Register r = new Register(p, currentStock);

            result.add(r);
        }
        return result;

    }
}