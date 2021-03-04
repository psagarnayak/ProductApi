package com.example.products.service;

import java.util.List;

import com.example.products.controller.dto.ProductFetchCriteria;
import com.example.products.dto.ProductDTO;

public interface IProductService {

	ProductDTO fetchProductById(long id, ProductFetchCriteria criteria);

	List<ProductDTO> fetchProducts(ProductFetchCriteria criteria);

}
