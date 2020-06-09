package com.example.shoponline1.service;

import com.example.shoponline1.dto.ProductDto;
import com.example.shoponline1.entity.ProductDetail;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductDetailService {

    List<ProductDetail> findAll();

    List<ProductDto> findAllPrd();

    Page<ProductDto> findPaginated(Pageable pageable);

    ProductDetail findById(int id);

    ProductDto findProductDtoById(int id);

    List<ProductDto> findListProductById(int id);

}
