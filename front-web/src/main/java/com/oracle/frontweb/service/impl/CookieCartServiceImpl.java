package com.oracle.frontweb.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.oracle.commons.pojo.HttpClientReturn;
import com.oracle.commons.utils.CookieUtils;
import com.oracle.frontweb.httpclient.service.HttpClientUtils;
import com.oracle.frontweb.redis.utils.RedisUtils;
import com.oracle.frontweb.service.CookieCartService;
import com.oracle.manager.pojo.Cart;
import com.oracle.manager.pojo.Product;

@Service
public class CookieCartServiceImpl implements CookieCartService {
	
	@Autowired
	private RedisUtils redisUtils;
	
	@Autowired
	private HttpClientUtils httpClientUtils;
	
	@Value("${DISPLAY_PRODUCT_URL}")
	private String DISPLAY_PRODUCT_URL;
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	private String COOKIE_CART = "cookie_cart";

	@Override
	public List<Cart> showCart(HttpServletRequest request) throws Exception  {
		List<Cart> list = new ArrayList<>();
		String cart_ticket = CookieUtils.getCookieValue(request, COOKIE_CART, true);
		if ("cookie_cart".equals(cart_ticket)) {
			String jsonData = redisUtils.get(cart_ticket);
			if (!StringUtils.isEmpty(jsonData)) {
				redisUtils.expire(cart_ticket, 86400);
				JavaType valueType = MAPPER.getTypeFactory().constructCollectionType(List.class, Cart.class);
				list = MAPPER.readValue(jsonData, valueType);
				return list;
			}
		}
		return list;
	}

	@Override
	public Boolean addCart(HttpServletRequest request,
			HttpServletResponse response, Long productId) throws Exception {
		List<Cart> old_list = showCart(request);
		for (Cart cart : old_list) {
			System.out.println(cart);
		}
		if (!old_list.isEmpty()) {
			for (Cart cart : old_list) {
				if (cart.getProductId().toString().equals(productId.toString())) {
					cart.setNum(cart.getNum()+1);
					cart.setUpdated(new Date());
					String new_jsonData = MAPPER.writeValueAsString(old_list);
					redisUtils.setAndExpire("cookie_cart", new_jsonData,86400);
					return true;
				}
			}
			setNewCart(productId,old_list,request,response);
			return true;
		}else {
			setNewCart(productId,old_list,request,response);
			return true;
		}
	}
	public void setNewCart(Long productId,List<Cart> old_list,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		if (productId!=null) {
			String url = DISPLAY_PRODUCT_URL + productId;
			HttpClientReturn httpClientReturn = httpClientUtils.doGet(url);
			Integer code = httpClientReturn.getStatusCode();
			if (code==200) {
				String jsonData = httpClientReturn.getResponseBody();
				Product product = MAPPER.readValue(jsonData, Product.class);
				Cart cart = new Cart();
				cart.setProductId(productId);
				cart.setProductTitle(product.getTitle());
				String[] images = product.getImages();
				if (images!=null) {
					cart.setProductImage(images[0]);
				}
				cart.setProductPrice(product.getPrice());
				cart.setCreated(new Date());
				cart.setUpdated(cart.getCreated());
				cart.setNum(1);
				old_list.add(cart);
			}
			redisUtils.set("cookie_cart", MAPPER.writeValueAsString(old_list));
			CookieUtils.setCookie(request, response, COOKIE_CART, "cookie_cart", true);
		}
		
	}

	@Override
	public void deleteCart(HttpServletRequest request, Long productId) throws Exception {
		List<Cart> old_list = showCart(request);
		if (!old_list.isEmpty()) {
			for (Cart cart : old_list) {
				if (cart.getProductId().toString().equals(productId.toString())) {
					old_list.remove(cart);
				}
			}
			redisUtils.setAndExpire("cookie_cart", MAPPER.writeValueAsString(old_list), 86400);
		}
	}

	@Override
	public void updateNum(HttpServletRequest request, Long productId,
			Integer num) throws Exception {
		List<Cart> old_list = showCart(request);
		if (!old_list.isEmpty()) {
			for (Cart cart : old_list) {
				System.out.println(cart.getProductId() instanceof Long);
				if (cart.getProductId().toString().equals(productId.toString())) {
					cart.setNum(num);
					cart.setUpdated(new Date());
				}
			}
			redisUtils.setAndExpire("cookie_cart", MAPPER.writeValueAsString(old_list), 86400);
		}
	}

}
