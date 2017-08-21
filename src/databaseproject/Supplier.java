/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseproject;

/**
 *
 * @author User
 */
public class Supplier {
    int supplierID;
    String contactName;
    String phone;

    public Supplier(int supplierID, String contactName) {
        this.supplierID = supplierID;
        this.contactName = contactName;
    }

    public Supplier(String contactName, String phone) {
        this.contactName = contactName;
        this.phone = phone;
    }
    
    public Supplier(String contactName) {
        this.contactName = contactName;     
    }
    
    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
     @Override
    public String toString() {
        return contactName;
    }
}
