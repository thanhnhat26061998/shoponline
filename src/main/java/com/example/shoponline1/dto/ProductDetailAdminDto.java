package com.example.shoponline1.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class ProductDetailAdminDto {
	
	private int id;
	private String amount;
	private String price;
	private int colorId;
	private int configId;
	private int productId;
	private String colorVsConfig;
	private String colorVsConfigs;
	private String image;
	private String amouts;
	private String prices;

}
