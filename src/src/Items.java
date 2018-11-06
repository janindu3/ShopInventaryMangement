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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    private String item;
    private double distributerPrice;
    private double salePrice;

    public Items(int id,String item, double distributerPrice, double salePrice) {
        this.id=id;
        this.item = item;
        this.distributerPrice = distributerPrice;
        this.salePrice = salePrice;
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
