package com.oracle.frontweb.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.github.abel533.mapper.Mapper;
import com.oracle.commons.pojo.HttpClientReturn;
import com.oracle.frontweb.httpclient.service.HttpClientUtils;
import com.oracle.frontweb.service.CartService;
import com.oracle.manager.pojo.Cart;
import com.oracle.manager.pojo.Product;
import com.oracle.manager.pojo.User;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private HttpClientUtils httpClientUtils;

	@Value("${CART_LIST}")
	private String CART_LIST;

	@Value("${DISPLAY_PRODUCT_URL}")
	private String DISPLAY_PRODUCT_URL;

	@Value("${ADD_CART}")
	private String ADD_CART;

	@Value("${DELETE_CART}")
	private String DELETE_CART;

	@Value("${UPDATA_NUM}")
	private String UPDATA_NUM;

	private static final ObjectMapper Mapper = new ObjectMapper();

	@Override
	public List<Cart> showCart(User user) throws Exception {
		String url = CART_LIST + user.getId();
		HttpClientReturn httpClientReturn = httpClientUtils.doGet(url);
		Integer statusCode = httpClientReturn.getStatusCode();
		if (statusCode == 200) {
			String jsonData = httpClientReturn.getResponseBody();
			JavaType valueType = Mapper.getTypeFactory()
					.constructCollectionType(List.class, Cart.class);
			List<Cart> list = Mapper.readValue(jsonData, valueType);
			return list;
		}
		return null;
	}

	// 查商品的接口：manager.oracle.com/restful/dispalyproduct/{productId}
	// 添加商品到购物车的接口：cart.oracle.com/cart
	@Override
	public void addCart(User user, Long productId) throws Exception {
		String queryProduct_url = DISPLAY_PRODUCT_URL + productId;
		String addCart_url = ADD_CART;

		HttpClientReturn queryProduct = httpClientUtils.doGet(queryProduct_url);

		Integer statusCode = queryProduct.getStatusCode();

		Product product = null;
		if (statusCode == 200) {
			String jsonData = queryProduct.getResponseBody();
			if (!StringUtils.isEmpty(jsonData) && !"".equals(jsonData)) {
				product = Mapper.readValue(jsonData, Product.class);
			}
		}
		if (product != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("userId", user.getId());
			map.put("productId", product.getId());
			map.put("productTitle", product.getTitle());
			String[] images = product.getImages();
			if (images.length != 0) {
				map.put("productImage", images[0]);
			}
			map.put("productPrice", product.getPrice());
			map.put("num", 1);
			httpClientUtils.doPost(addCart_url, map);
		}

	}

	@Override
	public void deleteCart(User user, Long productId) throws Exception {
		String deleteCart_url = DELETE_CART + user.getId() + "/" + productId;
		httpClientUtils.doDelete(deleteCart_url);
	}

	@Override
	public void updateNum(User user, Long productId, Integer num)
			throws Exception {
		String deleteNum_url = UPDATA_NUM + user.getId() +"/"+ productId +"/"+ num;
		httpClientUtils.doPut(deleteNum_url);
	}

}
