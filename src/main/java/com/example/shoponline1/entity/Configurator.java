package com.example.shoponline1.entity;



import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="configurator")
public class Configurator {
	@Id
	@Column(name = "configuratorId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	private String screen;
	private String cpu;
	private String ram;
	private String rom;
	private String cameraFront ;
	private String cameraRear;
	private String pin;
	private String chipset;
	private String systems;	
	private String notes;
//	@OneToOne(mappedBy = "configurator",  cascade = CascadeType.ALL, orphanRemoval = true)
//    private ProductDetail productDetail;

}
