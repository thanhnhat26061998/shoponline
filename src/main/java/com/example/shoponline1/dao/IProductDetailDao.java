package com.example.shoponline1.dao;

import com.example.shoponline1.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IProductDetailDao extends JpaRepository<ProductDetail, Integer> {

}
