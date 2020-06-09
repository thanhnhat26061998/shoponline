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
public class CartInfo {

    private int orderNum;    

    private final List<CartLineInfo> cartLines = new ArrayList<CartLineInfo>();

    public CartInfo() {

    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }
    
    public List<CartLineInfo> getCartLines() {
        return this.cartLines;
    }

    private CartLineInfo findLineByCode(int code) {
        for (CartLineInfo line : this.cartLines) {
            if (line.getProductInfo().getProductId() == code) {
                return line;
            }
        }
        return null;
    }

    public void addProduct(ProductCartDto productInfo, int quantity) {
        CartLineInfo line = this.findLineByCode(productInfo.getProductId());

        if (line == null) {
            line = new CartLineInfo();
            line.setQuantity(0);
            line.setProductInfo(productInfo);
            this.cartLines.add(line);
        }
        int newQuantity = line.getQuantity() + quantity;
        if (newQuantity <= 0) {
            this.cartLines.remove(line);
        } else {
            line.setQuantity(newQuantity);
        }
    }

    public void validate() {

    }

    public void updateProduct(int code, int quantity) {
        CartLineInfo line = this.findLineByCode(code);
        if (line != null) {
            if (quantity <= 0) {
                this.cartLines.remove(line);
            } else {
                line.setQuantity(quantity);
            }
        }
    }

    public void removeProduct(ProductCartDto productInfo) {
        CartLineInfo line = this.findLineByCode(productInfo.getProductId());
        if (line != null) {
            this.cartLines.remove(line);
        }
    }

    public boolean isEmpty() {
        return this.cartLines.isEmpty();
    }

   // public boolean isValidCustomer() {
   //     return this.customerInfo != null && this.customerInfo.isValid();
   // }

    public int getQuantityTotal() {
        int quantity = 0;
        for (CartLineInfo line : this.cartLines) {
            quantity += line.getQuantity();
        }
        return quantity;
    }

    public double getAmountTotal() {
        double total = 0;
        for (CartLineInfo line : this.cartLines) {
            total += line.getAmount();            
        }
        return total;
    }
    
}