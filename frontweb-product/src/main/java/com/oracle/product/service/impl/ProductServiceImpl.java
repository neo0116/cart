package com.oracle.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oracle.commons.pojo.HttpClientReturn;
import com.oracle.manager.pojo.Product;
import com.oracle.product.httpclient.utils.HttpClientUtils;
import com.oracle.product.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private HttpClientUtils httpClientUtils;
	
	@Value("${DISPALY_PRODUCT_URL}")
	public String DISPALY_PRODUCT_URL;
	
	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Override
	public Product displayProduct(Long id) throws Exception {
		HttpClientReturn httpClientReturn = httpClientUtils.doGet(DISPALY_PRODUCT_URL+id);
		Integer statusCode = httpClientReturn.getStatusCode();
		if (statusCode==200) {
			String responseBody = httpClientReturn.getResponseBody();
			Product product = MAPPER.readValue(responseBody, Product.class);
			return product;
		}
		return null;
	}

	

}
