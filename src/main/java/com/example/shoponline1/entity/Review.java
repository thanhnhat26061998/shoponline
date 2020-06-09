package com.example.shoponline1.entity;


import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="review")
public class Review {

	@Id
	@Column(name = "reviewId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	private String content;
	private int star;
	private Date date;
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="productId")
    private Product product;
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="userId")
    private User user;
	
	

	
	
}
