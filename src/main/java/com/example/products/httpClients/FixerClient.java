package com.example.products.httpClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "fixerClient", url = "${fixer.base-url}", configuration = FixerClientConfig.class )
public interface FixerClient {
	
	@GetMapping("${fixer.latest-endpoint-url}")
	FixerConversionInfo fetchConversionInfo();
}
