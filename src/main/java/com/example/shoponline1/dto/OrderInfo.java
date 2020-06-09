/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.shoponline1.dto;

import java.util.Date;
import java.util.List;

/**
 *
 * @author thanh
 */
public class OrderInfo {
    private int id;
    private Date orderDate;
    
    private String productName;
    private int quantity;
    private double price;
    
    private OrderDetailInfo orderDetailInfo;
    
    private List<OrderDetailInfo> orderDetailInfos;

    public OrderInfo() {
    }

    public OrderInfo(int id, Date orderDate, String productName, int quantity, double price, OrderDetailInfo orderDetailInfo, List<OrderDetailInfo> orderDetailInfos) {
        this.id = id;
        this.orderDate = orderDate;
        this.productName = orderDetailInfo.getProductName();
        this.price = orderDetailInfo.getPrice();
        this.quantity = orderDetailInfo.getQuantity();
        this.orderDetailInfos = orderDetailInfos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public List<OrderDetailInfo> getOrderDetailInfos() {
        return orderDetailInfos;
    }

    public void setOrderDetailInfos(List<OrderDetailInfo> orderDetailInfos) {
        this.orderDetailInfos = orderDetailInfos;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public OrderDetailInfo getOrderDetailInfo() {
        return orderDetailInfo;
    }

    public void setOrderDetailInfo(OrderDetailInfo orderDetailInfo) {
        this.orderDetailInfo = orderDetailInfo;
    }
    
    
}
