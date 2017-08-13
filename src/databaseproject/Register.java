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
public class Register {
    /* i set the date as a string to put that value in db*/
    String date;
    int addedOrRemovedStock;
    Product product;
    int initialStock;
    int finalStock;
    int quantitySold;
    double cashSale;
    
    public Register(){        
    }    
    public Register(Product product,int initialStock){        
        this.product = product;
        this.initialStock=initialStock;
    }
    public Register(String date,Product product, int quantitySold, double cashSale) {
        this.date = date;
        this.product = product;
        this.quantitySold = quantitySold;
        this.cashSale = cashSale;
    }
    public Register(int addedOrRemovedStock,Product product, int initialStock, int finalStock, int quantitySold, double cashSale) {
        this.addedOrRemovedStock = addedOrRemovedStock;
        this.product = product;
        this.initialStock = initialStock;
        this.finalStock = finalStock;
        this.quantitySold = quantitySold;
        this.cashSale = cashSale;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
        
    public int getAddedOrRemovedStock() {
        return addedOrRemovedStock;
    }

    public void setAddedOrRemovedStock(int addedOrRemovedStock) {
        this.addedOrRemovedStock = addedOrRemovedStock;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }  
    
    public int getInitialStock() {
        return initialStock;
    }

    public void setInitialStock(int initialStock) {
        this.initialStock = initialStock;
    }

    public int getFinalStock() {
        return finalStock;
    }

    public void setFinalStock(int finalStock) {
        this.finalStock = finalStock;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }

    public double getCashSale() {
        return cashSale;
    }

    public void setCashSale(double cashSale) {
        this.cashSale = cashSale;
    }

    /*
       Routines to update the values when  
       "addedOrRemovedStock" and "finalStock"
       are entered
    */
    
    public void computeQuantitySold(){
        int initVal=(addedOrRemovedStock+initialStock);
        int finVal=finalStock;
        setQuantitySold(initVal-finVal);
    }
    
    public void computeCashSales(){
        setCashSale(quantitySold*product.getPrice());
    }
    
    @Override
    public String toString() {
        return "Register{" +"date=" + date + ", addedOrRemovedStock=" + addedOrRemovedStock + ", initialStock=" + initialStock + ", finalStock=" + finalStock + ", quantitySold=" + quantitySold + ", cashSale=" + cashSale + '}';
    }
  
    
}
