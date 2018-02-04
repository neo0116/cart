package com.oracle.frontweb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.oracle.commons.pojo.HttpClientReturn;
import com.oracle.commons.utils.CookieUtils;
import com.oracle.frontweb.httpclient.service.HttpClientUtils;
import com.oracle.frontweb.redis.utils.RedisUtils;
import com.oracle.frontweb.service.UserService;
import com.oracle.manager.pojo.Cart;
import com.oracle.manager.pojo.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private HttpClientUtils httpClientUtils;
	
	@Autowired
	private RedisUtils redisUtils;
	
	@Value("${ADD_CART}")
	private String ADD_CART;
	
	@Value("${CART_LIST}")
	private String CART_LIST;
	
	private String COOKIE_CART = "cookie_cart";
	
	private static final ObjectMapper ObjectMapper = new ObjectMapper();

	@Override
	public Boolean doRegist(String url, User user) throws Exception {
		Map<String, Object> map = new HashMap<>();
		url = url + "user/register";

		map.put("username", user.getUsername());
		map.put("password", user.getPassword());
		map.put("phone", user.getPhone());
		map.put("email", user.getEmail());

		HttpClientReturn httpClientReturn = httpClientUtils.doPost(url, map);

		Integer statusCode = httpClientReturn.getStatusCode();

		if (statusCode == 201) {
			return true;
		}

		return false;
	}

	@Override
	public String dologin(HttpServletRequest request,String url, String loginname, String nloginpwd) throws Exception {
		Map<String, Object> map = new HashMap<>();
		url = url + "user/login";
		map.put("username", loginname);
		map.put("password", nloginpwd);

		HttpClientReturn httpClientReturn = httpClientUtils.doPost(url, map);
		if (httpClientReturn.getStatusCode() == 200) {
			String ticket = httpClientReturn.getResponseBody();
			System.out.println(ticket);
			if (!StringUtils.isEmpty(ticket) && !"".equals(ticket)) {
				//购物车同步
				String cookieValue = CookieUtils.getCookieValue(request, COOKIE_CART, true);
				if ("cookie_cart".equals(cookieValue)) {
					String jsonData = redisUtils.get(cookieValue);
					if (!StringUtils.isEmpty(jsonData)) {
						JavaType valueType = ObjectMapper.getTypeFactory().constructCollectionType(List.class, Cart.class);
						List<Cart> list = ObjectMapper.readValue(jsonData, valueType);
						String jsonData2 = redisUtils.get(ticket);
						User user = ObjectMapper.readValue(jsonData2, User.class);
						
						//通过用户Id去查所有的商品
						String url2 = CART_LIST + user.getId();
						HttpClientReturn httpClientReturn2 = httpClientUtils.doGet(url2);
						List<Cart> list2 = new ArrayList<>();
						if (httpClientReturn2.getStatusCode()==200) {
							String jsonData3 = httpClientReturn2.getResponseBody();
							JavaType valueType1 = ObjectMapper.getTypeFactory().constructCollectionType(List.class, Cart.class);
							list2 = ObjectMapper.readValue(jsonData3, valueType1);
						}
						//map保存productId和cart
						Map<Long,Cart> maps = new HashMap<>();
						for (Cart cart : list2) {
							maps.put(cart.getProductId(), cart);
						}
						
						if (list!=null) {
							for (Cart cart : list) {
								Map<String, Object> map2 = new HashMap<>();
								Cart cart2 = maps.get(cart.getProductId());
								if (cart2!=null) {
									cart2.setNum(cart2.getNum()+cart.getNum());
									cart2.setUpdated(new Date());
									//更新已有的数量和时间
									String update_urlString = ADD_CART+user.getId()+"/"+cart2.getProductId()+"/"+cart2.getNum();
									httpClientUtils.doPut(update_urlString);
								}else {
									map2.put("userId", user.getId().toString());
									map2.put("productId", cart.getProductId().toString());
									map2.put("productTitle", cart.getProductTitle());
									map2.put("productImage", cart.getProductImage());
									map2.put("productPrice", cart.getProductPrice().toString());
									map2.put("num", "1");
									httpClientUtils.doPost(ADD_CART, map2);
								}
								
								
							}
						}
					}
				}
				//用户登录
				return ticket;
			}
		}
		return null;
	}

	@Override
	public User check(String url,String ticket) throws Exception {
		url = url + "user/" +ticket;
		HttpClientReturn httpClientReturn = httpClientUtils.doGet(url);
		if (httpClientReturn.getStatusCode()==200) {
			String jsonString = httpClientReturn.getResponseBody();
			User user = ObjectMapper.readValue(jsonString, User.class);
			return user;
		}
		return null;
	}

}
