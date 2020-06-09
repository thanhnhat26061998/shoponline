package com.example.shoponline1.dto;



import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class ProductDto {
	private int productId;
	private int productDetailId;
	private int configuratorId;
	private int amout;
	private String productName;
	private String rom;
	private String color;
	private String brandName;
	private double priceBefore;
	private double priceAfter;
	private double reducedPrice;
	private String img;
	private String img2;
	private String img3;
}
