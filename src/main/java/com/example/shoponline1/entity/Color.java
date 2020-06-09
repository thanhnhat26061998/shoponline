package com.example.shoponline1.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name="color")
public class Color {
	@Id
	@Column(name = "colorId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	private String name;
	private String notes;
	/*
	 * @OneToOne(mappedBy = "color", cascade = CascadeType.ALL, orphanRemoval =
	 * true) private ProductDetail productDetail;
	 */
}
