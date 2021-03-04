package com.example.products.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.products.controller.dto.ProductFetchCriteria;
import com.example.products.db.repo.ProductRepo;
import com.example.products.dto.ProductDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProductService implements IProductService {

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private ICurrencyService currencyService;

	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public List<ProductDTO> fetchProducts(ProductFetchCriteria criteria) {
		List<ProductDTO> fetchedProducts = fetchProducts();
		fetchedProducts.forEach(p -> applyCurrencyConversion(p, criteria));
		return fetchedProducts;
	}

	@Override
	public ProductDTO fetchProductById(long id, ProductFetchCriteria criteria) {
		ProductDTO fetchedProduct = fetchProductById(id);
		applyCurrencyConversion(fetchedProduct, criteria);
		return fetchedProduct;
	}

	private void applyCurrencyConversion(ProductDTO fetchedProduct, ProductFetchCriteria criteria) {
		String currencyCode = criteria.getCurrencyCode();
		if(currencyCode == null ) {
			currencyCode = currencyService.getCurrencyBaseCode();
		}
		fetchedProduct.setPrice(currencyService.convertCurrency(fetchedProduct.getPrice(), currencyCode));
		fetchedProduct.setCurrencyCode(currencyCode);
	}

	private List<ProductDTO> fetchProducts() {
		List<ProductDTO> fetchedProducts = mapper.convertValue(productRepo.findAll(),
				mapper.getTypeFactory().constructCollectionType(List.class, ProductDTO.class));
		return fetchedProducts;
	}

	private ProductDTO fetchProductById(long id) {
		ProductDTO fetchedProduct = mapper.convertValue(productRepo.findById(id).orElse(null), ProductDTO.class);
		return fetchedProduct;
	}
}
