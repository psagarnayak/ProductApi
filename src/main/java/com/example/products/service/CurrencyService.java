package com.example.products.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.products.httpClients.FixerClient;
import com.example.products.httpClients.FixerConversionInfo;

@Service
public class CurrencyService implements ICurrencyService{

	private static final long MAX_CACHE_TIME_IN_SEC = 3600;

	@Value("${products.default-currency-code}")
	private String currencyBaseCode;

	@Autowired
	private FixerClient fixerClient;

	private FixerConversionInfo conversionInfoCache;

	@Override
	public BigDecimal convertCurrency(BigDecimal value, String fromCode, String toCode) {

		FixerConversionInfo conversionInfo = fetchConversionInfo();

		if(conversionInfo == null || !conversionInfo.isSuccess()) {
			
			throw new RuntimeException("Conversion Service is unavailable/non accessible");
		}
		
		BigDecimal convertedValue = value;
		// If the from code is not same as base of conversionInfo, first convert
		// currency to base.
		if (StringUtils.hasLength(fromCode) && !fromCode.equalsIgnoreCase(conversionInfo.getBase())) {
			BigDecimal rate = conversionInfo.getRates().get(fromCode);
			convertedValue = convertedValue.divide(rate).setScale(2, RoundingMode.HALF_EVEN);
		}

		if (StringUtils.hasLength(toCode) && !toCode.equalsIgnoreCase(conversionInfo.getBase())) {
			// If toCode is not same as base of conversionInfo, apply convert currency to
			// toCode.
			BigDecimal rate = conversionInfo.getRates().get(toCode);
			convertedValue = convertedValue.multiply(rate).setScale(2, RoundingMode.HALF_EVEN);
		}
		
		return convertedValue;
	}
	
	@Override
	public BigDecimal convertCurrency(BigDecimal value, String toCode) {

		return convertCurrency(value, currencyBaseCode, toCode);
	}

	private FixerConversionInfo fetchConversionInfo() {

		return this.conversionInfoCache = this.conversionInfoCache == null || System.currentTimeMillis()
				- this.conversionInfoCache.getTimestamp() * 1000 > MAX_CACHE_TIME_IN_SEC * 1000
						? fixerClient.fetchConversionInfo()
						: this.conversionInfoCache;
	}
	
	@Override
	public String getCurrencyBaseCode() {
		return currencyBaseCode;
	}

}
