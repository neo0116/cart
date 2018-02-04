package com.oracle.product.controller;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.abel533.mapper.Mapper;
import com.oracle.manager.pojo.Product;
import com.oracle.product.redis.utils.RedisUtils;
import com.oracle.product.service.ProductService;

@Controller
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private RedisUtils redisUtils;
	
	@Value("${PRODUCT_REDIS_KEY}")
	public String PRODUCT_REDIS_KEY;
	
	private static final ObjectMapper  Mapper = new ObjectMapper();
	
	@RequestMapping(value="/{productId}.html",method=RequestMethod.GET)
	public String displayProduct(@PathVariable("productId")Long id,
			Model model){
		String jsonData = redisUtils.get(PRODUCT_REDIS_KEY+id);
		if (!StringUtils.isEmpty(jsonData)) {
			try {
				Product product = Mapper.readValue(jsonData, Product.class);
				model.addAttribute("product", product);
				return "product";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		Product product = new Product();
		try {
			product = productService.displayProduct(id);
			String valueAsString = Mapper.writeValueAsString(product);
			redisUtils.set(PRODUCT_REDIS_KEY+id, valueAsString);
			model.addAttribute("product", product);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("product", product);
		return "product";
	}
	

}
