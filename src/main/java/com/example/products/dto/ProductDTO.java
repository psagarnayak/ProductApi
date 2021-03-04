package com.example.products.dto;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDTO {
	
	private long productId;
	
	private String productName;
	
	private String productDescription;
	
	private BigDecimal price;
	
	private String currencyCode;
}
