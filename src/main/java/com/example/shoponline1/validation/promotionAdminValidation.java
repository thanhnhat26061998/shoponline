package com.example.shoponline1.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.shoponline1.dto.ProductAdminDto;
import com.example.shoponline1.entity.Product;
import com.example.shoponline1.entity.Promotion;
import com.example.shoponline1.entity.Trademark;




@Component

public class promotionAdminValidation implements Validator {

	@Override
	public boolean supports(Class<?> aClass) {
		// TODO Auto-generated method stub
		return Promotion.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		Promotion td = (Promotion) o;
		if (td.getName()!=null) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty", "Không được để trống");
			
		}
		if (td.getDiscountvalue()!=0) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "discountvalue", "NotEmpty", "Không được để trống");
		}else {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "NotEmpty");
		}
		
	}

}
