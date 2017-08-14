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
public class Product {
    String name;
    double price;
    int quantity;

    public Product() {
    }

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }
    
    public Product(String name) {
        this.name = name;    
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return name + " $" + price;
    }
    
}
