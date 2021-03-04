package com.example.products.httpClients;

import java.math.BigDecimal;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FixerConversionInfo {
	private long timestamp;
	private String base;
	private String date;
	private Map<String, BigDecimal> rates;

}
