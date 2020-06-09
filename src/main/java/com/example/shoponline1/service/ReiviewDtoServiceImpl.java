package com.example.shoponline1.service;

import com.example.shoponline1.dao.IReviewDao;
import com.example.shoponline1.dto.ReviewDto;
import com.example.shoponline1.entity.Review;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ReiviewDtoServiceImpl implements IReviewDtoService {
	
	@Autowired 
	private IReviewDao reviewDao;
	
	@Override
	public List<ReviewDto> findAllReviewDto(int id) {
		
		List<Review> rv = reviewDao.findAll();
		List<ReviewDto> rvDtos = new ArrayList<ReviewDto>(); 
		for (Review review : rv) {
			if (review.getProduct().getId()==id) {
				ReviewDto rvDto = new ReviewDto();
				rvDto.setUserId(review.getUser().getId());
				rvDto.setProductId(review.getProduct().getId());
				rvDto.setConten(review.getContent());
				rvDto.setUserName(review.getUser().getName());
				rvDtos.add(rvDto);
			}
			
		}
		
		return rvDtos;
	}

}
