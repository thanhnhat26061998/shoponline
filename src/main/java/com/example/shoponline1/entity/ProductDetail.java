package com.example.shoponline1.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "productDetail")
public class ProductDetail {

    @Id
    @Column(name = "productDetailId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private double price;
    
    private int amount;
    
    @OneToOne
    @JoinColumn(name = "configuratorId")
    private Configurator configurator;
    
    @OneToOne
    @JoinColumn(name = "colorId")
    private Color color;
    
    @OneToOne
    @JoinColumn(name = "imageId")
    private Images images;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productId")
    private Product product;

}
