package com.example.shoponline1.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "image")
public class Images {
	@Id
    @Column(name = "imageId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String image1;
	private String image2;
	private String image3;
	private String image4;
	private String image5;
	private String image6;
	private String notes;

}
