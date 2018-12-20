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
public class SellShopModel implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private int uid;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
    private  String ownername;
    private  String id;
    private  String paymentMethod;
    private  Long paymentAmount;
    private  String date;
    private  String goodName;
    private Double goodAmount;
    private String goodUnit;

    public String getGoodUnit() {
        return goodUnit;
    }

    public void setGoodUnit(String goodUnit) {
        this.goodUnit = goodUnit;
    }

    public SellShopModel(int uid,String ownername, String id, String paymentMethod, Long paymentAmount, String date, String goodName, Double goodAmount,String goodUnit) {
        
        this.uid=uid;
        this.ownername = ownername;
        this.id = id;
        this.paymentMethod = paymentMethod;
        this.paymentAmount = paymentAmount;
        this.date = date;
        this.goodName = goodName;
        this.goodAmount = goodAmount;
        this.goodUnit = goodUnit;
    }
    
    public String getOwnername() {
        return ownername;
    }

    public void setOwnername(String ownername) {
        this.ownername = ownername;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Long getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Long paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public Double getGoodAmount() {
        return goodAmount;
    }

    public void setGoodAmount(Double goodAmount) {
        this.goodAmount = goodAmount;
    }
    
    
}
