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
public class UserService {
    IDBRepository repository = new MysqlRepository();

    public boolean userExists(String firstName, String lastName, String userName) {
        String query1 = "select FirstName,LastName,UserName from Users where FirstName=? and LastName=? and UserName=? and inDB=1";
        List<Object> fields = new ArrayList<>();
        Collections.addAll(fields, firstName, lastName, userName);
        return !this.repository.dataIsInDB(fields, query1, "TabAddUsser.java to check if already exist user").isEmpty();

    }

    public boolean addUser(String firstName, String lastName, String userName, String passInput){
        String query2 = "";
        query2 += "insert into Users (FirstName,LastName,UserName,Password)";
        query2 += "values(?,?,?,?) ";
        List<Object> fields = new ArrayList<>();
        Collections.addAll(fields, firstName, lastName, userName, passInput);
        return this.repository.queryWithData(fields, query2,  "TabAddUser to insert user");
    }

    public boolean removeUser(Integer userId) {
        // logical deletion
        String query3 = "call setStateUser(0,?)";//todo: fix
        List<Object> data = new ArrayList<>();
        Collections.addAll(data, userId);
        return this.repository.queryWithData(data, query3, "TabAddUser to delete");
    }

    public boolean updateUser(String firstName, String lastName, String userName, String passInput,
            Integer userId) {
        String query3 = "call updateUser(?,?,?,?,?) ";//todo: fix
        List<Object> data = new ArrayList<>();
        Collections.addAll(data, firstName, lastName, userName, passInput,userId);
        return this.repository.queryWithData(data, query3, "TabAdduser to update");
    }

    public List<User> getUsers() {
        String sql = "select ";
        sql += " UserID,FirstName,LastName,UserName,Password  ";
        sql += " from Users ";
        sql += " where inDB=1";

        List<String> properties = Arrays.asList(
                "UserID",
                "FirstName",
                "LastName",
                "UserName",
                "Password");
        List<Map<String, Object>> items = this.repository.getItemsWithQuery(sql, properties);
        List<User> result = new ArrayList<>();
        for (Map<String, Object> m : items) {
            //int userID = (int) m.get("UserID");
            String firstName = (String) m.get("FirstName");
            String lastName = (String) m.get("LastName");
            String userName = (String) m.get("UserName");
            String pass = (String) m.get("Password");

            User u = new User(firstName, lastName, userName, pass);
            result.add(u);
        }
        return result;

    }
}