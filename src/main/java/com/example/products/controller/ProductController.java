package com.example.products.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.products.controller.dto.ProductFetchCriteria;
import com.example.products.dto.ProductDTO;
import com.example.products.service.IProductService;

@RestController
@RequestMapping("/api")
public class ProductController {
	
	@Autowired
	private IProductService productService;
	
	@GetMapping("/products")
	public List<ProductDTO> fetchAllProducts(ProductFetchCriteria criteria) {
			//@RequestParam(name = "currencyCode", required = false) String currencyCode){
		//ProductFetchCriteria criteria = ProductFetchCriteria.builder().currencyCode(currencyCode).build();
		return productService.fetchProducts(criteria);
	}
	
	@GetMapping("/products/{id}")
	public ProductDTO fetchProductByIf(@PathVariable("id") long id, ProductFetchCriteria criteria ) {
		//ProductFetchCriteria criteria = ProductFetchCriteria.builder().currencyCode(currencyCode).build();
		return productService.fetchProductById(id, criteria);
	}
	
}
