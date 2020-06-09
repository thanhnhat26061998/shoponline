/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.shoponline1.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;


@Entity
@Data
@Table(name = "orders")
public class Order implements Serializable {

    @Id
    @Column(name = "orderId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;
    private String nameReceiver;
    private String deliveryAdress;
    @Temporal(TemporalType.DATE)
    private Date deliveryTime;
    private String status;
    private String payments;
    private String notes;
    private double totals;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User users;
    
    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetail;

    
    
    

}
