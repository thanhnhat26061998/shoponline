/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.shoponline1.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author thanh
 */
public class CartLineInfo {
    private ProductCartDto productInfo;
    private int quantity;
    
  
    public CartLineInfo() {
        this.quantity = 0;
    }
  
    public ProductCartDto getProductInfo() {
        return productInfo;
    }
  
    public void setProductInfo(ProductCartDto productInfo) {
        this.productInfo = productInfo;
    }
  
    public int getQuantity() {
        return quantity;
    }
  
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
  
    public double getAmount() {
        return this.productInfo.getPriceAfter() * this.quantity;
    }
    
    public double getAmountBefore(){
        return this.productInfo.getPriceBefore() * this.quantity;
    }
    
    public double getAmountReduced(){
        return this.productInfo.getReducedPrice() * this.quantity;
    }
    
    public double getAmountTotal() {
        double total = 0;
        List<CartLineInfo> cartLines = new ArrayList<>();
        for (CartLineInfo line : cartLines) {
            total += line.getAmount();
        }
        return total;
    }
}
