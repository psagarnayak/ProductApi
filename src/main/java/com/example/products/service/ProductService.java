package com.example.products.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.products.db.repo.ProductRepo;
import com.example.products.dto.ProductDTO;
import com.example.products.httpClients.FixerClient;
import com.example.products.httpClients.FixerConversionInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProductService {

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private FixerClient fixerClient;
	
	@Value("${products.default-currency-code}")
	private String DEFAULT_CURRENCY_CODE;

	private ObjectMapper mapper = new ObjectMapper();
	

	public List<ProductDTO> fetchProducts(String currencyCode) {
		List<ProductDTO> fetchedProducts = fetchProducts();

		if (currencyCode != null && !DEFAULT_CURRENCY_CODE.equalsIgnoreCase(currencyCode)) {

			FixerConversionInfo conversionInfo = fixerClient.fetchConversionInfo();
			BigDecimal conversionRate = conversionInfo.getRates().get(currencyCode);
			if (conversionRate != null) {
				fetchedProducts.forEach(p -> {
					p.setPrice(p.getPrice().multiply(conversionRate).setScale(2, RoundingMode.HALF_EVEN));
					p.setCurrencyCode(currencyCode);
				});
			}
		}
		return fetchedProducts;
	}

	private List<ProductDTO> fetchProducts() {
		List<ProductDTO> fetchedProducts = mapper.convertValue(productRepo.findAll(),
				mapper.getTypeFactory().constructCollectionType(List.class, ProductDTO.class));

		fetchedProducts.forEach(p -> {
			p.setCurrencyCode(DEFAULT_CURRENCY_CODE);
		});
		
		return fetchedProducts;
	}
}
