/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.io.Serializable;

/**
 *
 * @author savantis
 */
public class Items implements Serializable{
    
    private int id;
    
       private double gAmount;
       
      String goodUnit;

    public String getGoodUnit() {
        return goodUnit;
    }

    public void setGoodUnit(String goodUnit) {
        this.goodUnit = goodUnit;
    }

    public double getgAmount() {
        return gAmount;
    }

    public void setgAmount(double gAmount) {
        this.gAmount = gAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    private String item;
    private double distributerPrice;
    private double salePrice;

    public Items(int id,String item, double distributerPrice, double salePrice,double gAmount,String goodUnit) {
        this.id=id;
        this.item = item;
        this.distributerPrice = distributerPrice;
        this.salePrice = salePrice;
        this.gAmount=gAmount;
        this.goodUnit=goodUnit;
    }

    
    
    /**
     * @return the item
     */
    public String getItem() {
        return item;
    }

    /**
     * @return the distributerPrice
     */
    public double getDistributerPrice() {
        return distributerPrice;
    }

    /**
     * @return the salePrice
     */
    public double getSalePrice() {
        return salePrice;
    }
    
    
    
}
