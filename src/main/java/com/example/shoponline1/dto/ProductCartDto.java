/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.shoponline1.dto;

import com.example.shoponline1.entity.Product;
import com.example.shoponline1.entity.ProductDetail;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 *
 * @author thanh
 */
@Component

public class ProductCartDto {

    private int productId;
    private String img;
    private String productName;
    private double priceBefore;
    private double priceAfter;
    private double reducedPrice;
    private String color;
    private int amount;

    public ProductCartDto(ProductDetail productDetail) {
        this.productId = productDetail.getId();
        this.productName = productDetail.getProduct().getName();
        this.img = productDetail.getImages().getImage1();
        this.priceBefore = productDetail.getPrice();
        this.priceAfter = productDetail.getPrice() - ((productDetail.getPrice() * productDetail.getProduct().getPromotion().getDiscountvalue())/100);
        this.reducedPrice = this.priceBefore - this.priceAfter;        
        this.color = productDetail.getColor().getName();
        this.amount= productDetail.getAmount();
    }

    public ProductCartDto() {
    }

    public ProductCartDto(int productId, String img, String productName, double priceBefore, double priceAfter, double reducedPrice, int amount) {
        this.productId = productId;
        this.img = img;
        this.productName = productName;
        this.priceBefore = priceBefore;
        this.priceAfter = priceAfter;
        this.reducedPrice = reducedPrice;
        this.amount = amount;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
    
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPriceBefore() {
        return priceBefore;
    }

    public void setPriceBefore(double priceBefore) {
        this.priceBefore = priceBefore;
    }

    public double getPriceAfter() {
        return priceAfter;
    }

    public void setPriceAfter(double priceAfter) {
        this.priceAfter = priceAfter;
    }

    public double getReducedPrice() {
        return reducedPrice;
    }

    public void setReducedPrice(double reducedPrice) {
        this.reducedPrice = reducedPrice;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
