package com.example.shoponline1.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "promotion")
public class Promotion {
	 @Id
	 @Column(name = "promotionId")
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private int id;
	 private String name;
	 private double discountvalue;
	 private String notes;
	 private Date start;
	 private Date end;

}
