package com.example.products.service;

import java.math.BigDecimal;

public interface ICurrencyService {

	String getCurrencyBaseCode();

	BigDecimal convertCurrency(BigDecimal value, String toCode);

	BigDecimal convertCurrency(BigDecimal value, String fromCode, String toCode);

}
