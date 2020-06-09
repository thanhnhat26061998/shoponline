package com.example.shoponline1.service;

import com.example.shoponline1.dao.IProductDao;
import com.example.shoponline1.dao.IProductDetailDao;
import com.example.shoponline1.dto.ProductDto;
import com.example.shoponline1.entity.ProductDetail;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



@Service
public class ProductDetailServiceImpl implements IProductDetailService {
	
	@Autowired
	private IProductDetailDao productDetailDao;
	
	@Autowired
	private IProductDao productDao;
	
	


	@Override
	public List<ProductDetail> findAll() {
		return productDetailDao.findAll();
	}

	@Override
	public List<ProductDto> findAllPrd() {
		List<ProductDetail> prdDetails = productDetailDao.findAll();
		List<ProductDto> prdDto = new ArrayList<ProductDto>();
		for (ProductDetail prdDetail : prdDetails) {
			ProductDto productDto = new ProductDto();
			double price = prdDetail.getPrice() - (prdDetail.getPrice()*prdDetail.getProduct().getPromotion().getDiscountvalue()/100);
			productDto.setProductId(prdDetail.getProduct().getId());
			productDto.setProductDetailId(prdDetail.getId());
			productDto.setConfiguratorId(prdDetail.getConfigurator().getId());
			productDto.setProductName(prdDetail.getProduct().getName());
			productDto.setBrandName(prdDetail.getProduct().getTrademark().getName());
			productDto.setRom(prdDetail.getConfigurator().getRom());
			productDto.setPriceBefore(prdDetail.getPrice());
			productDto.setPriceAfter(price);
			productDto.setReducedPrice(prdDetail.getPrice()-price);
			productDto.setImg(prdDetail.getImages().getImage1());
			prdDto.add(productDto);
		}
		return prdDto;
	}
	// -----------------
	@Override
	public Page<ProductDto> findPaginated(Pageable pageable) {
		int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<ProductDetail> prdDetails = productDetailDao.findAll();
		List<ProductDto> products = new ArrayList<ProductDto>();
		
		for (ProductDetail prdDetail : prdDetails) {
			ProductDto  productDto = new ProductDto();
			double price = prdDetail.getPrice() - ((prdDetail.getPrice()*prdDetail.getProduct().getPromotion().getDiscountvalue())/100);
			productDto.setProductId(prdDetail.getProduct().getId());
			productDto.setProductDetailId(prdDetail.getId());
			productDto.setConfiguratorId(prdDetail.getConfigurator().getId());
			productDto.setProductName(prdDetail.getProduct().getName());
			productDto.setRom(prdDetail.getConfigurator().getRom());
			productDto.setPriceBefore(prdDetail.getPrice());
			productDto.setPriceAfter(price);
			productDto.setReducedPrice(prdDetail.getPrice()-price);
			productDto.setImg(prdDetail.getImages().getImage1());
			products.add(productDto);
		}
		
        List<ProductDto> list;
        if (products.size()<startItem) {
			list =Collections.emptyList();
		}else {
			int toIndex = Math.min(startItem+pageSize, products.size());
			list = products.subList(startItem, toIndex);
		}      
        Page<ProductDto> productPage = new PageImpl<ProductDto>(list, PageRequest.of(currentPage, pageSize), products.size());
		return productPage;
	}
	
	
	

	@Override
	public ProductDetail findById(int id) {
		return productDetailDao.findById(id).get();
	}

	@Override
	public ProductDto findProductDtoById(int id) {
		List<ProductDetail> prdDetails = productDetailDao.findAll();
		ProductDto productDto = new ProductDto();
		for (ProductDetail prdDetail : prdDetails) {
			if (prdDetail.getId()==id) {
				double price = prdDetail.getPrice() - (prdDetail.getPrice()*prdDetail.getProduct().getPromotion().getDiscountvalue()/100);
				productDto.setProductId(prdDetail.getProduct().getId());
				productDto.setProductDetailId(prdDetail.getId());
				productDto.setConfiguratorId(prdDetail.getConfigurator().getId());
				productDto.setProductName(prdDetail.getProduct().getName());
				productDto.setBrandName(prdDetail.getProduct().getTrademark().getName());
				productDto.setRom(prdDetail.getConfigurator().getRom());
				productDto.setPriceBefore(prdDetail.getPrice());
				productDto.setPriceAfter(price);
				productDto.setReducedPrice(prdDetail.getPrice()-price);
				productDto.setImg(prdDetail.getImages().getImage1());
				productDto.setImg2(prdDetail.getImages().getImage2());
				productDto.setImg3(prdDetail.getImages().getImage3());
				productDto.setColor(prdDetail.getColor().getName());
			}
			

		}
		return productDto;
	}

	@Override
	public List<ProductDto> findListProductById(int id) {
		List<ProductDetail> prdDetails = productDetailDao.findAll();
		List<ProductDto> prdDto = new ArrayList<ProductDto>();
		for (ProductDetail prdDetail : prdDetails) {
			if (prdDetail.getProduct().getId()==id) {
				ProductDto productDto = new ProductDto();
				double price = prdDetail.getPrice() - (prdDetail.getPrice()*prdDetail.getProduct().getPromotion().getDiscountvalue()/100);
				productDto.setProductId(prdDetail.getProduct().getId());
				productDto.setProductDetailId(prdDetail.getId());
				productDto.setConfiguratorId(prdDetail.getConfigurator().getId());
				productDto.setProductName(prdDetail.getProduct().getName());
				productDto.setBrandName(prdDetail.getProduct().getTrademark().getName());
				productDto.setRom(prdDetail.getConfigurator().getRom());
				
				productDto.setPriceBefore(prdDetail.getPrice());
				productDto.setPriceAfter(price);
				productDto.setReducedPrice(prdDetail.getPrice()-price);
				productDto.setImg(prdDetail.getImages().getImage1());
				productDto.setImg2(prdDetail.getImages().getImage2());
				productDto.setImg3(prdDetail.getImages().getImage3());
				productDto.setColor(prdDetail.getColor().getName());
				prdDto.add(productDto);
			}
			

		}
		return prdDto;
	}

	
	



	

	

}
